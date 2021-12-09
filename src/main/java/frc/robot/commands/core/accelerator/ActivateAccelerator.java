// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.core.accelerator;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Accelerator;

public class ActivateAccelerator extends CommandBase {
  private final Accelerator accelerator;
  private final double percent;

  /** Creates a new ActivateFeeder. */
  public ActivateAccelerator(Accelerator accelerator, double percent) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.accelerator = accelerator;
    this.percent = percent;
    addRequirements(this.accelerator);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    this.accelerator.setPercentOutput(this.percent);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    this.accelerator.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
