// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.group;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.Storage;

public class DebugPullObjects extends CommandBase {
  private final Storage storage;
  private final Feeder feeder;

  private double storageVelocity;
  private double feederPercent;

  private int lastObjectPullingTime;
  private double timeDisableFeeder;

  private double timeSinceSawObjectForTheFirstTime;
  private boolean wasObjectDetected;
  private boolean isObjectDetected;

  private final NetworkTableEntry storageVelocityEntry;
  private final NetworkTableEntry feederPercentEntry;
  private final NetworkTableEntry lastObjectPullingTimeEntry;
  private final NetworkTableEntry timeDisableFeederEntry;

  /** Creates a new PullObjects. */
  public DebugPullObjects(Storage storage, Feeder feeder, ShuffleboardTab debugTab) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.storage = storage;
    this.feeder = feeder;
    addRequirements(this.storage);
    
    ShuffleboardLayout layout = debugTab.getLayout("DebugPull", BuiltInLayouts.kList);
    layout.add(this);
    this.storageVelocityEntry = layout.add("storageVelocity", 2500.0).getEntry();
    this.feederPercentEntry = layout.add("feederPercent", 0.6).getEntry();
    this.lastObjectPullingTimeEntry = layout.add("lastObjectPullingTime", 60.0).getEntry();
    this.timeDisableFeederEntry = layout.add("TimeDisableFeeder", 40.0).getEntry();
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    this.storageVelocity = this.storageVelocityEntry.getDouble(0.0);
    this.feederPercent = this.feederPercentEntry.getDouble(0.0);
    this.lastObjectPullingTime = (int) this.lastObjectPullingTimeEntry.getDouble(0.0);
    this.timeDisableFeeder = (int) this.timeDisableFeederEntry.getDouble(0.0);
    
    this.timeSinceSawObjectForTheFirstTime = 0;
    this.wasObjectDetected = false;
    this.isObjectDetected = false;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    this.isObjectDetected = this.storage.isObjectDetected();

    this.timeSinceSawObjectForTheFirstTime++;

    if (this.isObjectDetected && !this.wasObjectDetected){
      // first time seeing object:
      this.timeSinceSawObjectForTheFirstTime = 0; // mark it as the first time
      this.storage.setVelocity(this.storageVelocity);
    }
    else{
      if (!this.isObjectDetected && this.wasObjectDetected){
        // first time NOT seeing object:
        this.storage.countObject(); // mark object pulled into the storage
      }
      else{
        if (this.storage.getCount() == 4){
          // 4 objects are already inside the storage
          if (this.isObjectDetected){
            // seeing the next (fifth) object:
            if (this.timeSinceSawObjectForTheFirstTime >= this.lastObjectPullingTime){
              // pulled it more than "this.lastObjectTime" iterations
              this.storage.countObject();
            }
          }
        }
      }
    }

    if (!this.isObjectDetected){
      // not seeing object
      this.storage.stop();
      this.feeder.setPercentOutput(this.feederPercent);
    }
    else{
      if (this.timeSinceSawObjectForTheFirstTime < this.timeDisableFeeder){
        // the first "this.timeDisableFeeder" iterations of seeing object
        this.feeder.stop();
      }
      else{
        // after the fisrt "this.timeDisableFeeder" iterations of seeing object
        this.feeder.setPercentOutput(this.feederPercent);
      }
    }

    this.wasObjectDetected = this.isObjectDetected;
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
