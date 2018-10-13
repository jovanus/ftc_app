package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.Range;
import com.sun.tools.javac.util.ArrayUtils;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.Arrays;
import java.util.Collections;

import static java.util.Arrays.*;

public class MechenumDrive {

    DcMotor DriveM[];
    private final double OUTPUT_SCALE_FACTOR = 1.0;


    /******************************************
     * Initialize with a motor array
     * @param Motors Front -> Rear, Left -> Right in order of initialization
     */
    public void Initialize(DcMotor[] Motors){
        DriveM = Motors;
        DriveM[2].setDirection(DcMotorSimple.Direction.REVERSE);
    }

    /*PROTOTYPE
    public void RunAtVoltage(double Voltage, double CurrentVoltage){
        FL.setPower(Voltage/CurrentVoltage);
    }
    */

    public double[] Drive(double ForwardPower, double LateralPower, double RotationalPower){
        double x = deadzone(LateralPower);
        double y = -deadzone(ForwardPower);
        double r = deadzone(RotationalPower);

        double wheelSpeeds[] = {y + x + r, y - x - r, y - x + r, y + x - r};

        normalize(wheelSpeeds);
        scale(wheelSpeeds, OUTPUT_SCALE_FACTOR);

        for (int i = 0; i < 4; i++) {
            DriveM[i].setPower(Range.clip(wheelSpeeds[i],-1,1));
        }

        return wheelSpeeds;
    }

    private double deadzone(double power) {
        return Math.abs(power) > 0.2 ? power : 0.0 ;
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
