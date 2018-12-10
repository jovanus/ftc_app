package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "Wind Spindle")
@Disabled
public class WindSpindle extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        DcMotor x = hardwareMap.dcMotor.get("D_FL");
        waitForStart();
        while (opModeIsActive()){
            x.setPower(gamepad1.left_stick_y);
        }
    }
}
