package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.Subsystems.BlockPosition;

@Autonomous(name = "Ball/White/Crater Side")

public class WhiteAuto extends BaseAuto {

    Thread f = new Thread(new Runnable(){
        @Override
        public void run() {
            ExtendForTime(600, false);
        }
    });

    @Override
    public void runOpMode() throws InterruptedException {
        Initialize();
        Claw.SimpleOpenClose(false, true, false,false);
        Bats.Bat(false, false);

        waitForStart();
        Drive.EnableSensors();
        TFlow.activate();

        // Drop into position to read
        GoToArmPosition(2.00, 0.2);
        while (IsArmThreadRunning());
        sleep(200);

        // Read Camera
        for (int i = 0; i < 5; i++){
            BPos = TFlow.FindBlock().getLoc();
            sleep(20);
        }
        TFlow.close();
        telemetry.addData("Cam Det", BPos.toString());
        telemetry.update();

        LandingSequence();

        // Prepare for initial scoring sequence
        f.start();
        Drive.EncPID.Reset();
        Drive.GyroPID.Reset();
        sleep(50);
        DrivetoPosition(7.0);
        while (f.isAlive()) idle();

        // Do scoring Actions
        SampleMinerals.start();
        while (SampleMinerals.isAlive()) idle();
        TurnToHeading(70);
        if (BPos == BlockPosition.CENTER){
            Bats.SetRight(0.5);
            sleep(500);
            Bats.RightBat(false);
        }

        // Get to and square to wall
        Drive.EncPID.Reset();
        DrivetoPosition(30);
        TurnToHeading(125);
        Drive.Drive(0, 1.0, 0);
        sleep(2000);
        Drive.Stop();
        Drive.EncPID.Reset();

        // Drop Marker
        ExtendToLimit.start();
        GoToArmPosition(1.75, 0.5);
        while (ExtendToLimit.isAlive() || IsArmThreadRunning());
        Claw.SimpleOpenClose(true, false,false,false);
        Drive.Drive(-1.0, 0, 0);
        sleep(500);

        // Drive to Crater
        Drive.Drive(1.0,0,0);
        sleep(2000);
        Drive.Stop();
        Drive.DisableSensors();

        while (opModeIsActive());
    }
}