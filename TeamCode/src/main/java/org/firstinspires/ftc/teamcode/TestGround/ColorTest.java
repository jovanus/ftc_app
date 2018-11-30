package org.firstinspires.ftc.teamcode.TestGround;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DistanceSensor;

import org.firstinspires.ftc.teamcode.Subsystems.*;

@TeleOp(name = "Color Test")
@Disabled

public class ColorTest extends LinearOpMode {

    ClawSensors ClawS;

    @Override
    public void runOpMode() throws InterruptedException {
        ClawS = new ClawSensors();

        final ColorSensor CS[] = {hardwareMap.get(ColorSensor.class, "CSLeft"),
        hardwareMap.get(ColorSensor.class, "CSCent"),
        hardwareMap.get(ColorSensor.class, "CSRight")};

        final DistanceSensor DS[] = {hardwareMap.get(DistanceSensor.class, "CSLeft"),
        hardwareMap.get(DistanceSensor.class, "CSCent"),
        hardwareMap.get(DistanceSensor.class, "CSRight")};

        ClawS.Initialize(DS, CS);

        waitForStart();

        while(opModeIsActive()){
            float HSVArray[][] = ClawS.getHSV();
            double DistArray[] = ClawS.getDist();

            for (int i = 0; i < 3; i++) {
                telemetry.addLine()
                        .addData("Sensor: ", i)
                        .addData("H:", HSVArray[i][0])
                        .addData("S:", HSVArray[i][1])
                        .addData("V:", HSVArray[i][2])
                        .addData("Dist:", DistArray[i]);
            }

            Colors[] CDetColor = ClawS.determineColors();

                telemetry.addLine()
                        .addData("S1", CDetColor[0])
                        .addData("S2", CDetColor[1])
                        .addData("S3", CDetColor[2]);




            telemetry.update();
            idle();
        }

    }
}
