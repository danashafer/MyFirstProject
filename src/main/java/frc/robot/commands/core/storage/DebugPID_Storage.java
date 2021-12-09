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

public class DebugPID_Storage extends CommandBase {
  private final Storage storage;
  
  private NetworkTableEntry targetVelocityEntry;
  private NetworkTableEntry kpEntry;
  private NetworkTableEntry kiEntry;
  private NetworkTableEntry kdEntry;
  private NetworkTableEntry kfEntry;
  private NetworkTableEntry currentVelocity;

  private double targetVelocity;
  
  /** Creates a new PID_Accelerator. */
  public DebugPID_Storage(Storage storage, ShuffleboardTab debugTab) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(storage);
    this.storage = storage;

    ShuffleboardLayout layout = debugTab.getLayout("DebugPID_Storage", BuiltInLayouts.kList);
    layout.add(this);
    this.targetVelocityEntry = layout.add("target_velocity", 0.0).getEntry();
    this.kpEntry = layout.add("kP", 0.0).getEntry();
    this.kiEntry = layout.add("kI", 0.0).getEntry();
    this.kdEntry = layout.add("kD", 0.0).getEntry();
    this.kfEntry = layout.add("kF", 0.0).getEntry();
    this.currentVelocity = layout.add("velocity", 0.0).getEntry();
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    this.storage.configPIDFSlot(
      this.storage.getProfileSlot(),
      this.kpEntry.getDouble(0.0),
      this.kiEntry.getDouble(0.0),
      this.kdEntry.getDouble(0.0),
      this.kfEntry.getDouble(0.0));
    
    this.targetVelocity = this.targetVelocityEntry.getDouble(0.0);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    this.currentVelocity.setDouble(this.storage.getVelocity());
    this.storage.setVelocity(this.targetVelocity);
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
