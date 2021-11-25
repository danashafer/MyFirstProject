// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class Accelerator  extends SubsystemBase {
  private final WPI_TalonSRX masterRight;
  private final WPI_TalonSRX slaveLeft;

  /** Creates a new Accelerator. */
  public Accelerator() {
    this.masterRight = new WPI_TalonSRX(Constants.acceleratorRightID);
    this.slaveLeft = new WPI_TalonSRX(Constants.acceleratorLeftID);
    this.configureSubsystem();
    this.stop();
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

  public void setPercentOutput(double percent) {
    this.masterRight.set(ControlMode.PercentOutput, percent);
    this.slaveLeft.set(ControlMode.PercentOutput, percent);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void stop() {
    this.setPercentOutput(0);
  }
}
