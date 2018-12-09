package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.hardware.rev.RevTouchSensor;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

public class ArmSystem {
    DcMotor Arm[];
    RevTouchSensor LimitsArm[];
    AnalogInput Potentiometer;

    public void Initialize(DcMotor ArmMotors[], RevTouchSensor ArmLimits[], AnalogInput Pot){
        Arm = ArmMotors;
        LimitsArm = ArmLimits;//0 = Up, 1 = Down
        Potentiometer = Pot;
    }


    public void Power(double Power){
        for (DcMotor x :
                Arm) {
            //If Up Sensor is pressed, only allow values 0 - -1.  If Down sensor is pressed, only allow values 0 - 1
            double limPower = Range.clip(Power, GetPotVoltage() < 0.5d ? 0 : -1,  1);
            x.setPower(limPower);
        }
    }

    public double GetPotVoltage(){
        return Potentiometer.getVoltage();
    }

    public boolean[] TouchStatus(){
        boolean ArmLims[] = {
                LimitsArm[0].isPressed(),
                LimitsArm[1].isPressed()};
        return ArmLims;
    }


    public void Stop() {
        Power(0);
    }
}
