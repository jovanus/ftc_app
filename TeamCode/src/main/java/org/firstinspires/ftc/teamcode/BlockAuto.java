package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Yellow Mode")

public class BlockAuto extends BaseAuto {


    @Override
    public void runOpMode() throws InterruptedException {
        Initialize();
        Claw.SimpleOpenClose(false, true, false,false);

        waitForStart();
        Drive.EnableSensors();

        //LandingSequence();

        Drive.EncPID.Reset();
        sleep(50);
        DrivetoPosition(4);

        double BlockHeading = 35;
        if (SweepArea(0.5, 0.25, 10)) BlockHeading *= -1;
        else if (SweepArea(0.25, 0.0, 10)) BlockHeading = 0;

        TurnToHeading(BlockHeading);

        Drive.EncPID.Reset();
        MinDetector.GoToPos(1);
        DrivetoPosition(28);

        TurnToHeading(-BlockHeading);


        Drive.DisableSensors();

        while (opModeIsActive());
    }
}
