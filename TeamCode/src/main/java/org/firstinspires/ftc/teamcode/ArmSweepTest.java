package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DistanceSensor;

import org.firstinspires.ftc.teamcode.Subsystems.Colors;
import org.firstinspires.ftc.teamcode.Subsystems.MineralDetector;

@TeleOp(name = "TestArm2")

public class ArmSweepTest extends LinearOpMode {
    MineralDetector MinDetector = new MineralDetector();
    double PosA = 0, PosB = 1;
    boolean result = false;

    @Override
    public void runOpMode() throws InterruptedException {
        MinDetector.Initialize(hardwareMap.servo.get("MinSweep"),
                hardwareMap.colorSensor.get("MinDetector"),
                hardwareMap.get(DistanceSensor.class, "MinDetector"));



        Thread e = new Thread(new Runnable() {
            @Override
            public void run() {
                result = SweepArea(PosA, PosB, 20);
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
            telemetry.addLine("Set Points")
                    .addData("A", PosA)
                    .addData("B", PosB);
            telemetry.addData("Result", result);

            telemetry.update();

            idle();
        }

    }


    public boolean SweepArea(double Begin, double End, int Jumps){
        double startTime = getRuntime();
        double spaceBetweenJumps = (End - Begin)/Jumps;
        MinDetector.GoToPos(Begin);
        sleep(750);
        for (int i = 0; i < Jumps; i++) {
            MinDetector.GoToPos(Begin + i * spaceBetweenJumps);
            sleep(20);
            if (MinDetector.determineColor() == Colors.YELLOW) return true;
            else if (MinDetector.determineColor() == Colors.WHITE) break;
        }
        return false;
    }
}
