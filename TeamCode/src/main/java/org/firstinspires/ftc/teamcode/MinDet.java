package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "MinDetect")
public class MinDet extends BaseAuto {
    @Override
    public void runOpMode() throws InterruptedException {
        Initialize();
        waitForStart();

        while (opModeIsActive()){

            telemetry.addData("Color", MinDetector.determineColor());
            telemetry.addData("Detect", MinDetector.getHSV()[0]);
            telemetry.addData("Dist", MinDetector.getDist());
            telemetry.update();
        }
    }
}
