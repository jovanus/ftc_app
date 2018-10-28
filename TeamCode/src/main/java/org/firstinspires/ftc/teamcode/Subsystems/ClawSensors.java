package org.firstinspires.ftc.teamcode.Subsystems;

import android.graphics.Color;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DistanceSensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class ClawSensors {

    ColorSensor CS[];
    DistanceSensor DS[];

    public void Initialize(DistanceSensor[] DistanceSensors, ColorSensor[] ColorSensors){
        CS = ColorSensors;
        DS = DistanceSensors;
    }

    public void EnableLED(boolean enable){

        CS[0].enableLed(enable);
        CS[1].enableLed(enable);
        CS[2].enableLed(enable);
    }

    final static int CSCALE = 255;
    // @TODO: 10/13/2018 Determine Correct HUE

    private float[] getHSV(ColorSensor Sensor){
        float HSVVal[] = {0,0,0};
        Color.RGBToHSV((int) Sensor.red() * CSCALE, (int) Sensor.green(), Sensor.blue() * CSCALE, HSVVal);
        return HSVVal;
    }

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

    final static float YELLOW_HUE = 340, YELLOW_TOL = 10,
            WHITE_HUE = 320, WHITE_TOL = 10;
    final static double DIST = 75;
    public Colors[] determineColors(){
        Colors Output[] = {Colors.UNKNOWN, Colors.UNKNOWN, Colors.UNKNOWN};
        float[][] HSV = getHSV();
        double[] Dist = getDist();

        for (int i = 0; i < 3; i++) {
            if (Math.abs(HSV[i][0] - WHITE_HUE) < WHITE_TOL && (DIST - Dist[i]) > 0){
                Output[i] = Colors.WHITE;
            }
            else if (Math.abs(HSV[i][0] - YELLOW_HUE) < YELLOW_TOL && (DIST - Dist[i]) > 0){
                Output[i] = Colors.YELLOW;
            }
        }

        return Output;
    }

}
