// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.core.accelerator;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Accelerator;

public class DebugPID_Accelerator extends CommandBase {
  private final Accelerator accelerator;
  
  private NetworkTableEntry targetVelocityEntry;
  private NetworkTableEntry kpEntry;
  private NetworkTableEntry kiEntry;
  private NetworkTableEntry kdEntry;
  private NetworkTableEntry kfEntry;
  private NetworkTableEntry currentVelocity;

  private double targetVelocity;
  
  /** Creates a new PID_Accelerator. */
  public DebugPID_Accelerator(Accelerator accelerator, ShuffleboardTab debugTab) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(accelerator);
    this.accelerator = accelerator;

    ShuffleboardLayout layout = debugTab.getLayout("DebugPID_Accelerator", BuiltInLayouts.kList);
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
    this.accelerator.configPIDFSlot(
      this.kpEntry.getDouble(0.0),
      this.kiEntry.getDouble(0.0),
      this.kdEntry.getDouble(0.0),
      this.kfEntry.getDouble(0.0));
    
    this.targetVelocity = this.targetVelocityEntry.getDouble(0.0);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    this.currentVelocity.setDouble(this.accelerator.getVelocity());
    this.accelerator.setVelocity(this.targetVelocity);
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
