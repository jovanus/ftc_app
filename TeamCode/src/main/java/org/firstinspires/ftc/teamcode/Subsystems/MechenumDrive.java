package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.Range;
import com.sun.tools.javac.util.ArrayUtils;

import java.util.Arrays;
import java.util.Collections;

import static java.util.Arrays.*;

public class MechenumDrive {

    DcMotor DriveM[];
    private final double OUTPUT_SCALE_FACTOR = 1.0;

    private void SetupMotors(){
        DriveM[1].setDirection(DcMotorSimple.Direction.REVERSE);
        DriveM[3].setDirection(DcMotorSimple.Direction.REVERSE);
    }


    /******************************************
     * Initialize with a motor array
     * @param Motors Front -> Rear, Left -> Right in order of initialization
     */
    public void Initialize(DcMotor[] Motors){
        DriveM = Motors;
        SetupMotors();
    }
    public void Initialize(DcMotor FrontLeft, DcMotor FrontRight, DcMotor RearLeft, DcMotor RearRight){
        DriveM[0] = FrontLeft;
        DriveM[1] = FrontRight;
        DriveM[2] = RearLeft;
        DriveM[3] = RearRight;
        SetupMotors();
    }

    /*PROTOTYPE
    public void RunAtVoltage(double Voltage, double CurrentVoltage){
        FL.setPower(Voltage/CurrentVoltage);
    }
    */

    public void Drive(double ForwardPower, double LateralPower, double RotationalPower){
        double x = deadzone(LateralPower);
        double y = deadzone(ForwardPower);
        double r = deadzone(RotationalPower);

        double wheelSpeeds[] = {x + y + r, -x + y - r, -x + y + r, x + y - r};

        normalize(wheelSpeeds);
        scale(wheelSpeeds, OUTPUT_SCALE_FACTOR);

        int i = 0;
        for (DcMotor t :
                DriveM) {
            t.setPower(Range.clip(wheelSpeeds[i],-1,1));
        }
    }

    private double deadzone(double power) {
        return Math.abs(power) > 0.3 ? power : 0.0 ;
    }

    private static void scale(double wheelSpeeds[], double scaleFactor) {
        for (double x :
                wheelSpeeds) {
            x = x * scaleFactor;
        }
    }

    private static void normalize(double wheelSpeeds[]) {
        double maxMagnitude = Math.abs(wheelSpeeds[0]);
        for (double p :
                wheelSpeeds) {
            maxMagnitude = Math.max(maxMagnitude, p);
        }

        if (maxMagnitude > 1.0) {
            for (double q :
                    wheelSpeeds) {
                q = q / maxMagnitude;
            }
        }
    }



}
