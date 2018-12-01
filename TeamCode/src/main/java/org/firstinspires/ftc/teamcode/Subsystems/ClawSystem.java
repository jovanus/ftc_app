package org.firstinspires.ftc.teamcode.Subsystems;

import android.graphics.Color;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.sun.tools.javac.comp.Todo;


public class ClawSystem {
    Servo Claw[];


    final static double CLOSE_LEFT = 1.0, CLOSE_RIGHT = 0.0, OPEN_CLAW = 0.5;

    public void Initialize(Servo ClawServos[]){
        Claw = ClawServos;
        Claw[0].setDirection(Servo.Direction.REVERSE);

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

    final static double SOPEN_CLAW = 0.3;
    final static double SCLOSE_CLAW = 1.0;
    public void SimpleOpenClose(boolean OpenAll, boolean CloseAll, boolean OpenLeft, boolean OpenRight){
        if (OpenAll) {
            Claw[0].setPosition(SOPEN_CLAW);
            Claw[1].setPosition(SOPEN_CLAW);
        }

        if (CloseAll) {
            Claw[0].setPosition(SCLOSE_CLAW);
            Claw[1].setPosition(SCLOSE_CLAW);
        }

        if (OpenLeft){
            Claw[0].setPosition(SOPEN_CLAW);
        }

        if (OpenRight){
            Claw[1].setPosition(SOPEN_CLAW);
        }
    }



}