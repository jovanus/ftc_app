package org.firstinspires.ftc.teamcode.Subsystems;

import android.graphics.Color;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.sun.tools.javac.comp.Todo;


public class ClawSystem {
    Servo Claw[];


    final static double CLOSE_LEFT = 1.0, CLOSE_RIGHT = 0.0, OPEN_CLAW = 0.5;

    public void Initilize(Servo ClawServos[]){
        Claw = ClawServos;

    }

    public double[] ClawPos(ClawPos Pos){
        double[] setPos = {0.5,0.5};
        switch (Pos){
            case LEFT1:
                setPos[0] = CLOSE_LEFT;
                setPos[1] = OPEN_CLAW;
                break;
            case LEFT2:
                setPos[0] = CLOSE_LEFT;
                setPos[1] = CLOSE_LEFT;
                break;
            case CENT:
                setPos[0] = CLOSE_RIGHT;
                setPos[1] = CLOSE_LEFT;
                break;
            case RIGHT2:
                setPos[0] = CLOSE_RIGHT;
                setPos[1] = CLOSE_RIGHT;
                break;
            case RIGHT1:
                setPos[0] = OPEN_CLAW;
                setPos[1] = CLOSE_RIGHT;
                break;
            case OUTSIDE:
                setPos[0] = CLOSE_LEFT;
                setPos[1] = CLOSE_RIGHT;
                break;
            case OPEN:
            default:
                setPos[0] = OPEN_CLAW;
                setPos[1] = OPEN_CLAW;
                break;
        }

        for (int i = 0; i < 2; i++) {
            Claw[i].setPosition(setPos[i]);
        }

        return setPos;
    }



}