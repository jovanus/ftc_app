package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Subsystems.BlockPosition;

@Autonomous(name = "Gold/Block/Depot Side")

public class BlockAuto extends BaseAuto {

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

        TFlow.activate();
        waitForStart();
        Drive.EnableSensors();

        // Detect Mineral
        for (int i = 0; i < 5; i++){
            BPos = TFlow.FindBlock().getLoc();
            sleep(20);
        }
        TFlow.close();
        telemetry.addData("Cam Det", BPos.toString());
        telemetry.update();

        // Land
        LandingSequence();
        //f.start();

        // Prepare to Score
        Drive.EncPID.Reset();
        Drive.GyroPID.Reset();
        sleep(50);
        DrivetoPosition(8.0);
        while (f.isAlive()) idle();


        // Score
        MarkerDrop.start();
        SampleMinerals.start();
        while (MarkerDrop.isAlive() || SampleMinerals.isAlive()) idle();
        TurnToHeading(75);
        if (BPos == BlockPosition.CENTER){
            Bats.SetRight(0.5);
            sleep(500);
            Bats.RightBat(false);
        }

        // Drive to Crater
        Drive.EncPID.Reset();
        DrivetoPosition(30);
        TurnToHeading(125);

        //Prepare to pick up Blocks
        ExtendToLimit.start(); // Want to enter Crater Further Back
        while (ExtendToLimit.isAlive());
        GoToArmPosition(1.75, 0.2);
        while(IsArmThreadRunning());

        // Close Shop
        Drive.DisableSensors();
        while (opModeIsActive());
    }
}
