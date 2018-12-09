package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "White/Yellow Mode")

public class BlockAuto extends BaseAuto {


    // Drops the Team Marker in the Zone
    Thread MarkerDrop = new Thread(new Runnable() {
        @Override
        public void run() {
//            Arm.Power(.75);
//            while(Arm.GetPotVoltage() < 1.75) idle();
//            Arm.Power(0);
            GoToArmPosition(1.75, 0.75);
            while (IsArmThreadRunning());
            Claw.SimpleOpenClose(true, false,false,false);
            RetractToLimit.start();
            GoToArmPosition(1.55, 1);
        }
    });

    // Once In position, hit the blocks
    Thread SampleMinerals = new Thread(new Runnable() {
        @Override
        public void run() {
            final int sleeptime = 500;
            switch (BPos){
                case LEFT:
                    Bats.Bat(true, false);
                    sleep(sleeptime);
                    Bats.Bat(false, false);
                    break;
                case CENTER:
                    Bats.Bat(false, false);
                    Drive.EncPID.Reset();
                    DrivetoPosition(13);
                    Drive.EncPID.Reset();
                    DrivetoPosition(-13);
                    break;
                case RIGHT:
                    Bats.Bat(false, true);
                    sleep(sleeptime);
                    Bats.Bat(false, false);
                    break;
            }
        }
    });

    Thread f = new Thread(new Runnable(){
        @Override
        public void run() {
            ExtendForTime(600, false);
//            Extend.Power(1.0);
//            sleep(600);
//            Extend.Power(0);
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

        for (int i = 0; i < 5; i++){
            BPos = TFlow.FindBlock().getLoc();
            sleep(20);
        }
        telemetry.addData("Cam Det", BPos.toString());
        telemetry.update();

        LandingSequence();

        f.start();

        Drive.EncPID.Reset();
        Drive.GyroPID.Reset();
        sleep(50);
        DrivetoPosition(7.0);
        while (f.isAlive()) idle();


        MarkerDrop.start();
        SampleMinerals.start();

        while (MarkerDrop.isAlive() || SampleMinerals.isAlive()) idle();

        TurnToHeading(70);
        Drive.EncPID.Reset();
        DrivetoPosition(30);
        TurnToHeading(125);
//        Drive.EncPID.Reset();
//        DrivetoPosition(30);


        ExtendToLimit.run(); // Want to enter Crater Further Back
        GoToArmPosition(1.75, 0.5);
        while (ExtendToLimit.isAlive() || IsArmThreadRunning())
        Drive.DisableSensors();

        while (opModeIsActive());
    }
}
