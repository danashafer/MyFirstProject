// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.core.storage;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Storage;

public class DebugActivateStorage extends CommandBase {
  private final Storage storage;

  private NetworkTableEntry debugTargetPercentOutput;
  private NetworkTableEntry debugEncoderVelocity;
  private double percent;

  /** Creates a new DebugActivateStorage. */
  public DebugActivateStorage(Storage storage, ShuffleboardTab debugTab) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(storage);
    this.storage = storage;
    ShuffleboardLayout layout = debugTab.getLayout("DebugActivateStorage", BuiltInLayouts.kList);

    layout.add(this);
    this.debugTargetPercentOutput = layout.add("percent", 0.0).getEntry();
    this.debugEncoderVelocity = layout.add("velocity", 0.0).getEntry();
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    this.percent = this.debugTargetPercentOutput.getDouble(0.0);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    this.storage.setPercentOutput(this.percent);
    this.debugEncoderVelocity.setDouble(this.storage.getVelocity());
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
