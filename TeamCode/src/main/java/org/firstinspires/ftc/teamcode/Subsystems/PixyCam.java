package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.I2cDeviceSynch;

public class PixyCam {

    I2cDeviceSynch Pixy;
    I2cDeviceSynch.ReadWindow readWindow;

    public void Initialize(I2cDeviceSynch pixy){
        Pixy = pixy;
        Pixy.setI2cAddress(I2cAddr.create7bit(0x54));

        readWindow = new I2cDeviceSynch.ReadWindow(1, 26, I2cDeviceSynch.ReadMode.REPEAT);
        Pixy.setReadWindow(readWindow);
    }

    public void start(){
        Pixy.engage();
    }

    public byte[] ReadData(){
        byte[] var = new byte[14];
        for (int i = 0; i < 14; i++) {
            var[i] = Pixy.read8(i);
        }

        return var;
    }




}
