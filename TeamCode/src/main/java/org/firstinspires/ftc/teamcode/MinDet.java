package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.Subsystems.MineralDetector;

@Autonomous(name = "MinDetect")
@Disabled

public class MinDet extends BaseAuto {
    @Override
    public void runOpMode() throws InterruptedException {
        Initialize();
        waitForStart();

        MinDetector.GoToPos(0.5);

        while (opModeIsActive()){

            float[] Color = MinDetector.getHSV();
            telemetry.addData("Color", MinDetector.determineColor());
            telemetry.addData("Hue", Color[0]);
            telemetry.addData("Saturation", Color[1]);
            telemetry.addData("Value", Color[2]);
            telemetry.addData("Dist", MinDetector.getDist());
            telemetry.update();
        }
        MinDetector.GoToPos(1);

    }
}
