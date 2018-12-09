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
import org.firstinspires.ftc.teamcode.Subsystems.DetectBats;
import org.firstinspires.ftc.teamcode.Subsystems.ExtendArmSystem;
import org.firstinspires.ftc.teamcode.Subsystems.MechenumDrive;
import org.firstinspires.ftc.teamcode.Subsystems.MineralDetector;
import org.firstinspires.ftc.teamcode.Subsystems.TensorFlow;
import org.firstinspires.ftc.teamcode.Subsystems.WheelieBar;

public abstract class BaseAuto extends LinearOpMode {

    AutoDrive Drive = new AutoDrive();
    ArmSystem Arm = new ArmSystem();
    ClawSystem Claw = new ClawSystem();
    WheelieBar Wheelie = new WheelieBar();
    ExtendArmSystem Extend = new ExtendArmSystem();
    TensorFlow TFlow = new TensorFlow();
    BlockPosition BPos = BlockPosition.LEFT;
    DetectBats Bats = new DetectBats();



    protected void TurnToHeading(double Angle){
        Drive.GyroPID.setSetPoint(Angle);
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
        Drive.EncPID.setSetPoint(Position);
        telemetry.addLine()
                .addData("Run: ", Drive.isPositionInTolerance())
                .addData("Error :", Drive.PositionError())
                .addData("Output :", Drive.EncPID.getOutput());
        telemetry.update();

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
        final Servo BServos[] = {hardwareMap.servo.get("B_Left"), hardwareMap.servo.get("B_Right")};

        // Initializers
        Drive.initialize(init_drive,
                hardwareMap.get(BNO055IMU.class,"imu"));
        Arm.Initialize(init_Arm, init_Arm_Touch,
                hardwareMap.analogInput.get("Arm_P"));
        Extend.Initialize(init_Extend, init_Extend_Touch);
        Claw.Initialize(CServo);
        Wheelie.Initialize(hardwareMap.servo.get("Wheelie"),
                hardwareMap.get(RevTouchSensor.class,"Wheelie_Out"));
        TFlow.Initialize(hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName()));
        Bats.Initialize(BServos);


    }

    // Thread that brings arm down
    Thread a = new Thread(new Runnable() {
        @Override
        public void run() {
            // Move Arm Forward
            Arm.Power(0.3);
            while(Arm.GetPotVoltage() < DROP_TO_RIDE){
                telemetry.addData("Arm Pos", Arm.GetPotVoltage());
                telemetry.update();
                idle();
            }
            Arm.Power(0);
        }
    });

    // Extend Arm Threads
    Thread RetractToLimit = new Thread(new Runnable() {
        @Override
        public void run() {
            Extend.Power(-1);
            while (!Extend.TouchStatus()[1]);
            Extend.Power(0);
        }
    });

    Thread ExtendToLimit = new Thread(new Runnable() {
        @Override
        public void run() {
            Extend.Power(1);
            while (!Extend.TouchStatus()[0] && opModeIsActive());
            Extend.Power(0);
        }
    });

    public void ExtendForTime(long Time, boolean ExtendOut){
        Extend.Power(ExtendOut ? 1 : -1);
        sleep(Time);
        Extend.Stop();
    }

    // Arm deflection setup then run
    double ArmSetPower = 0;
    double targetVoltage = 0;
    boolean isArmAboveTarget = false;
    public void GoToArmPosition(double targetVoltage, double Power){
        isArmAboveTarget = targetVoltage < Arm.GetPotVoltage();
        ArmSetPower = (isArmAboveTarget ? 1 : -1) * Math.abs(Power);
        this.targetVoltage = targetVoltage;
        DeflectArm.run();
    }

    public boolean IsArmThreadRunning(){
        return DeflectArm.isAlive();
    }

    private Thread DeflectArm = new Thread(new Runnable() {
        @Override
        public void run() {
            Arm.Power(ArmSetPower);
            if (isArmAboveTarget) while (Arm.GetPotVoltage() < targetVoltage);
            else while (Arm.GetPotVoltage() > targetVoltage);
            Arm.Stop();
        }
    });



    final double LANDING_LEVEL = 0.6,
    DROP_TO_RIDE = 1.0;
    protected void LandingSequence(){
        // Land
//        Arm.Power(-0.75);
//        while(Arm.GetPotVoltage() > LANDING_LEVEL){
//            telemetry.addData("Arm Pos", Arm.GetPotVoltage());
//            telemetry.update();
//        }
//        Arm.Power(0);
        GoToArmPosition(LANDING_LEVEL, 0.75);
        while(IsArmThreadRunning());

        // Extend Arm

        ExtendForTime(2250, true);
//        Extend.Power(1);
//        Arm.Power(0.2);
//        sleep(2250);
//        Extend.Power(0);
//        Arm.Power(0);

        GoToArmPosition(DROP_TO_RIDE, 0.3);
        while(IsArmThreadRunning());
//        a.start();
//        sleep(250);
//        while(a.isAlive());
    }

    @Override
    public abstract void runOpMode() throws InterruptedException;
}
