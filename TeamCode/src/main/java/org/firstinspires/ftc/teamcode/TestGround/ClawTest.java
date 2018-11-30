package org.firstinspires.ftc.teamcode.TestGround;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Subsystems.*;

@TeleOp(name = "Claw Test")
@Disabled

public class ClawTest extends LinearOpMode {

    AutomaticClaw Claw;

    @Override
    public void runOpMode() throws InterruptedException {
        Claw = new AutomaticClaw();
        final ColorSensor CS[] = {hardwareMap.get(ColorSensor.class, "CSLeft"),
                hardwareMap.get(ColorSensor.class, "CSCent"),
                hardwareMap.get(ColorSensor.class, "CSRight")};
        final DistanceSensor DS[] = {hardwareMap.get(DistanceSensor.class, "CSLeft"),
                hardwareMap.get(DistanceSensor.class, "CSCent"),
                hardwareMap.get(DistanceSensor.class, "CSRight")};
        final Servo CServo[] = {hardwareMap.servo.get("C_Left"),
                hardwareMap.servo.get("C_Right")};
        Claw.Initialize(CServo, CS, DS);

        Claw.OpenClaw();

        waitForStart();

        boolean[] z = Claw.CHECKLIST[0];

        while(opModeIsActive()){
            if(gamepad2.a) Claw.CloseClaw();
            if(gamepad2.b) Claw.OpenClaw();

            boolean[] x = Claw.ParseColors();

            telemetry.addData("L", x[0]);
            telemetry.addData("C", x[1]);
            telemetry.addData("R", x[2]);

            ClawPos y = Claw.DeterminePosition(x);

            telemetry.addData("Parsed Loc", y.toString());



            telemetry.addLine("Test ").addData("L", z[0])
                    .addData("C", z[1])
                    .addData("R", z[2]);

            telemetry.update();
            idle();
        }

    }
}
