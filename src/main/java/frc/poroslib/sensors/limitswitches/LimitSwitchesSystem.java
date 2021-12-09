// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.poroslib.sensors.limitswitches;

/** Add your docs here. */
public class LimitSwitchesSystem {
    private final LimitSwitch switchTop;
    private final LimitSwitch switchBottum;
    
    public enum SysPosition
    {
        Top,
        Buttom,
        Free,
        Blocked;
    }

    public LimitSwitchesSystem(int channel1, int channel2, boolean isNormallyClosed1, boolean isNormallyClosed2){
        this.switchTop = new LimitSwitch(channel1, isNormallyClosed1);
        this.switchBottum = new LimitSwitch(channel2, isNormallyClosed2);
    }

    public void setTopSwitch(int channel, boolean isNormallyClosed){
        this.switchTop.setLimitSwitch(channel);
        this.switchTop.setIsNormallyClosed(isNormallyClosed);
    }

    public void setButtomSwitch(int channel, boolean isNormallyClosed){
        this.switchBottum.setLimitSwitch(channel);
        this.switchBottum.setIsNormallyClosed(isNormallyClosed);
    }

    public SysPosition getStatus(){
        if (!this.switchTop.get() && !this.switchBottum.get()){
            return SysPosition.Free;
        }
        else if (this.switchTop.get() && !this.switchBottum.get()){
            return SysPosition.Top;
        }
        else if (this.switchBottum.get() && !this.switchTop.get()){
            return SysPosition.Buttom;
        }
        else{
            return SysPosition.Blocked;
        }
    }
}
