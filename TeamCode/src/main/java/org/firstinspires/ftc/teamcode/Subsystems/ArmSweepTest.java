package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DistanceSensor;

@TeleOp(name = "TestArm")

public class ArmSweepTest extends LinearOpMode {
    MineralDetector MinDetector = new MineralDetector();
    double PosA = 0, PosB = 1;

    @Override
    public void runOpMode() throws InterruptedException {
        MinDetector.Initialize(hardwareMap.servo.get("MinSweep"),
                hardwareMap.colorSensor.get("MinDetector"),
                hardwareMap.get(DistanceSensor.class, "MinDetector"));



        Thread e = new Thread(new Runnable() {
            @Override
            public void run() {
                SweepArea(PosA, PosB, 10);
            }
        });

        waitForStart();


        while(opModeIsActive()){
            if (!e.isAlive()) {
                double SetPos = gamepad1.left_stick_y / 2 + 0.5;
                MinDetector.GoToPos(SetPos);
                if (gamepad1.a) PosA = SetPos;
                else if (gamepad1.b) PosB = SetPos;

                if (gamepad1.y) e.start();
            }

            idle();
        }

    }


    public boolean SweepArea(double Begin, double End, int Jumps){
        double startTime = getRuntime();
        double spaceBetweenJumps = (End - Begin)/Jumps;
        while(getRuntime() < (startTime + 0.75)){
            MinDetector.GoToPos(Begin);
        }
        for (int i = 0; i < Jumps; i++) {
            MinDetector.GoToPos(Begin + i * spaceBetweenJumps);
            if (MinDetector.determineColor() == Colors.YELLOW) return true;
            else if (MinDetector.determineColor() == Colors.WHITE) return false;
            sleep(20);
        }
        return false;
    }
}
