package frc.robot.commands;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants.ArmConstants;
import frc.robot.RobotContainer;

public class AutonCommands {

  static Pose2d blueStart = new Pose2d(3, 3, Rotation2d.k180deg);
  static Pose2d redStart = new Pose2d(12, 3, Rotation2d.kZero);

  public static Command doNothing(RobotContainer r) {
    return zeroRobot(r);
  }

  public static Command justDrive(RobotContainer r) {
    return zeroRobot(r)
        .andThen(
            DriveCommands.driveForward(r, 0.2)
                .raceWith(new WaitCommand(1))
                .finallyDo(() -> r.drive.stop()));
  }

  public static Command scoreAutoCoral(RobotContainer r) {
    SequentialCommandGroup c = new SequentialCommandGroup();
    c.addCommands(zeroRobot(r));
    c.addCommands(new InstantCommand(() -> r.arm.runArm(ArmConstants.ARM_HOLD_UP))); // hold arm up
    c.addCommands(
        DriveCommands.driveForward(r, 0.2)
            .raceWith(new WaitCommand(2.6))
            .finallyDo(() -> r.drive.stop()));
    c.addCommands(
        new CoralOutCommand(r.roller)
            .alongWith(
                new InstantCommand(() -> r.arm.runArm(0.2))
                    .raceWith(new WaitCommand(0.1))
                    .andThen(new RunCommand(() -> r.arm.runArm(0.1))))
            .raceWith(new WaitCommand(0.5)));
    c.addCommands(new ArmUpCommand(r.arm).raceWith(new WaitCommand(0.5)));

    return c;
  }

  public static Command zeroRobot(RobotContainer r) {
    return new InstantCommand(() -> r.drive.setPose(isBlue() ? blueStart : redStart));
  }

  public static boolean isBlue() {
    return DriverStation.getAlliance().orElse(Alliance.Blue) == Alliance.Blue;
  }
}
