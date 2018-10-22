package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.Range;

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

    public double[] getEncoders(){
        double EncVal[] = new double[4];
        for (int i = 0; i < 4; i++) {
            EncVal[i] = DriveM[i].getCurrentPosition();
        }
        return EncVal;
    }

    public final static double ENC_SCALE = Math.PI * 4 / ( 8 * 280);
    public double[] getPosition(){
        double[] Position = getEncoders();

        for (double i : Position) {
            i = i * ENC_SCALE;
        }
        return Position;
    }

    public boolean HasEncodersReset(){

        for (double i :
                getPosition()) {
            if (i != 0) return false;
        }
        return true;
    }

    public void SetMode(DcMotor.RunMode SMode){
        for (DcMotor i:
             DriveM) {
            i.setMode(SMode);
        }
    }


}
