package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Subsystems.PixyCam;


@Autonomous(name = "CamTest")
@Disabled

public class PixyTest extends LinearOpMode {
    PixyCam Cam = new PixyCam();

    @Override
    public void runOpMode() throws InterruptedException {
        Cam.Initialize(hardwareMap.i2cDeviceSynch.get("Pixy"));

        waitForStart();

        Cam.start();

        while (opModeIsActive()){
            byte[] data = Cam.ReadData();


            telemetry.addLine()
                    .addData("Num" , 0xff & data[0])
                    .addData("X", 0xff & data[1])
                    .addData("Y", 0xff & data[2]);
            telemetry.update();


        }
        Cam.stop();
    }
}
