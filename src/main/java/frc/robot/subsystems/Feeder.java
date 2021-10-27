// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Feeder extends SubsystemBase {

  private final Victor master;
  /** Creates a new Feeder. */
  public Feeder() {
    master = new Victor(Constants.feederID);
    stop();
  }

  public void setPercentOutput(double percent){
    master.set(percent);
  }
  public void stop(){
    // test commit
    master.set(0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }


}
