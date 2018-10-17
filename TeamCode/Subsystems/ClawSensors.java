package org.firstinspires.ftc.teamcode.Subsystems;

import android.graphics.Color;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DistanceSensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

enum Colors {WHITE, YELLOW, UNKNOWN}

public class ClawSensors {

    ColorSensor CS[];
    DistanceSensor DS[];

    public void Initialize(DistanceSensor[] DistanceSensors, ColorSensor[] ColorSensors){
        CS = ColorSensors;
        DS = DistanceSensors;
    }

    final static int CSCALE = 255;
    // @TODO: 10/13/2018 Determine Correct HUE
    final static float YELLOW_HUE = 0, HUE_TOL = 5,
            WHITE_VAL = 255, WHITE_TOL = 5;
    private float[] getHSV(ColorSensor Sensor){
        float HSVVal[] = {0,0,0};
        Color.RGBToHSV((int) Sensor.red() * CSCALE, (int) Sensor.green(), Sensor.blue() * CSCALE, HSVVal);
        return HSVVal;
    }

    /*
    public Colors[] getColors(){
        Colors Output[] = new Colors[3];
        for (int i = 0; i < 4; i++) {
            Output[i] = determineColor(CS[i]);
        }
        return Output;
    }
    */

    public float[][] getHSV(){
        float Output[][] = new float[3][3];
        for (int i = 0; i < 3; i++) {
            Output[i] = getHSV(CS[i]);
        }
        return Output;
    }

    public double[] getDist(){
        double dist[] = {0,0,0};
        for (int i = 0; i < 3; i++) {
            dist[i] = DS[i].getDistance(DistanceUnit.MM);
        }
        return dist;
    }

}
