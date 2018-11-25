package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Subsystems.LocationStatus;
import org.firstinspires.ftc.teamcode.Subsystems.TensorFlow;

@TeleOp(name = "Tensor Flow")
@Disabled

public class TensorFlowTest extends LinearOpMode {

    TensorFlow TFlow = new TensorFlow();

    @Override
    public void runOpMode() throws InterruptedException {
        TFlow.Initialize(hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName()));

        waitForStart();
        TFlow.activate();
        while(opModeIsActive()){

            LocationStatus LStat = TFlow.FindBlock();
            telemetry.addLine().
                    addData("Loc: ", LStat.getLoc().toString()).
                    addData("#O: ", LStat.getNumOfObjects()).
                    addData("B? ", LStat.isBOnScreen());
            //telemetry.addData("Status", TFlow.FindBlock().toString());
            telemetry.update();
        }

        TFlow.close();

    }
}
