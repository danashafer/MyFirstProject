// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Accelerator extends SubsystemBase {
  private final WPI_TalonSRX masterRight;
  private final WPI_TalonSRX slaveLeft;

  private int targetVelocity;

    // PIDF Control values
    private final int profileSlotID = 0;
    private final double kP = 0.5;
    private final double kI = 0.001;
    private final double kD = 0.3;
    private final double kF = 0;

  /** Creates a new Arm. */
  public Accelerator() {
    this.masterRight = new WPI_TalonSRX(Constants.acceleratorRightID);
    this.slaveLeft = new WPI_TalonSRX(Constants.acceleratorLeftID);
    this.configureSubsystem();
  }

  private void configureSubsystem() {
    // configuration of a single motor:
    ////////////////////////////////////////////////
    // set the usual configuration:
    this.masterRight.configFactoryDefault();
    this.slaveLeft.configFactoryDefault();

    // modify motor-direction:
    this.masterRight.setInverted(false);
    this.slaveLeft.setInverted(true);
    
    // modify the mode at neutral:
    this.masterRight.setNeutralMode(NeutralMode.Coast);
    this.slaveLeft.setNeutralMode(NeutralMode.Coast);
    ////////////////////////////////////////////////
    // configuration of an encoder sensor:
    ////////////////////////////////////////////////
    // set the sensor which operates along with the motor:
    this.masterRight.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute);
    this.slaveLeft.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute);
    
    // modify sensor-direction:
    this.masterRight.setSensorPhase(false);
    this.slaveLeft.setSensorPhase(false);
    ////////////////////////////////////////////////
    this.slaveLeft.follow(this.masterRight);

    // as initialize, stop the subsystem:
    this.stop();
  }

  public void setVelocity(double velocity){
    this.masterRight.selectProfileSlot(this.profileSlotID, 0);
    this.masterRight.set(ControlMode.Velocity, velocity);
    this.slaveLeft.selectProfileSlot(this.profileSlotID, 0);
    this.slaveLeft.set(ControlMode.Velocity, velocity);
  }

  public void configPIDFSlot(double kP, double kI, double kD, double kF) {
    this.masterRight.config_kP(this.profileSlotID, kP);
    this.masterRight.config_kI(this.profileSlotID, kI);
    this.masterRight.config_kD(this.profileSlotID, kD);
    this.masterRight.config_kF(this.profileSlotID, kF);
    this.slaveLeft.config_kP(this.profileSlotID, kP);
    this.slaveLeft.config_kI(this.profileSlotID, kI);
    this.slaveLeft.config_kD(this.profileSlotID, kD);
    this.slaveLeft.config_kF(this.profileSlotID, kF);
  }

  public void setPercentOutput(double percent) {
    this.masterRight.set(ControlMode.PercentOutput, percent);
  }

  public double getVelocity() {
    return this.masterRight.getSelectedSensorVelocity();
  }
  
  public void stop() {
    this.setPercentOutput(0);
  }

  public double getRightVelocity(){
    return this.masterRight.getSelectedSensorVelocity();
  }

  public double getLeftVelocity(){
    return this.slaveLeft.getSelectedSensorVelocity();
  }

  public double getAvarageVelocity(){
    return (this.getRightVelocity() + this.getLeftVelocity()) / 2;
  }

  public boolean atTarget(){
    return Math.abs(this.getAvarageVelocity() - this.targetVelocity) < 60;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
