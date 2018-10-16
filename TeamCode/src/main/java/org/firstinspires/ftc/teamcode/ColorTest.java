package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DistanceSensor;

import org.firstinspires.ftc.teamcode.Subsystems.ClawSensors;

@TeleOp(name = "Color Test")

public class ColorTest extends LinearOpMode {

    ClawSensors ClawS;

    @Override
    public void runOpMode() throws InterruptedException {

        ColorSensor CS[] = new ColorSensor[3];

        CS[0] = hardwareMap.get(ColorSensor.class, "CSLeft");
        CS[1] = hardwareMap.get(ColorSensor.class, "CSCent");
        CS[2] = hardwareMap.get(ColorSensor.class, "CSRight");

        DistanceSensor DS[] = new DistanceSensor[3];
        DS[0] = hardwareMap.get(DistanceSensor.class, "CSLeft");
        DS[1] = hardwareMap.get(DistanceSensor.class, "CSCent");
        DS[2] = hardwareMap.get(DistanceSensor.class, "CSRight");

        telemetry.addLine("CS Null?").addData("CS1",CS[0] == null)
                .addData("CS2",CS[1] == null)
                .addData("CS3", CS[2] == null);
        telemetry.addLine("DS Null?")
                .addData("DS1",DS[0] == null)
                .addData("DS2",DS[1] == null)
                .addData("DS3",DS[2] == null);
        telemetry.update();

        sleep(5000);

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



            telemetry.update();
            idle();
        }

    }
}
