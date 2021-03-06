package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.teamcode.Subsystems.ArmSystem;
import org.firstinspires.ftc.teamcode.Subsystems.ClawSystem;
import org.firstinspires.ftc.teamcode.Subsystems.MechenumDrive;

@TeleOp(name = "TestOp")

public class TestBed extends LinearOpMode {

    MechenumDrive Drive = new MechenumDrive();
    ArmSystem Arm = new ArmSystem();
    ClawSystem Claw = new ClawSystem();

    @Override
    public void runOpMode() throws InterruptedException {
        final DcMotor init_drive[] ={hardwareMap.dcMotor.get("D_FL"),
                hardwareMap.dcMotor.get("D_FR"),
                hardwareMap.dcMotor.get("D_RL"),
                hardwareMap.dcMotor.get("D_RR")};
        final DcMotor init_Arm[] = {hardwareMap.dcMotor.get("A_Left"),
                hardwareMap.dcMotor.get("A_Right")};
        final DcMotor init_Extend[] = {hardwareMap.dcMotor.get("E_Left"),
                hardwareMap.dcMotor.get("E_Right")};
        final Servo init_Claw[] = {hardwareMap.servo.get("C_Left"),
                hardwareMap.servo.get("C_Right")};

        Drive.Initialize(init_drive);
        Arm.Initialize(init_Arm, init_Extend);
        Claw.Initilize(init_Claw);

        waitForStart();

        while (opModeIsActive()){
            double Power[] = Drive.Drive(gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);
            telemetry.addLine("Power").addData("FL",Power[0]).addData("FR",Power[1])
                    .addData("RL",Power[2]).addData("RR",Power[3]);
            Arm.ArmLiftPow(gamepad2.left_stick_y);
            Arm.ExtendArmPow(gamepad2.right_stick_y);
            Claw.ClawPos(gamepad2.a);
            telemetry.update();
            idle();
        }
    }
}
