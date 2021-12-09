// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.group;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.Storage;

public class PullObjects extends CommandBase {
  private Storage storage;
  private Feeder feeder;

  private final double feederPercent;
  private final double storageVelocity;

  /** Creates a new PullObjects. */
  public PullObjects(Feeder feeder, Storage storage,
                  double feederPercent, double storageVelocity) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.storage = storage;
    this.feeder = feeder;
    addRequirements(this.storage, this.feeder);

    this.feederPercent = feederPercent;
    this.storageVelocity = storageVelocity;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (this.storage.isObjectDetected()){
      this.storage.setVelocity(this.storageVelocity);
    }
    else{
      this.storage.stop();
    }
    this.feeder.setPercentOutput(this.feederPercent);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    this.storage.stop();
    this.feeder.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return this.storage.isFull();
  }
}
