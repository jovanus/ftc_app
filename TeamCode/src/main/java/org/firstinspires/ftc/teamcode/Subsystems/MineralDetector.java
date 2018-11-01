package org.firstinspires.ftc.teamcode.Subsystems;

import android.graphics.Color;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class MineralDetector {
    ColorSensor CS;
    DistanceSensor DS;
    Servo Arm;

    public void Initialize(Servo AServ, ColorSensor Col, DistanceSensor Dist){
        CS = Col;
        DS = Dist;
        Arm = AServ;
        Arm.setDirection(Servo.Direction.REVERSE);
    }

    final static int CSCALE = 255;
    public float[] getHSV(){
        float HSVVal[] = {0,0,0};
        Color.RGBToHSV(CS.red() * CSCALE, CS.green() * CSCALE, CS.blue() * CSCALE, HSVVal);
        return  HSVVal;
    }

    public double getDist(){
        return DS.getDistance(DistanceUnit.MM);
    }

    final static float YELLOW_HUE = 25.0f, YELLOW_TOL = 5.0f, YELLOW_VAL = 35f,
            WHITE_HUE = 35f, WHITE_TOL = 5f, WHITE_VAL = 60f, YELLOW_SAT = 0.6f, WHITE_SAT = 0.25f, SAT_TOL = 0.2f;
    public Colors determineColor(){
        Colors Output[] = {Colors.UNKNOWN, Colors.UNKNOWN, Colors.UNKNOWN};
        float[] HSV = getHSV();
        if (Math.abs(HSV[1] - WHITE_SAT) < SAT_TOL  && getDist() < 100) return Colors.WHITE;
        else if (Math.abs(HSV[1] - YELLOW_SAT) < SAT_TOL  && getDist() < 100) return Colors.YELLOW;
        else return  Colors.UNKNOWN;
    }

    public void GoToPos(double ServoPos){
        Arm.setPosition(ServoPos);
    }

}
