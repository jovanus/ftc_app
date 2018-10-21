package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.hardware.rev.RevTouchSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

public class ArmSystem {
    DcMotor Arm[], ExtendArm[];
    RevTouchSensor LimitsArm[], LimitsExtend[];

    public void Initialize(DcMotor ArmMotors[], DcMotor ExtendMotors[], RevTouchSensor ArmLimits[], RevTouchSensor ExtendLimits[]){
        Arm = invertOtherMotor(ArmMotors);
        ExtendArm = invertOtherMotor(ExtendMotors);
        LimitsArm = ArmLimits;//0 = Up, 1 = Down
        LimitsExtend = ExtendLimits;//0 = Out, 1 = In
    }

    public DcMotor[] invertOtherMotor(DcMotor Motors[]){
        Motors[1].setDirection(DcMotorSimple.Direction.REVERSE);
        return  Motors;
    }

    public void ArmLiftPow(double Power){
        for (DcMotor x :
                Arm) {
            //If Up Sensor is pressed, only allow values 0 - -1.  If Down sensor is pressed, only allow values 0 - 1
            double limPower = Range.clip(Power, LimitsArm[1].isPressed() ? 0 : -1, LimitsArm[0].isPressed() ? 0 : 1);
            x.setPower(limPower);
        }
    }

    public void ExtendArmPow(double Power){
        for (DcMotor x :
                ExtendArm) {
            //If Out Sensor is pressed, only allow values 0 - -1.  If in sensor is pressed, only allow values 0 - 1
            double limPower = Range.clip(Power, LimitsExtend[1].isPressed() ? 0 : -1, LimitsExtend[0].isPressed() ? 0 : 1);
            x.setPower(limPower);

        }
    }

    public boolean[] CurrentTouchStatus(){
        boolean javaSuks[] = {LimitsExtend[0].isPressed(),
                LimitsExtend[1].isPressed(),
                LimitsArm[0].isPressed(),
                LimitsArm[1].isPressed()};
        return javaSuks;
    }


}
