// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
    // controllers
    public static final int operatorControlerID = 1;

    // motors
    public static final int feederID = 1;
    public static final int armID = 10;
    public static final int coverID = 9;
    public static final int acceleratorRightID = 12;
    public static final int acceleratorLeftID = 2;
    public static final int storageID = 13;
    
    // sensors
	public static final int upperSwitchID = 1;
	public static final int lowerSwitchID = 0;
	public static final int infraRedID = 4;
}