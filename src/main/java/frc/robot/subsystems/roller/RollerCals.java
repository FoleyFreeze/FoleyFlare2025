package frc.robot.subsystems.roller;

import edu.wpi.first.math.util.Units;

public class RollerCals {
  double RollerLength = 0;
  double gearRatio = 0;
  double gearRatioToAbsEncoder = 0;
  double encoderZero = 0;
  double RollerEncOffset = 0;
  double startEncVal = 0;

  double closeEnough = Units.degreesToRadians(5); // radians
}
