package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Subsystems.Colors;

@Autonomous(name = "StrafeTest")

public class StrafeTest extends BaseAuto {
    @Override
    public void runOpMode() throws InterruptedException {
        Initialize();

        waitForStart();

        Drive.Drive(0,1,0);
        while(MinDetector.determineColor() != Colors.YELLOW);
        Drive.Stop();

    }
}
