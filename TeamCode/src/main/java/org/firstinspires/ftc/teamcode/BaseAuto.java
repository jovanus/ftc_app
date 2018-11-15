package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.rev.RevTouchSensor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Subsystems.ArmSystem;
import org.firstinspires.ftc.teamcode.Subsystems.AutoDrive;
import org.firstinspires.ftc.teamcode.Subsystems.AutomaticClaw;
import org.firstinspires.ftc.teamcode.Subsystems.BlockPosition;
import org.firstinspires.ftc.teamcode.Subsystems.ClawSystem;
import org.firstinspires.ftc.teamcode.Subsystems.Colors;
import org.firstinspires.ftc.teamcode.Subsystems.ExtendArmSystem;
import org.firstinspires.ftc.teamcode.Subsystems.MechenumDrive;
import org.firstinspires.ftc.teamcode.Subsystems.MineralDetector;
import org.firstinspires.ftc.teamcode.Subsystems.WheelieBar;

public abstract class BaseAuto extends LinearOpMode {

    AutoDrive Drive = new AutoDrive();
    ArmSystem Arm = new ArmSystem();
    ClawSystem Claw = new ClawSystem();
    WheelieBar Wheelie = new WheelieBar();
    ExtendArmSystem Extend = new ExtendArmSystem();
    MineralDetector MinDetector = new MineralDetector();

    protected void TurnToHeading(double Angle){
        Drive.setHeading(Angle);
        sleep(30);
        telemetry.addLine()
                .addData("Run: ", Drive.isHeadingInTolerance())
                .addData("Error :", Drive.getHeadingError())
                .addData("Output :", Drive.GyroPID.getOutput());
        telemetry.update();

        while (!Drive.isHeadingInTolerance() && opModeIsActive()){
            telemetry.addLine()
                    .addData("Run: ", Drive.isHeadingInTolerance())
                    .addData("Error :", Drive.getHeadingError())
                    .addData("Output :", Drive.GyroPID.getOutput());
            telemetry.update();
            Drive.TurnToHeading();
            sleep(10);
        }
        Drive.Stop();
    }

    protected void DrivetoPosition(double Position){
        Drive.setPosition(Position);

        sleep(50);
        while (!Drive.isPositionInTolerance() && opModeIsActive()){
            telemetry.addLine()
                    .addData("Run: ", Drive.isPositionInTolerance())
                    .addData("Error :", Drive.PositionError())
                    .addData("Output :", Drive.EncPID.getOutput());
            telemetry.update();
            Drive.DriveToPosition();
        }
        Drive.Stop();
    }

    public void Initialize(){

        // Create Initializer Arrays
        final DcMotor init_drive[] = {
                hardwareMap.dcMotor.get("D_FL"),
                hardwareMap.dcMotor.get("D_FR"),
                hardwareMap.dcMotor.get("D_RL"),
                hardwareMap.dcMotor.get("D_RR")};
        final DcMotor init_Arm[] = {
                hardwareMap.dcMotor.get("A_Left"),
                hardwareMap.dcMotor.get("A_Right")};
        final DcMotor init_Extend[] = {
                hardwareMap.dcMotor.get("E_Left"),
                hardwareMap.dcMotor.get("E_Right")};
        final Servo init_Claw[] = {
                hardwareMap.servo.get("C_Left"),
                hardwareMap.servo.get("C_Right")};
        final RevTouchSensor init_Arm_Touch[] = {
                hardwareMap.get(RevTouchSensor.class, "A_OUT_T"),
                hardwareMap.get(RevTouchSensor.class, "A_IN_T")};
        final RevTouchSensor init_Extend_Touch[] = {
                hardwareMap.get(RevTouchSensor.class, "A_TILT_UP_T"),
                hardwareMap.get(RevTouchSensor.class, "A_TILT_DOWN_T")};
        final Servo CServo[] = {hardwareMap.servo.get("C_Left"),
                hardwareMap.servo.get("C_Right")};

        // Initializers
        Drive.initialize(init_drive,
                hardwareMap.get(BNO055IMU.class,"imu"));
        Arm.Initialize(init_Arm, init_Arm_Touch,
                hardwareMap.analogInput.get("Arm_P"));
        Extend.Initialize(init_Extend, init_Extend_Touch);
        Claw.Initialize(CServo);
        Wheelie.Initialize(hardwareMap.servo.get("Wheelie"),
                hardwareMap.get(RevTouchSensor.class,"Wheelie_Out"));
        MinDetector.Initialize(hardwareMap.servo.get("MinSweep"),
                hardwareMap.colorSensor.get("MinDetector"),
                hardwareMap.get(DistanceSensor.class, "MinDetector"));
    }

    // Thread that brings arm down
    Thread a = new Thread(new Runnable() {
        @Override
        public void run() {
            // Move Arm Forward
            Arm.Power(0.3);
            while(Arm.GetPotVoltage() < 1.1){
                telemetry.addData("Arm Pos", Arm.GetPotVoltage());
                telemetry.update();
                idle();
            }
            Arm.Power(0);
        }
    });

    // Retract Arm
    Thread b = new Thread(new Runnable() {
        @Override
        public void run() {
            Extend.Power(-1);
            while (!Extend.TouchStatus()[1]);
            Extend.Power(0);
            idle();
        }
    });


    protected void LandingSequence(){
        // Land
        Arm.Power(-0.75);
        Drive.Unwind(1);
        while(Arm.GetPotVoltage() > 0.72){
            telemetry.addData("Arm Pos", Arm.GetPotVoltage());
            telemetry.update();
        }
        Arm.Power(0);
        Drive.Unwind(0);

        // Extend Arm

        Extend.Power(1);
        Arm.Power(0.2);
        sleep(2250);
        Extend.Power(0);
        Arm.Power(0);

        a.start();
        b.start();
        sleep(250);
        while(a.isAlive() || b.isAlive());
    }


    public boolean SweepArea(double Begin, double End, int Jumps){
        double startTime = getRuntime();
        double spaceBetweenJumps = (End - Begin)/Jumps;
        MinDetector.GoToPos(Begin);
        sleep(750);
        for (int i = 0; i < Jumps; i++) {
            MinDetector.GoToPos(Begin + i * spaceBetweenJumps);
            sleep(30);
            if (MinDetector.determineColor() == Colors.YELLOW) return true;
            else if (MinDetector.determineColor() == Colors.WHITE) break;
        }
        return false;
    }

    public BlockPosition DetectBlock(){
        BlockPosition BPos = BlockPosition.LEFT;
        if (SweepArea(0.45, 0.3, 15)) BPos = BlockPosition.RIGHT;
        else if (SweepArea(0.19, 0.1, 15)) BPos = BlockPosition.CENTER;
        telemetry.addData("Block: ", BPos.toString());
        telemetry.update();
        return BPos;
    }

    public void DriveToBlock(BlockPosition BPos){
        switch (BPos){
            case LEFT:
                MinDetector.GoToPos(1);
                TurnToHeading(35);
                break;
            case CENTER:
                MinDetector.GoToPos(1);
                break;
            case RIGHT:
                MinDetector.GoToPos(1);
                TurnToHeading(-35);
                break;
        }
        Drive.EncPID.Reset();
        DrivetoPosition(28);
        switch (BPos){
            case LEFT:
                TurnToHeading(-35);
                break;
            case CENTER:
                break;
            case RIGHT:
                TurnToHeading(35);
                break;
        }
    }

    @Override
    public abstract void runOpMode() throws InterruptedException;
}
