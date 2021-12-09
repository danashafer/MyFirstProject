// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.core.cover;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Cover;

public class ActivateCover extends CommandBase {
  private final Cover cover;
  private final double percent;

  /** Creates a new ActivateFeeder. */
  public ActivateCover(Cover cover, double percent) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.cover = cover;
    this.percent = percent;
    addRequirements(this.cover);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    this.cover.setPercentOutput(this.percent);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    this.cover.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
