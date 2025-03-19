package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.RobotContainer;

public class CoralAutoButton {

  public Command CoralAutoButtonA(RobotContainer r) {
    SequentialCommandGroup c = new SequentialCommandGroup();
    c.addCommands(new CoralOutCommand(r.roller).raceWith(new WaitCommand(1)));
    c.addCommands(new ArmDownCommand(r.arm).raceWith(new WaitCommand(0.5)));
    return c;
  }
}
