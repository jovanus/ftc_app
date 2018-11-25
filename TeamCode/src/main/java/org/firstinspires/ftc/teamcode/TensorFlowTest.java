package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.Subsystems.TensorFlow;

@TeleOp(name = "Tensor Flow")

public class TensorFlowTest extends LinearOpMode {

    TensorFlow TFlow = new TensorFlow();

    @Override
    public void runOpMode() throws InterruptedException {
        TFlow.Initialize(hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName()));

        waitForStart();
        while(opModeIsActive()){
            telemetry.addData("Status | ", TFlow.FindBlock().toString());
            telemetry.update();
        }

        TFlow.close();

    }
}
