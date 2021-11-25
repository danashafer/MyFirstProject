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

public class Cap extends SubsystemBase {
  private final WPI_TalonSRX master;
  private final static int maxPosition = 100;
  private final static int minPosition = 0;
  
  private int lastUsed;

  /** Creates a new Storage. */
  public Cap() {
    this.master = new WPI_TalonSRX(Constants.capID);
    this.configureSubsystem();
    
  }
  private void configureSubsystem() {
    // configuration of a single motor:
    ////////////////////////////////////////////////
    // set the usual configuration:
    this.master.configFactoryDefault();

    // modify motor-direction:
    this.master.setInverted(false);
    
    // modify the mode at neutral:
    this.master.setNeutralMode(NeutralMode.Brake);
    ////////////////////////////////////////////////
    
    // configuration of an encoder sensor:
    ////////////////////////////////////////////////
    // set the sensor which operates along with the motor:
    this.master.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute);
    
    // modify sensor-direction:
    this.master.setSensorPhase(false);
        
    // config software limits:
    this.master.configForwardSoftLimitThreshold(maxPosition);
    this.master.configReverseSoftLimitThreshold(minPosition);
    ////////////////////////////////////////////////
    
    // enable the software limits:
    this.master.configForwardSoftLimitEnable(true);
    this.master.configReverseSoftLimitEnable(true);
    
    // as initialize, stop the subsystem:
    this.stop();
    this.setLastUse();
  }

  public void setPercentOutput(double percent) {
    this.master.set(ControlMode.PercentOutput, percent);
  }
  public void stop() {
    this.setPercentOutput(0);
  }
  public void setLastUse(){
    this.lastUsed = 0;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run

  }

  
  
}
