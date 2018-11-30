package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.Servo;

public class DetectBats {
    Servo[] Bats;

    public void Initialize(Servo[] bats){
        Bats = bats;
        Bats[1].setDirection(Servo.Direction.REVERSE);
    }

    private final static double IN = 0.0f, OUT = 0.75f;
    public void LeftBat(boolean Activate){
        Bats[0].setPosition(Activate ? OUT : IN);
    }

    public void RightBat(boolean Activate){
        Bats[1].setPosition(Activate ? OUT : IN);
    }
}
