package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.Servo;

public class DetectBats {
    Servo[] Bats;

    public void Initialize(Servo[] bats){
        Bats = bats;
        Bats[1].setDirection(Servo.Direction.REVERSE);
    }

    private final static double IN = 0.95f, OUT = 0.0f;
    public double SetLeft(double Pos){
        Bats[0].setPosition(Pos);
        return Pos;
    }
    public void LeftBat(boolean Activate){
        SetLeft(Activate ? OUT : IN);
    }

    public double SetRight(double Pos){
        Bats[1].setPosition(Pos);
        return Pos;
    }
    public void RightBat(boolean Activate){
        SetRight(Activate ? OUT : IN);
    }

    public void Bat(boolean Left, boolean Right){
        LeftBat(Left);
        RightBat(Right);
    }

}
