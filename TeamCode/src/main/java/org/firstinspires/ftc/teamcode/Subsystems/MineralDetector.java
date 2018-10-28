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

    final static float YELLOW_HUE = 25, YELLOW_TOL = 5,
            WHITE_HUE = 35, WHITE_TOL = 5;
    public Colors determineColor(){
        Colors Output[] = {Colors.UNKNOWN, Colors.UNKNOWN, Colors.UNKNOWN};
        float[] HSV = getHSV();
        if (Double.isNaN(getDist())) return Colors.UNKNOWN;

        if (Math.abs(HSV[0] - WHITE_HUE) < WHITE_TOL) return Colors.WHITE;
        else if (Math.abs(HSV[0] - YELLOW_HUE) < YELLOW_TOL) return Colors.YELLOW;
        else return  Colors.UNKNOWN;
    }

    public void GoToPos(double ServoPos){
        Arm.setPosition(ServoPos);
    }

}
