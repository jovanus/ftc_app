package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

public class ArmSystem {
    DcMotor Arm[], ExtendArm[];

    public void Initialize(DcMotor ArmMotors[], DcMotor ExtendMotors[]){
        Arm = invertOtherMotor(ArmMotors);
        ExtendArm = invertOtherMotor(ExtendMotors);
    }

    public DcMotor[] invertOtherMotor(DcMotor Motors[]){
        Motors[1].setDirection(DcMotorSimple.Direction.REVERSE);
        return  Motors;
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


}
