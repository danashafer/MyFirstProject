// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.poroslib.sensors.limitswitches.LimitSwitch;
import frc.robot.Constants;

public class Storage extends SubsystemBase {
  private final WPI_TalonSRX master;
  private final LimitSwitch limitSwitch;

  private final int maxStoragability = 5;
  private int countedObjects;

  // PIDF Control values
  private final int profileSlotID = 0;
  private final double kP = 0.5;
  private final double kI = 0.001;
  private final double kD = 0.3;
  private final double kF = 0;

  private NetworkTableEntry debugIsObjectDetected;
  
  /** Creates a new Storage. */
  public Storage() {
    this.master = new WPI_TalonSRX(Constants.storageID);
    this.limitSwitch = new LimitSwitch(Constants.infraRedID, false);
    this.configureSubsystem();
  }

  public void fillDebugTab(ShuffleboardTab debugTab){
    this.debugIsObjectDetected = debugTab.add("Is Object Detected", false).getEntry();
  }
  
  public void debugTabPeriodic(){
    this.debugIsObjectDetected.setBoolean(this.isObjectDetected());
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("objects in:", this.countedObjects);
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

    // config profile slot:
    this.configPIDFSlot(this.profileSlotID, this.kP, this.kI, this.kD, this.kF);

    // as initialize, stop the subsystem:
    this.stop();
    this.resetCount();
  }
  
  public void configPIDFSlot(int profileSlot, double kP, double kI, double kD, double kF) {
    this.master.config_kP(profileSlot, kP);
    this.master.config_kI(profileSlot, kI);
    this.master.config_kD(profileSlot, kD);
    this.master.config_kF(profileSlot, kF);
  }

  public void setVelocity(double velocity){
    this.master.selectProfileSlot(this.profileSlotID, 0);
    this.master.set(ControlMode.Velocity, velocity);
  }

  public void setPercentOutput(double percent) {
    this.master.set(ControlMode.PercentOutput,percent);
  }

  public void stop() {
    this.setPercentOutput(0);
  }

  public boolean isObjectDetected(){
    return this.limitSwitch.get();
  }

  public void countObject(){
    this.countedObjects++;
  }

  public void disCountObject(){
    this.countedObjects--;
  }

  public void resetCount(){
    this.countedObjects = 0;
  }

  public int getCount() {
    return this.countedObjects;
  }

  public boolean isFull(){
    return this.countedObjects >= this.maxStoragability;
  }

  public double getVelocity() {
    return this.master.getSelectedSensorVelocity();
  }

  public int getProfileSlot() {
    return this.profileSlotID;
  }
}
