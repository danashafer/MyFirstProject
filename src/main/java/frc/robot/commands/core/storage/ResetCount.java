// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.core.storage;

import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Storage;

public class ResetCount extends CommandBase {
  private final Storage storage;
  /** Creates a new ResetCount. */
  public ResetCount(Storage storage, ShuffleboardTab debugTab) {
    this.storage = storage;
    ShuffleboardLayout layout = debugTab.getLayout("StorageResetCount", BuiltInLayouts.kList);
    layout.add(this);
  }
  @Override
  public void initialize() {
    this.storage.resetCount();
  }
  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return true;
  }
}
