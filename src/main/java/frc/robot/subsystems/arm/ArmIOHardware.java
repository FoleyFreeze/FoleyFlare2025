package frc.robot.subsystems.arm;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkBase;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import frc.robot.Constants.ArmConstants;

public class ArmIOHardware implements ArmIO {
  private final SparkMax motor;
  private RelativeEncoder encoder;
  private final ArmIOInputsAutoLogged inputs = new ArmIOInputsAutoLogged();

  ArmCals k;

  public ArmIOHardware() {
    motor = new SparkMax(ArmConstants.ARM_MOTOR_ID, MotorType.kBrushed);
    motor.setCANTimeout(250);
    encoder = motor.getEncoder();
    SparkMaxConfig armConfig = new SparkMaxConfig();
    armConfig.voltageCompensation(10);
    armConfig.smartCurrentLimit(ArmConstants.ARM_MOTOR_CURRENT_LIMIT);
    armConfig.idleMode(IdleMode.kBrake);
    SparkBase.ResetMode resetMode = SparkBase.ResetMode.kNoResetSafeParameters;
    motor.configureAsync(
        armConfig,
        com.revrobotics.spark.SparkBase.ResetMode.kNoResetSafeParameters,
        PersistMode.kPersistParameters);
  }

  @Override
  public void set(double speed) {
    motor.set(speed);
  }

  @Override
  public void updateInputs(ArmIOInputs inputs) {
    double rawVolts = motor.getBusVoltage();

    inputs.armConnected = rawVolts > 6;
    inputs.armAppliedVolts = rawVolts * motor.getAppliedOutput();
    inputs.armCurrent = motor.getOutputCurrent();
    inputs.armTempC = motor.getMotorTemperature();
    inputs.armPosition = encoder.getPosition();
  }
}
