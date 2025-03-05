package frc.robot.subsystems.arm;

import org.littletonrobotics.junction.AutoLog;

public interface ArmIO {

  @AutoLog
  public static class ArmIOInputs {
    public boolean armConnected = false;
    public double armAppliedVolts = 0;
    public double armCurrent = 0;
    public double armTempF = 0;
    public double armPosition = 0;
  }

  public default void updateInputs(ArmIOInputs inputs) {}

  public default void setArmVolts(double volts) {}

  public default void zero() {}
}
