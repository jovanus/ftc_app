package org.firstinspires.ftc.teamcode.Subsystems;

import android.graphics.Color;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DistanceSensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class MineralDetector {
    ColorSensor CS;
    DistanceSensor DS;

    public void Initialize(ColorSensor Col, DistanceSensor Dist){
        CS = Col;
        DS = Dist;
    }

    public float[] getHSV(){
        float HSVVal[] = {0,0,0};
        Color.RGBToHSV(CS.red() * 255, CS.green() * 255, CS.blue() * 255, HSVVal);
        return  HSVVal;
    }

    public double getDist(){
        return DS.getDistance(DistanceUnit.MM);
    }

    final static float YELLOW_HUE = 25, YELLOW_TOL = 5,
            WHITE_HUE = 35, WHITE_TOL = 5;
    final static double DIST = 75;
    public Colors determineColor(){
        Colors Output[] = {Colors.UNKNOWN, Colors.UNKNOWN, Colors.UNKNOWN};
        float[] HSV = getHSV();
        if (Double.isNaN(getDist())) return Colors.UNKNOWN;

        if (Math.abs(HSV[0] - WHITE_HUE) < WHITE_TOL) return Colors.WHITE;
        else if (Math.abs(HSV[0] - YELLOW_HUE) < YELLOW_TOL) return Colors.YELLOW;
        else return  Colors.UNKNOWN;
    }
}
