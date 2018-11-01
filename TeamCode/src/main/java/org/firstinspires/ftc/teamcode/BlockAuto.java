package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Subsystems.BlockPosition;

@Autonomous(name = "White/Yellow Mode")

public class BlockAuto extends BaseAuto {


    @Override
    public void runOpMode() throws InterruptedException {
        Initialize();
        Claw.SimpleOpenClose(false, true, false,false);

        waitForStart();
        Drive.EnableSensors();

        LandingSequence();

        Drive.EncPID.Reset();
        Drive.GyroPID.Reset();
        sleep(50);
        DrivetoPosition(5.5);

        DriveToBlock(DetectBlock());

        Arm.Power(0.3);
        while(Arm.GetPotVoltage() < 1.5){
            telemetry.addData("Arm Pos", Arm.GetPotVoltage());
            telemetry.update();
        }
        Arm.Power(0);

        Extend.Power(1);
        sleep(1000);
        Extend.Power(0);

        Claw.SimpleOpenClose(true, false,false,false);
        sleep(200);

        Extend.Power(-1);
        while (!Extend.TouchStatus()[1]);
        Extend.Power(0);

        Drive.DisableSensors();

        while (opModeIsActive());
    }
}
