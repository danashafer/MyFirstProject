// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.poroslib.sensors.limitswitches.LimitSwitchesSystem;
import frc.poroslib.sensors.limitswitches.LimitSwitchesSystem.SysPosition;
import frc.robot.Constants;

public class Arm extends SubsystemBase {
  private final WPI_TalonSRX master;
  private final LimitSwitchesSystem limitSwitches;

  private NetworkTableEntry debugTop;
  private NetworkTableEntry debugButtom;

  /** Creates a new Arm. */
  public Arm() {
    this.master = new WPI_TalonSRX(Constants.armID);
    this.limitSwitches = new LimitSwitchesSystem(Constants.upperSwitchID, Constants.lowerSwitchID, false, false);
    this.configureSubsystem();
  }
  
  public void fillDebugTab(ShuffleboardTab debugTab){
    this.debugTop = debugTab.add("Arm Top", false).getEntry();
    this.debugButtom = debugTab.add("Arm Buttom", false).getEntry();
  }
  
  public void debugTabPeriodic(){
    this.debugTop.setBoolean(this.limitSwitches.getStatus() == SysPosition.Top ||
                              this.limitSwitches.getStatus() == SysPosition.Blocked);
    this.debugButtom.setBoolean(this.limitSwitches.getStatus() == SysPosition.Buttom ||
                              this.limitSwitches.getStatus() == SysPosition.Blocked);
  }

  private void configureSubsystem() {
    // configuration of a single motor:
    ////////////////////////////////////////////////
    // set the usual configuration:
    this.master.configFactoryDefault();

    // modify motor-direction:
    this.master.setInverted(true);
    
    // modify the mode at neutral:
    this.master.setNeutralMode(NeutralMode.Brake);
    ////////////////////////////////////////////////

    // as initialize, stop the subsystem:
    this.stop();
  }
  
  public void setPercentOutput(double percent) {
    if (this.limitSwitches.getStatus() == SysPosition.Top && percent > 0){
      this.master.set(ControlMode.PercentOutput, 0);
    }
    else if (this.limitSwitches.getStatus() == SysPosition.Buttom && percent < 0){
      this.master.set(ControlMode.PercentOutput, 0);
    }
    else{
      this.master.set(ControlMode.PercentOutput, percent);
    }
  }

  public void stop() {
    this.setPercentOutput(0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
