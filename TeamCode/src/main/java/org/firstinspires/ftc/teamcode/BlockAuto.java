package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Subsystems.BlockPosition;

@Autonomous(name = "White/Yellow Mode")

public class BlockAuto extends BaseAuto {

    Thread f = new Thread(new Runnable(){
        @Override
        public void run() {
            Extend.Power(1.0);
            sleep(600);
            Extend.Power(0);
        }
    });

    // Drops the Team Marker in the Zone
    Thread c = new Thread(new Runnable() {
        @Override
        public void run() {
            Arm.Power(.75);
            while(Arm.GetPotVoltage() < 1.75) idle();
            Arm.Power(0);
            Claw.SimpleOpenClose(true, false,false,false);
            b.start();
            e.start();
        }
    });

    // Once In position, hit the blocks
    Thread d = new Thread(new Runnable() {
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

    Thread e = new Thread(new Runnable() {
        @Override
        public void run() {
            Arm.Power(-1.0);
            while(Arm.GetPotVoltage() > 1.55) idle();
            Arm.Power(0);
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


        c.start();
        d.start();

        while (c.isAlive() || d.isAlive()) idle();

        TurnToHeading(70);
        Drive.EncPID.Reset();
        DrivetoPosition(30);
        TurnToHeading(125);
        Drive.EncPID.Reset();
        DrivetoPosition(30);

        Extend.Power(1);
        while (!Extend.TouchStatus()[0] && opModeIsActive());
        Extend.Power(0);


        Drive.DisableSensors();

        while (opModeIsActive());
    }
}
