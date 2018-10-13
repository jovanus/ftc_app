package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by SorenTech on 10/13/2018.
 */

public class ClawSystem {
    Servo Claw[];

    final static double OPEN_CLAW = 1.0, CLOSE_CLAW = 0.0;

    public void Initilize(Servo ClawServos[]){
        Claw = invertOtherServo(ClawServos);
    }

    public Servo[] invertOtherServo(Servo Servos[]){
        Servos[1].setDirection(Servo.Direction.REVERSE);
        return Servos;
    }

    public void ClawPos(boolean Open){
        for (Servo x :
                Claw) {
            x.setPosition(Open ? OPEN_CLAW : CLOSE_CLAW);
        }
    }
}
