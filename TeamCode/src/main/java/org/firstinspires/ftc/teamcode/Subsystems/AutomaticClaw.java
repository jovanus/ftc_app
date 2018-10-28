package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;

import java.util.Arrays;

public class AutomaticClaw {
    ClawSystem Claw;
    ClawSensors Sensors;

    public void Initialize(Servo[] CServos, ColorSensor[] CSensors, DistanceSensor[] DSensors){
        Claw = new ClawSystem();
        Sensors = new ClawSensors();
        Claw.Initialize(CServos);
        Sensors.Initialize(DSensors, CSensors);
    }

    public void CloseClaw(){
        Claw.ClawPos(DeterminePosition(ParseColors()));
    }

    public void OpenClaw(){
        Claw.ClawPos(ClawPos.OPEN);
    }

    public boolean[] ParseColors(){
        int WhiteCount = 0;
        int YellowCount = 0;
        boolean[] WhiteLocation = new boolean[3];
        boolean[] YellowLocation = new boolean[3];
        Colors[] x = Sensors.determineColors();
        for (int i = 0; i < x.length; i++) {
            if (x[i] == Colors.YELLOW){
                YellowCount++;
                YellowLocation[i] = true;
            }
            else if (x[i] == Colors.WHITE){
                WhiteCount++;
                WhiteLocation[i] = true;
            }
        }

        return (YellowCount > WhiteCount ? YellowLocation : WhiteLocation);
    }

    public final static boolean[][] CHECKLIST =
            {{true, true, false}, // Left 2
                    {false, true, true}, // Right 2
                    {false, true, false}, // Center
                    {true, false, true}, // Outside
                    {true, false, false}, // Left 1
                    {false, false, true}, // Right 1
                    {true, true, true},   // Right 2
                    {false, false, false}}; // Open


    public final static ClawPos[] OUTLIST = {
            ClawPos.LEFT2,
            ClawPos.RIGHT2,
            ClawPos.CENT,
            ClawPos.OUTSIDE,
            ClawPos.LEFT1,
            ClawPos.RIGHT1,
            ClawPos.RIGHT2,
            ClawPos.OPEN
    };

    public ClawPos DeterminePosition(boolean[] CloseLocation){
        for (int i = 0; i < OUTLIST.length; i++) {
            if (Arrays.equals(CloseLocation,CHECKLIST[i])) {
                return OUTLIST[i];
            }
        }
        return ClawPos.OPEN;
    }

}
