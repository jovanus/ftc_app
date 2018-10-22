package org.firstinspires.ftc.teamcode.Subsystems;

/**
 * Created by ericw on 10/21/2017.
 */

public abstract class PID {
    double kP;
    double kI;
    double kD;
    double setPoint;
    double curIError;
    double curDError;
    double error;
    double lastError;
    long sleepTime;
    long prevTime;
    Thread T;
    boolean active;
    boolean invertOutput = false;


    /**********************************
     * Constructor - Set constants and sleep timer
     * @param kP The Proportional constant, reacts linearly to error
     * @param kI The Integral constant, calculates cumulative error of each cycle
     * @param kD The Derivative constant, calculates the change in error of each cycle
     * @param sleepT How quickly the input will update the error, try to match with sensor poling
     */
    public PID(double kP, double kI, double kD, long sleepT){
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
        this.sleepTime = sleepT;

        T = new Thread(new Runnable() {
            @Override
            public void run() {
                while (active){
                    error();
                    try {Thread.sleep(sleepTime);
                    } catch (InterruptedException e){}
                }
            }
        });

    }

    /********************************
     * Required to feed the error
     * @return Set as the variable which will be read. Be sure to make sure it will update.
     */
    protected abstract double getInput();

    /********************************
     * Required to be able to reset the value of sensor to zero when PID tries to reset.
     */
    protected abstract void resetInput();

    /********************************
     * Start, Stop, and Reset methods.
     */
    public void Activate(){
        this.active = true;
        T.start();
    }

    public void Deactivate(){
        this.active = false;
    }

    public void Reset(){
        curIError = 0;
        curDError = 0;
        lastError = 0;
        resetInput();
    }

    /**************************************
     * Setters
     */
    public void setkP(double kP) {
        this.kP = kP;
    }

    public void setkI(double kI) {
        this.kI = kI;
    }

    public void setkD(double kD) {
        this.kD = kD;
    }

    public void setkPID(double kP, double kI, double kD){
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;}

    public void setSetPoint(double setPoint) {
        this.setPoint = setPoint;
    }

    public void setInvertOutput(boolean invertOutput) {
        this.invertOutput = invertOutput;
    }

    /**************************************
     * Getters
     */
    public double getOutput() {
        return (invertOutput ? -1:1)*(kP*error + kI* curIError + kD*(error- lastError));
    }

    public double getError() {
        return error;
    }

    /**************************************
     * Error Calculation - Don't do extraneous calculations if required.
     */

    private void error(){
        error = setPoint - getInput();
        if (kI != 0) calcIError();
        if (kD != 0) curDError = error - this.lastError;
        this.lastError = error;
    }

    private void calcIError(){
        long CurrentTime = System.currentTimeMillis();
        curIError += error * (double)(CurrentTime- prevTime);
        prevTime = CurrentTime;
    }


}
