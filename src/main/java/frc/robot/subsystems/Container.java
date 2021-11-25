// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.limitSwitches.LimitSwitch;

public class Container extends SubsystemBase {
  private final WPI_TalonSRX master;
  private final LimitSwitch limitSwitch;
  private final int maxStoragability = 5;

  private int countedObjects;
  private ShuffleboardTab subsystemsDebugTab;
  private NetworkTableEntry debugIsObjectDetected;
  /** Creates a new Storage. */
  public Container() {
    this.master = new WPI_TalonSRX(Constants.containerID);
    this.limitSwitch=new LimitSwitch(Constants.containerID, false);
    this.configureSubsystem();
  }

  public void createDebugTab(String DebugTabName){
    this.subsystemsDebugTab = Shuffleboard.getTab(DebugTabName);
    this.debugIsObjectDetected=this.subsystemsDebugTab.add("Is Ball Detected",false).getEntry();
  }

  public void debugTabPeriodic(){
    this.debugIsObjectDetected.setBoolean(this.isObjectDetected());
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
    this.resetCount();
  }
  public boolean isObjectDetected(){
    return this.limitSwitch.get();
  }

  public void resetCount(){
    this.countedObjects = 0;
  }
  public boolean isFull(){
    return this.countedObjects >= this.maxStoragability;
  }
  public void disCountObject(){
    this.countedObjects--;
  }
  public void countObject(){
    this.countedObjects++;
  }

  public void setPercentOutput(double percent) {
    this.master.set(ControlMode.PercentOutput, percent);
  }

  public void stop() {
    this.setPercentOutput(0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("object in:", this.countedObjects);

  }
}
