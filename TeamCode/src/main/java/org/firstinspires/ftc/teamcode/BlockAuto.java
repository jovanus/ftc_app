package org.firstinspires.ftc.teamcode;


public class BlockAuto extends BaseAuto {


    @Override
    public void runOpMode() throws InterruptedException {
        Initialize();
        Claw.SimpleOpenClose(false, true, false,false);

        waitForStart();

        LandingSequence();

        Drive.EncPID.Reset();
        DrivetoPosition(6);

        double BlockHeading = 30;
        if (SweepArea(0.5, 0.25, 20)) BlockHeading = -30;
        else if (SweepArea(0.5, 0.0, 20)) BlockHeading = 0;

        TurnToHeading(BlockHeading);

        Drive.EncPID.Reset();
        DrivetoPosition(16);

        TurnToHeading(-BlockHeading);



        while (opModeIsActive());
    }
}
