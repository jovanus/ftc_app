package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "BatTest")
//@Disabled

public class ArmBatTest extends BaseAuto {


    @Override
    public void runOpMode() throws InterruptedException {
        Initialize();
        waitForStart();

        while (opModeIsActive()){
            telemetry.addData("ArmBatLeft", Bats.SetLeft(gamepad1.left_stick_y));
            telemetry.addData("ArmBatRight", Bats.SetRight(gamepad1.right_stick_y));
            telemetry.update();
        }
    }
}
