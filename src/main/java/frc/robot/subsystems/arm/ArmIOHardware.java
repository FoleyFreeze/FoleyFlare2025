// package frc.robot.subsystems.arm;

// import com.revrobotics.servohub.ServoHub.ResetMode;
// import com.revrobotics.spark.SparkMax;
// import com.revrobotics.spark.SparkBase.PersistMode;
// import com.revrobotics.spark.config.SparkMaxConfig;
// import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

// import frc.robot.Constants.ArmConstants;

// public class ArmIOHardware implements ArmIO {    
//   private final ArmIO io;
//   private final SparkMax armMotor;
//   private final ArmIOInputsAutoLogged inputs = new ArmIOInputsAutoLogged();


  //   ArmCals k;

// public ArmIOHardware (ArmCals k) {
// this.k = k;
// armMotor.setCANTimeout(250);

// SparkMaxConfig armConfig = new SparkMaxConfig();
// armConfig.voltageCompensation(10);
// armConfig.smartCurrentLimit(ArmConstants.ARM_MOTOR_CURRENT_LIMIT);
// armConfig.idleMode(IdleMode.kBrake);
//  armMotor.configure(armConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
//   }
// }

//TODO: this is empty cz i had to go, continue fxing :p