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

  public static final class OperatorConstants {

    //Joystick Ports
    public static final int kPrimaryControllerPort = 0;
    public static final int kSecondaryControllerPort = 1;

  }

  public static final class kLED {
    public static final int LEDCount = 60;

    public static final int BLINK_SPEED = 5; // in REFRESH_TIME intervals
 
    public static final int SIN_COUNT = 8;
    public static final double SIN_SPEED = 0.5;

    public static final double SIN_HS = 0.05;
    public static final double SIN_VS = 30.0;

    public static final double RAINBOW_CYCLE_SPEED = 0.5;   // negative speed to reverse
    public static final double RAINBOW_BLINK_SPEEED = 0.5; // negative speed to reverse
  }
}
