package org.firstinspires.ftc.teamcode.Subsystems;

import android.graphics.Color;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.sun.tools.javac.comp.Todo;

/**
 * Created by SorenTech on 10/13/2018.
 */

enum ClawPos {LEFT, OPEN, RIGHT}
enum Colors {WHITE, YELLOW, UNKNOWN}

public class ClawSystem {
    Servo Claw[];
    ColorSensor CS[];
    DistanceSensor DS[];

    final static double LEFT_CLOSE_CLAW = 1.0, RIGHT_CLOSE_CLAW = 0.0, OPEN_CLAW = 0.5;

    public void Initilize(Servo ClawServos[], ColorSensor ColorSensors[], DistanceSensor DistanceSensors[]){
        Claw = ClawServos;
        CS = ColorSensors;
        DS = DistanceSensors;
    }

    public double ClawPos(ClawPos Pos){
        double setPosition;
        switch (Pos){
            case LEFT:
                setPosition = LEFT_CLOSE_CLAW;
                break;
            case OPEN:
            default:
                setPosition = OPEN_CLAW;
                break;
            case RIGHT:
                setPosition = RIGHT_CLOSE_CLAW;
                break;
        }
        for (Servo x :
                Claw) {
            x.setPosition(setPosition);
        }
        return setPosition;
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

}