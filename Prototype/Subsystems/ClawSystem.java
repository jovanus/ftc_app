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


public class ClawSystem {
    Servo Claw[];


    final static double LEFT_CLOSE_CLAW = 1.0, RIGHT_CLOSE_CLAW = 0.0, OPEN_CLAW = 0.5;

    public void Initilize(Servo ClawServos[], ColorSensor ColorSensors[], DistanceSensor DistanceSensors[]){
        Claw = ClawServos;

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



}