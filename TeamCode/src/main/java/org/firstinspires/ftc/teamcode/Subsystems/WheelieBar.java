package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.hardware.rev.RevTouchSensor;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

public class WheelieBar {

    Servo Extend;
    RevTouchSensor Limit;

    public void Initialize(Servo Servo, RevTouchSensor Lim){
        Extend = Servo;
        Limit = Lim;
    }

    public void ActuateBar(boolean Out, boolean In){
        double Power = Range.clip(0.5 + (In?.5:0) - (Out?.5:0),  Limit.isPressed()?.5:0, 1); //(Out?1:0) - (In?1:0), -1, (Limit.isPressed()?0:1));
        Extend.setPosition(Power);
    }
}
