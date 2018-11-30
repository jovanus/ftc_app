package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Subsystems.BlockPosition;

@Autonomous(name = "White/Yellow Mode")

public class BlockAuto extends BaseAuto {


    // Drops the Team Marker in the Zone
    Thread c = new Thread(new Runnable() {
        @Override
        public void run() {
            Arm.Power(1.0);
            while(Arm.GetPotVoltage() < 1.9) idle();
            Arm.Power(0);
            Claw.SimpleOpenClose(true, false,false,false);
        }
    });

    // Once In position, hit the blocks
    Thread d = new Thread(new Runnable() {
        @Override
        public void run() {
            final int sleeptime = 250;
            switch (BPos){
                case LEFT:
                    Bats.LeftBat(true);
                    sleep(sleeptime);
                    Bats.LeftBat(false);
                    break;
                case CENTER:
                    Drive.EncPID.Reset();
                    DrivetoPosition(5.5);
                    DrivetoPosition(0);
                    break;
                case RIGHT:
                    Bats.RightBat(true);
                    sleep(sleeptime);
                    Bats.RightBat(false);
                    break;
            }
        }
    });

    @Override
    public void runOpMode() throws InterruptedException {
        Initialize();
        Claw.SimpleOpenClose(false, true, false,false);

        TFlow.activate();
        waitForStart();
        Drive.EnableSensors();

        BPos = TFlow.FindBlock().getLoc();

        LandingSequence();

        Drive.EncPID.Reset();
        Drive.GyroPID.Reset();
        sleep(50);
        DrivetoPosition(5.5);


        c.start();
        d.start();

        while (c.isAlive() || d.isAlive()) idle();


        Drive.DisableSensors();

        while (opModeIsActive());
    }
}
