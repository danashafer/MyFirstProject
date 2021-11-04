// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Shooter extends SubsystemBase {
  private final WPI_TalonSRX master1;
  private final WPI_TalonSRX master2;
  /** Creates a new Storage. */
  public Shooter() {
    this.master1 = new WPI_TalonSRX(Constants.shooter1ID);
    this.master2 = new WPI_TalonSRX(Constants.shooter2ID); 
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  
  }

  public void setPercentOutput(double percent){
    this.master1.set(percent);
    this.master2.set(percent);
  }

  public void stop(){
    this.setPercentOutput(0);
  }
}
