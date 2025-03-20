package frc.robot.subsystems.roller;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.littletonrobotics.junction.Logger;

public class Roller extends SubsystemBase {
  private final RollerIO io;
  private final RollerIOInputsAutoLogged inputs = new RollerIOInputsAutoLogged();

  public Roller(RollerIO io) {
    this.io = io;
  }

  @Override
  public void periodic() {
    io.updateInputs(inputs);
    Logger.processInputs("Roller", inputs);
  }

  /**
   * This is a method that makes the roller spin to your desired speed. Positive values make it spin
   * forward and negative values spin it in reverse.
   *
   * @param speedmotor speed from -1.0 to 1, with 0 stopping it
   */
  public void runRoller(double speed) {
    io.set(speed);
  }
}
