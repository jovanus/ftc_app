package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@Autonomous(name = "Ball/White Auto")
@Disabled

public class WhiteAuto extends BaseAuto {
    @Override
    public void runOpMode() throws InterruptedException {
        Initialize();
        Drive.EnableSensors();
        this.Drive.EncPID.Reset();
        this.Drive.GyroPID.Reset();

        waitForStart();

        LandingSequence();

        Drive.EncPID.Reset();
        DrivetoPosition(12.0);
        Drive.EncPID.Reset();
        Drive.GyroPID.Reset();
        TurnToHeading(90);
        Drive.EncPID.Reset();
        DrivetoPosition(48.0);
        //TurnToHeading(120);
        resetStartTime();
        while (getRuntime() < 0.3 && opModeIsActive()) Drive.Drive(0,0, -1);
        Drive.Stop();

        Drive.EncPID.Reset();
        DrivetoPosition(26.0);

        Drive.DisableSensors();



    }
}
