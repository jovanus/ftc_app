package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;

public class AutoDrive extends MechenumDrive {

    public IMU imu = new IMU();

    public PID EncPID = new PID(.33,0,0,20) {
        @Override
        protected double getInput() {
            return getPosition() - resetValue;
        }

        double resetValue = 0;
        @Override
        protected void resetInput() {
            resetValue = getPosition();
        }
    };

    public PID GyroPID = new PID(.033,0,0,20) {
        @Override
        protected double getInput() {
            return imu.getAngles().firstAngle - resetvalue;
        }

        double resetvalue = 0;
        @Override
        protected void resetInput() {
            resetvalue = imu.getAngles().firstAngle;
        }
    };

    public void initialize(DcMotor[] Motors, BNO055IMU IMUnit) {
        super.Initialize(Motors);
        imu.Initialize(IMUnit);
        EncPID.invertOutput = true;
        GyroPID.invertOutput = true;
    }

    public void StartEnc(){
        super.SetMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void ResetEnc(){
        EncPID.Reset();
    }

    public void ResetGyro(){
        GyroPID.Reset();
    }

    public void EnableSensors(){
        StartEnc();
        imu.Enable();
        GyroPID.Activate();
        EncPID.Activate();
    }

    public void ResetSensors(){
        ResetEnc();
        ResetGyro();
    }

    public void Stop(){
        Drive(0,0,0);
    }

    public void DriveToPosition(){
        Drive(EncPID.getOutput(),0,0);
    }

    public void TurnToHeading(){
        Drive(0, 0, GyroPID.getOutput());
    }

    public double PositionError(){
        return EncPID.getError();
    }

    public boolean isHeadingInTolerance(){
        return Math.abs(GyroPID.getError()) < 4;
    }

    public double getHeadingError(){
        return GyroPID.getError();
    }

    public void setPosition(double Position){
        EncPID.setSetPoint(Position);
    }

    public boolean isPositionInTolerance(){
        return Math.abs(EncPID.getError()) < 1;
    }

    public void setHeading(double Angle){
        GyroPID.setSetPoint(Angle);
    }

    public void DisableSensors(){
        imu.Disable();
        EncPID.Deactivate();
        GyroPID.Deactivate();
    }

    public void Unwind(double power){
        DriveM[0].setPower(power);
    }

}
