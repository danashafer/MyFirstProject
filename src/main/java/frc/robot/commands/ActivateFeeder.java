// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Feeder;

public class ActivateFeeder extends CommandBase {
  private final Feeder feeder;
  private final double percent;

  /** Creates a new ActivateFeeder. */
  public ActivateFeeder(Feeder feeder,double percent) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.feeder = feeder;
    this.percent = percent;
    addRequirements(feeder);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    this.feeder.setPercentOutput(this.percent);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    this.feeder.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  } 
}
