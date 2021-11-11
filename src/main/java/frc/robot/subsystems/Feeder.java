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
  private int lastUsed = 0;

  /** Creates a new Feeder. */
  public Feeder() {
    this.master = new VictorSPX(Constants.feederID);
    this.setLastUse();
    this.configureSubsystem();
  }

  private void configureSubsystem() {
    // reverse direction
    this.master.setInverted(true);
    // blocked on neutral
    this.master.setNeutralMode(NeutralMode.Brake);
    this.stop();
  }

  public void setPercentOutput(double percent) {
    this.master.set(ControlMode.PercentOutput, percent);
  }

  public void stop() {
    // test commit
    this.setPercentOutput(0);
  }

  public void setLastUse() {
    this.lastUsed = 0;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    if (this.lastUsed > 9) {
      this.stop();
    } else {
      this.lastUsed++;
    }
  }
}
