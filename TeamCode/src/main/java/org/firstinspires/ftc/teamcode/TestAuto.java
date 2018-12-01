package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

@Autonomous(name = "Test Mode")
@Disabled

public class TestAuto extends BaseAuto {

    Thread a = new Thread(new Runnable(){
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
            Extend.Power(-1);
            while (Extend.TouchStatus()[1]) idle();
            Extend.Power(0);
        }
    });

    // Once In position, hit the blocks
    Thread d = new Thread(new Runnable() {
        @Override
        public void run() {
            final int sleeptime = 500;
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

        //TFlow.activate();
        waitForStart();
        Drive.EnableSensors();

        // LandingSequence();

        //a.start();

        telemetry.addData("Segment", 1);
        telemetry.update();
        Drive.EncPID.Reset();
        Drive.GyroPID.Reset();
        telemetry.addData("Segment", 2);
        telemetry.update();
        sleep(50);
        telemetry.addData("Segment", 3);
        telemetry.update();
        DrivetoPosition(7.0);
        telemetry.addData("Segment", 4);
        telemetry.update();
        //while (a.isAlive()) idle();


        //c.start();
        //d.start();

        //while (c.isAlive()) idle();


        Drive.DisableSensors();

        telemetry.addData("Segment", 5);
        telemetry.update();
        while (opModeIsActive());
    }
}
