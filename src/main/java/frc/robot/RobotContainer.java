// Copyright 2021-2025 FRC 6328
// http://github.com/Mechanical-Advantage
//
// This program is free software; you can redistribute it and/or
// modify it under the terms of the GNU General Public License
// version 3 as published by the Free Software Foundation or
// available in the root directory of this project.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU General Public License for more details.

package frc.robot;

import com.pathplanner.lib.auto.AutoBuilder;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine;
import frc.robot.Constants.DriveConstants;
import frc.robot.commands.AlgieInCommand;
import frc.robot.commands.AlgieOutCommand;
import frc.robot.commands.ArmDownCommand;
import frc.robot.commands.ArmUpCommand;
import frc.robot.commands.AutonCommands;
import frc.robot.commands.ClimberDownCommand;
import frc.robot.commands.ClimberUpCommand;
import frc.robot.commands.CoralOutCommand;
import frc.robot.commands.CoralStackCommand;
import frc.robot.commands.DriveCommands;
import frc.robot.generated.TunerConstants;
import frc.robot.subsystems.arm.Arm;
import frc.robot.subsystems.arm.ArmIO;
import frc.robot.subsystems.arm.ArmIOHardware;
import frc.robot.subsystems.arm.ArmIOSim;
import frc.robot.subsystems.climb.Climb;
import frc.robot.subsystems.climb.ClimbIO;
import frc.robot.subsystems.climb.ClimbIOHardware;
import frc.robot.subsystems.climb.ClimbIOSim;
import frc.robot.subsystems.drive.Drive;
import frc.robot.subsystems.drive.GyroIO;
import frc.robot.subsystems.drive.GyroIOPigeon2;
import frc.robot.subsystems.drive.ModuleIO;
import frc.robot.subsystems.drive.ModuleIOSim;
import frc.robot.subsystems.drive.ModuleIOTalonFX;
import frc.robot.subsystems.roller.Roller;
import frc.robot.subsystems.roller.RollerIO;
import frc.robot.subsystems.roller.RollerIOHardware;
import frc.robot.subsystems.roller.RollerIOSim;
import org.littletonrobotics.junction.networktables.LoggedDashboardChooser;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // Subsystems
  public final Drive drive;
  public final Climb climb;
  public final Arm arm;
  public final Roller roller;

  // Controller
  public final CommandXboxController driveJoystick = new CommandXboxController(0);
  public final CommandXboxController operatorJoystick = new CommandXboxController(1);

  // Dashboard inputs
  private final LoggedDashboardChooser<Command> autoChooser;

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    switch (Constants.currentMode) {
      case REAL:
        // Real robot, instantiate hardware IO implementations
        drive =
            new Drive(
                new GyroIOPigeon2(),
                new ModuleIOTalonFX(TunerConstants.FrontLeft),
                new ModuleIOTalonFX(TunerConstants.FrontRight),
                new ModuleIOTalonFX(TunerConstants.BackLeft),
                new ModuleIOTalonFX(TunerConstants.BackRight));
        climb = new Climb(new ClimbIOHardware());
        arm = new Arm(new ArmIOHardware());
        roller = new Roller(new RollerIOHardware());

        break;

      case SIM:
        // Sim robot, instantiate physics sim IO implementations
        drive =
            new Drive(
                new GyroIO() {},
                new ModuleIOSim(TunerConstants.FrontLeft),
                new ModuleIOSim(TunerConstants.FrontRight),
                new ModuleIOSim(TunerConstants.BackLeft),
                new ModuleIOSim(TunerConstants.BackRight));
        climb = new Climb(new ClimbIOSim());
        arm = new Arm(new ArmIOSim());
        roller = new Roller(new RollerIOSim());

        break;

      default:
        // Replayed robot, disable IO implementations
        drive =
            new Drive(
                new GyroIO() {},
                new ModuleIO() {},
                new ModuleIO() {},
                new ModuleIO() {},
                new ModuleIO() {});

        climb = new Climb(new ClimbIO() {});
        arm = new Arm(new ArmIO() {});
        roller = new Roller(new RollerIO() {});
        break;
    }

    // Set up auto routines
    autoChooser = new LoggedDashboardChooser<>("Auto Choices", AutoBuilder.buildAutoChooser());

    // our autos
    autoChooser.addDefaultOption("ScoreAutoCoral", AutonCommands.scoreAutoCoral(this));
    autoChooser.addOption("JustDrive", AutonCommands.justDrive(this));
    autoChooser.addOption("DoNothing", AutonCommands.doNothing(this));

    // Set up SysId routines
    autoChooser.addOption(
        "Drive Wheel Radius Characterization", DriveCommands.wheelRadiusCharacterization(drive));
    autoChooser.addOption(
        "Drive Simple FF Characterization", DriveCommands.feedforwardCharacterization(drive));
    autoChooser.addOption(
        "Drive SysId (Quasistatic Forward)",
        drive.sysIdQuasistatic(SysIdRoutine.Direction.kForward));
    autoChooser.addOption(
        "Drive SysId (Quasistatic Reverse)",
        drive.sysIdQuasistatic(SysIdRoutine.Direction.kReverse));
    autoChooser.addOption(
        "Drive SysId (Dynamic Forward)", drive.sysIdDynamic(SysIdRoutine.Direction.kForward));
    autoChooser.addOption(
        "Drive SysId (Dynamic Reverse)", drive.sysIdDynamic(SysIdRoutine.Direction.kReverse));

    // Configure the button bindings
    configureSwerveBindings();
    configureEverybotBindings();
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureSwerveBindings() {
    // Default command, normal field-relative drive
    drive.setDefaultCommand(
        DriveCommands.joystickDrive(
            drive,
            () -> -driveJoystick.getLeftY(),
            () -> -driveJoystick.getLeftX(),
            () -> -driveJoystick.getRightX()));

    /**
     * Holding the left bumper (or whatever button you assign) will multiply the speed by a decimal
     * to limit the max speed of the robot -> 1 (100%) from the controller * .9 = 90% of the max
     * speed when held (we also square it)
     *
     * <p>Slow mode is very valuable for line ups and the deep climb
     *
     * <p>When switching to single driver mode switch to the B button
     */
    driveJoystick
        .leftBumper()
        .whileTrue(
            DriveCommands.joystickDrive(
                drive,
                () -> -driveJoystick.getLeftY() * DriveConstants.SLOW_MODE_MOVE,
                () -> -driveJoystick.getLeftX() * DriveConstants.SLOW_MODE_MOVE,
                () -> -driveJoystick.getRightX() * DriveConstants.SLOW_MODE_TURN));

    // Lock to 0° when A button is held
    driveJoystick
        .a()
        .whileTrue(
            DriveCommands.joystickDriveAtAngle(
                drive,
                () -> -driveJoystick.getLeftY(),
                () -> -driveJoystick.getLeftX(),
                () -> new Rotation2d()));

    // Switch to X pattern when X button is pressed
    driveJoystick.x().onTrue(Commands.runOnce(drive::stopWithX, drive));

    // Reset gyro to 0° when B button is pressed
    driveJoystick
        .b()
        .onTrue(
            Commands.runOnce(
                    () ->
                        drive.setPose(
                            new Pose2d(drive.getPose().getTranslation(), new Rotation2d())),
                    drive)
                .ignoringDisable(true));
  }

  private void configureEverybotBindings() {
    /**
     * Here we declare all of our operator commands, these commands could have been written in a
     * more compact manner but are left verbose so the intent is clear.
     */
    operatorJoystick.rightBumper().whileTrue(new AlgieInCommand(roller));

    // Here we use a trigger as a button when it is pushed past a certain threshold
    operatorJoystick.rightTrigger(.2).whileTrue(new AlgieOutCommand(roller));

    /**
     * The arm will be passively held up or down after this is used, make sure not to run the arm
     * too long or it may get upset!
     */
    operatorJoystick.leftBumper().whileTrue(new ArmUpCommand(arm));
    operatorJoystick.leftTrigger(.2).whileTrue(new ArmDownCommand(arm));

    /**
     * Used to score coral, the stack command is for when there is already coral in L1 where you are
     * trying to score. The numbers may need to be tuned, make sure the rollers do not wear on the
     * plastic basket.
     */
    operatorJoystick.x().whileTrue(new CoralOutCommand(roller));
    operatorJoystick.y().whileTrue(new CoralStackCommand(roller));

    /**
     * POV is a direction on the D-Pad or directional arrow pad of the controller, the direction of
     * this will be different depending on how your winch is wound
     */
    operatorJoystick.pov(0).whileTrue(new ClimberUpCommand(climb));
    operatorJoystick.pov(180).whileTrue(new ClimberDownCommand(climb));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return autoChooser.get();
  }
}
