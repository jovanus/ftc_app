package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

public class ArmSystem {
    DcMotor Arm[], ExtendArm[];
    Servo Claw[];

    final static double OPEN_CLAW = 1.0, CLOSE_CLAW = 0.0;

    public void Initialize(DcMotor ArmMotors[], DcMotor ExtendMotors[], Servo ClawServos[]){
        Arm = invertOtherMotor(ArmMotors);
        ExtendArm = invertOtherMotor(ExtendMotors);
        Claw = invertOtherServo(ClawServos);
    }

    public DcMotor[] invertOtherMotor(DcMotor Motors[]){
        Motors[1].setDirection(DcMotorSimple.Direction.REVERSE);
        return  Motors;
    }

    public Servo[] invertOtherServo(Servo Servos[]){
        Servos[1].setDirection(Servo.Direction.REVERSE);
        return Servos;
    }

    public void ArmLiftPow(double Power){
        for (DcMotor x :
                Arm) {
            x.setPower(Power);
        }
    }

    public void ExtendArmPow(double Power){
        for (DcMotor x :
                ExtendArm) {
            x.setPower(Power);
        }
    }

    public void ClawPos(boolean Open){
        for (Servo x :
                Claw) {
            x.setPosition(Open ? OPEN_CLAW : CLOSE_CLAW);
        }
    }


}
