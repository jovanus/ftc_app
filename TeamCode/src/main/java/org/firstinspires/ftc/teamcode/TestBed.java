package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevTouchSensor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.teamcode.Subsystems.ArmSystem;
import org.firstinspires.ftc.teamcode.Subsystems.AutomaticClaw;
import org.firstinspires.ftc.teamcode.Subsystems.ClawSystem;
import org.firstinspires.ftc.teamcode.Subsystems.MechenumDrive;

@TeleOp(name = "TestOp")

public class TestBed extends LinearOpMode {

    MechenumDrive Drive = new MechenumDrive();
    ArmSystem Arm = new ArmSystem();
    AutomaticClaw Claw = new AutomaticClaw();

    @Override
    public void runOpMode() throws InterruptedException {

        this.Initialize();
        Claw.OpenClaw();

        waitForStart();

        while (opModeIsActive()){
            Drive.Drive(gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);
            //telemetry.addLine("Power").addData("FL",Power[0]).addData("FR",Power[1])
            //        .addData("RL",Power[2]).addData("RR",Power[3]);
            Arm.ArmLiftPow(gamepad2.left_stick_y);
            Arm.ExtendArmPow(gamepad2.right_stick_y);
            if (gamepad2.b) Claw.OpenClaw();
            if (gamepad2.a) Claw.CloseClaw();
            telemetry.update();
            idle();
        }
    }


    public void Initialize(){
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
        final ColorSensor CS[] = {hardwareMap.get(ColorSensor.class, "CSLeft"),
                hardwareMap.get(ColorSensor.class, "CSCent"),
                hardwareMap.get(ColorSensor.class, "CSRight")};
        final DistanceSensor DS[] = {hardwareMap.get(DistanceSensor.class, "CSLeft"),
                hardwareMap.get(DistanceSensor.class, "CSCent"),
                hardwareMap.get(DistanceSensor.class, "CSRight")};
        final Servo CServo[] = {hardwareMap.servo.get("C_Left"),
                hardwareMap.servo.get("C_Right")};

        Drive.Initialize(init_drive);
        Arm.Initialize(init_Arm, init_Extend, init_Arm_Touch, init_Extend_Touch);
        Claw.Initialize(CServo, CS, DS);

    }
}
