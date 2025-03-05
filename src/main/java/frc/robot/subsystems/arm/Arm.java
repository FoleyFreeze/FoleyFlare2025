package frc.robot.subsystems.arm;

import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.google.flatbuffers.Constants;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ArmConstants;
import org.littletonrobotics.junction.AutoLogOutput;

public class Arm extends SubsystemBase {
  private final ArmIO io;
  private final SparkMax armMotor;
  private final ArmIOInputsAutoLogged inputs = new ArmIOInputsAutoLogged();


  public Arm(ArmIO io) {
this.io = io;
    armMotor = new SparkMax(ArmConstants.ARM_MOTOR_ID, MotorType.kBrushed);


  }

  @Override
  public void periodic() {}
  /**
   * This is a method that makes the arm move at your desired speed Positive values make it spin
   * forward and negative values spin it in reverse
   *
   * @param speed motor speed from -1.0 to 1, with 0 stopping it
   */
  @AutoLogOutput
  public void runArm(double speed) {
    io.setArmVolts(speed);
  }
  
}