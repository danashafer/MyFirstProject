// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Feeder extends SubsystemBase {
  private final VictorSPX master;
  
  /** Creates a new Feeder. */
  public Feeder() {
    this.master = new VictorSPX(Constants.feederID);
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

    // as initialize, stop the subsystem:
    this.stop();
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
  }
}
