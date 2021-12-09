// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.core.storage;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Storage;

public class ActivateStorage extends CommandBase {
  private final Storage storage;
  private final double percent;

  /** Creates a new ActivateFeeder. */
  public ActivateStorage(Storage storage, double percent) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.storage = storage;
    this.percent = percent;
    addRequirements(this.storage);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    this.storage.setPercentOutput(this.percent);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    this.storage.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
