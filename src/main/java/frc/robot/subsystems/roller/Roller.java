package frc.robot.subsystems.roller;

import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.RollerConstants;

public class Roller extends SubsystemBase {

  private final SparkMax rollerMotor;

  public Roller() {
    rollerMotor = new SparkMax(RollerConstants.ROLLER_MOTOR_ID, MotorType.kBrushed);
    rollerMotor.setCANTimeout(250);

    SparkMaxConfig rollerConfig = new SparkMaxConfig();
    rollerConfig.voltageCompensation(RollerConstants.ROLLER_MOTOR_VOLTAGE_COMP);
    rollerConfig.smartCurrentLimit(RollerConstants.ROLLER_MOTOR_CURRENT_LIMIT);
    rollerConfig.idleMode(IdleMode.kBrake);
    rollerMotor.configure(
        rollerConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
  }

  @Override
  public void periodic() {}

  /**
   * This is a method that makes the roller spin to your desired speed. Positive values make it spin
   * forward and negative values spin it in reverse.
   *
   * @param speedmotor speed from -1.0 to 1, with 0 stopping it
   */
  public void runRoller(double speed) {
    rollerMotor.set(speed);
  }
}
