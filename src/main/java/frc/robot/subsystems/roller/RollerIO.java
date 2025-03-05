package frc.robot.subsystems.roller;

import org.littletonrobotics.junction.AutoLog;

public interface RollerIO {

  @AutoLog
  public static class RollerIOInputs {
    public boolean rollerConnected = false;
    public double rollerAppliedVolts = 0;
    public double rollerCurrent = 0;
    public double rollerTempF = 0;
    public double rollerPosition = 0;
  }

  public default void updateInputs(RollerIOInputs inputs) {}

  public default void setRollerVolts(double volts) {}

  public default void zero() {}
}
