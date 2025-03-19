package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.RobotContainer;

public class AutonCommands {
  public static Command doNothing() {
    return new InstantCommand();
  }

  public static Command justDrive(RobotContainer r) {
    return DriveCommands.driveForward(r, 0.25)
        .raceWith(new WaitCommand(1))
        .finallyDo(() -> r.drive.stop());
  }

  public static Command scoreAutoCoral(RobotContainer r) {
    SequentialCommandGroup c = new SequentialCommandGroup();
    c.addCommands(
        DriveCommands.driveForward(r, 0.25)
            .raceWith(new WaitCommand(3))
            .finallyDo(() -> r.drive.stop()));
    c.addCommands(new CoralOutCommand(r.roller).raceWith(new WaitCommand(1)));
    c.addCommands(new ArmDownCommand(r.arm).raceWith(new WaitCommand(0.5)));

    return c;
  }
}
