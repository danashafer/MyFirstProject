// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.core.feeder.ActivateFeeder;
import frc.robot.commands.core.storage.DebugActivateStorage;
import frc.robot.commands.core.storage.DebugPID_Storage;
import frc.robot.commands.core.storage.ResetCount;
import frc.robot.commands.group.DebugPullObjects;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.Storage;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // Debug-Tabs:
  private final boolean isDebugMode = true;
  private final String subsystemsDebugTabName = "Subsystems";
  private final String commandsDebugTabName = "Commands";
  
  // The robot's subsystems and commands are defined here...
  private final Arm arm = new Arm();
  private final Feeder feeder = new Feeder();
  private final Storage storage = new Storage();

  // controllers:
  private final XboxController operatorControler = new XboxController(Constants.operatorControlerID);

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();
    if (this.isDebugMode){
      this.createCommandsDebugTab();
      this.createSubsystemsDebugTab();
    }
  }

  public void createCommandsDebugTab() {
    ShuffleboardTab commandsDebugTab = Shuffleboard.getTab(this.commandsDebugTabName);
    new DebugActivateStorage(this.storage, commandsDebugTab);
    new DebugPID_Storage(this.storage, commandsDebugTab);
    new DebugPullObjects(this.storage, this.feeder, commandsDebugTab);
    new ResetCount(this.storage, commandsDebugTab);
  }

  private void createSubsystemsDebugTab() {
    ShuffleboardTab subsystemsDebugTab = Shuffleboard.getTab(this.subsystemsDebugTabName);
    this.arm.fillDebugTab(subsystemsDebugTab);
    this.storage.fillDebugTab(subsystemsDebugTab);
  }

  private void debugTabPeriodic() {
    this.arm.debugTabPeriodic();
    this.storage.debugTabPeriodic();
  }

  public void scheduleRobotPeriodic() {
    if (this.isDebugMode){
      this.debugTabPeriodic();
    }
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    JoystickButton rumbleRollers = new JoystickButton(this.operatorControler, 1); // Button A
    rumbleRollers.whileActiveContinuous(new ActivateFeeder(this.feeder, 0.3));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return null;
  }
}
