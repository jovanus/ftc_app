package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Subsystems.AutoDrive;

public abstract class BaseAuto extends LinearOpMode {

    AutoDrive Drive = new AutoDrive();

    protected void TurnToHeading(double Angle){
        Drive.setHeading(Angle);
        telemetry.addLine()
                .addData("Run: ", Drive.isHeadingInTolerance())
                .addData("Error :", Drive.getHeadingError())
                .addData("Output :", Drive.GyroPID.getOutput());
        telemetry.update();

        while (!Drive.isHeadingInTolerance()){
            telemetry.addLine()
                    .addData("Run: ", Drive.isHeadingInTolerance())
                    .addData("Error :", Drive.getHeadingError())
                    .addData("Output :", Drive.GyroPID.getOutput());
            telemetry.update();
            Drive.TurnToHeading();
            sleep(10);
        }
        Drive.Stop();
    }

    protected void DrivetoPosition(double Position){
        Drive.setPosition(Position);
        telemetry.addLine()
                .addData("Run: ", Drive.isPositionInTolerance())
                .addData("Error :", Drive.PositionError())
                .addData("Output :", Drive.EncPID.getOutput());
        telemetry.update();

        while (!Drive.isPositionInTolerance()){
            telemetry.addLine()
                    .addData("Run: ", Drive.isPositionInTolerance())
                    .addData("Error :", Drive.PositionError())
                    .addData("Output :", Drive.EncPID.getOutput());
            telemetry.update();
            Drive.DriveToPosition();
        }
        Drive.Stop();
    }

    @Override
    public abstract void runOpMode() throws InterruptedException;
}
