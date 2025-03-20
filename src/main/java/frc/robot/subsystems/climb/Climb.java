package frc.robot.subsystems.climb;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.littletonrobotics.junction.AutoLogOutput;
import org.littletonrobotics.junction.Logger;

public class Climb extends SubsystemBase {
  private final ClimbIO io;
  private final ClimbIOInputsAutoLogged inputs = new ClimbIOInputsAutoLogged();

  public Climb(ClimbIO io) {
    this.io = io;
  }

  @Override
  public void periodic() {
    io.updateInputs(inputs);
    Logger.processInputs("Climb", inputs);
  }

  /**
   * Use to run the climber, can be set to run from 100% to -100%. Keep in mind that the direction
   * changes based on which way the winch is wound.
   *
   * @param speed motor speed from -1.0 to 1, with 0 stopping it
   */
  @AutoLogOutput
  public void runClimber(double speed) {
    io.set(speed);
  }
}
