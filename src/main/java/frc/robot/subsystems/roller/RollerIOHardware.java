package frc.robot.subsystems.roller;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import frc.robot.Constants.RollerConstants;
import frc.robot.subsystems.roller.RollerIO.RollerIOInputs;

public class RollerIOHardware implements RollerIO {
  private final SparkMax motor;
  private RelativeEncoder encoder;
  private final RollerIOInputsAutoLogged inputs = new RollerIOInputsAutoLogged();

  RollerCals k;

  public RollerIOHardware() {
    motor = new SparkMax(RollerConstants.ROLLER_MOTOR_ID, MotorType.kBrushed);
    // motor.setCANTimeout(250);
    encoder = motor.getEncoder();

    SparkMaxConfig rollerConfig = new SparkMaxConfig();
    rollerConfig.voltageCompensation(RollerConstants.ROLLER_MOTOR_VOLTAGE_COMP);
    rollerConfig.smartCurrentLimit(RollerConstants.ROLLER_MOTOR_CURRENT_LIMIT);
    rollerConfig.idleMode(IdleMode.kBrake);
    motor.configure(rollerConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
  }

  @Override
  public void set(double speed) {
    motor.set(speed);
  }

  @Override
  public void updateInputs(RollerIOInputs inputs) {
    double rawVolts = motor.getBusVoltage();

    inputs.rollerConnected = rawVolts > 6;
    inputs.rollerAppliedVolts = rawVolts * motor.getAppliedOutput();
    inputs.rollerCurrent = motor.getOutputCurrent();
    inputs.rollerTempF = motor.getMotorTemperature();
    inputs.rollerPosition = encoder.getPosition();
  }
}
