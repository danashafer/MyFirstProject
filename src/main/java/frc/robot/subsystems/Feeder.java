// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Feeder extends SubsystemBase {

  private final Victor master;
  private int lastUsed = 0;
  /** Creates a new Feeder. */
  public Feeder() {
    this.master = new Victor(Constants.feederID);
    this.setLastUse();
    this.stop();
  }

  public void setPercentOutput(double percent){
    this.master.set(percent);
  }
  public void stop(){
    // test commit
    this.setPercentOutput(0);
  }

  public void setLastUse(){
    this.lastUsed=0;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    if(this.lastUsed > 9){
      this.stop();
    }
    else{
      this.lastUsed++;
    }
  } 
}
