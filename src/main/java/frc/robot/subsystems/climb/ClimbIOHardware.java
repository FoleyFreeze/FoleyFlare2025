package frc.robot.subsystems.climb;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import frc.robot.Constants.ClimberConstants;

public class ClimbIOHardware implements ClimbIO {
  private SparkMax motor;
  private RelativeEncoder encoder;
  private final ClimbIOInputsAutoLogged inputs = new ClimbIOInputsAutoLogged();

  public ClimbIOHardware() {
    motor = new SparkMax(ClimberConstants.CLIMBER_MOTOR_ID, MotorType.kBrushless);
    encoder = motor.getEncoder();

    // motor.setCANTimeout(250);
    SparkMaxConfig climbConfig = new SparkMaxConfig();
    climbConfig.voltageCompensation(ClimberConstants.CLIMBER_MOTOR_VOLTAGE_COMP);
    climbConfig.smartCurrentLimit(ClimberConstants.CLIMBER_MOTOR_CURRENT_LIMIT);
    climbConfig.idleMode(IdleMode.kBrake);
    climbConfig.inverted(true);
    motor.configure(climbConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
  }

  @Override
  public void set(double speed) {
    motor.set(speed);
  }

  @Override
  public void updateInputs(ClimbIOInputs inputs) {
    double rawVolts = motor.getBusVoltage();

    inputs.climbConnected = rawVolts > 6;
    inputs.climbAppliedVolts = rawVolts * motor.getAppliedOutput();
    inputs.climbCurrent = motor.getOutputCurrent();
    inputs.climbTempC = motor.getMotorTemperature();
    inputs.climbPosition = encoder.getPosition();
  }
}
