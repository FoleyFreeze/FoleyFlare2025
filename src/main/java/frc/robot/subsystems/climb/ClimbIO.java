package frc.robot.subsystems.climb;

import org.littletonrobotics.junction.AutoLog;

public interface ClimbIO {

  @AutoLog
  public static class ClimbIOInputs {
    public boolean climbConnected = false;
    public double climbAppliedVolts = 0;
    public double climbCurrent = 0;
    public double climbTempF = 0;
    public double climbPosition = 0;
  }

  public default void updateInputs(ClimbIOInputs inputs) {}

  public default void setclimbVolts(double volts) {}

  public default void zero() {}
}
