// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Storage extends SubsystemBase {
  private final WPI_TalonSRX master;
  /** Creates a new Storage. */
  public Storage() {
    this.master = new WPI_TalonSRX(Constants.storageID); 
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  
  }

  public void setPercentOutput(double percent){
    this.master.set(percent);
  }

  public void stop(){
    this.setPercentOutput(0);
  }
}
