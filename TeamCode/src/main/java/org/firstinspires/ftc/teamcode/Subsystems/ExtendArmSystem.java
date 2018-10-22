package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.hardware.rev.RevTouchSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.Range;

public class ExtendArmSystem {

    private DcMotor[] ExtendArm;
    private RevTouchSensor[] LimitsExtend;

    public void Initialize(DcMotor ExtendMotors[], RevTouchSensor ExtendLimits[]){
        ExtendArm = ExtendMotors;
        ExtendArm[1].setDirection(DcMotorSimple.Direction.REVERSE);
        LimitsExtend = ExtendLimits;//0 = Out, 1 = In
    }

    public void Power(double Power){
        for (DcMotor x :
                ExtendArm) {
            //If Out Sensor is pressed, only allow values 0 - -1.  If in sensor is pressed, only allow values 0 - 1
            double limPower = Range.clip(Power, LimitsExtend[1].isPressed() ? 0 : -1, LimitsExtend[0].isPressed() ? 0 : 1);
            x.setPower(limPower);

        }
    }

    public boolean[] TouchStatus(){
        boolean ExtendLims[] = {LimitsExtend[0].isPressed(),
                LimitsExtend[1].isPressed()};
        return ExtendLims;
    }

}
