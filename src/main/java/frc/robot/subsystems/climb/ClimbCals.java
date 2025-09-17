package frc.robot.subsystems.climb;

import edu.wpi.first.math.util.Units;

public class ClimbCals {

  double climberLength = 0;
  double gearRatio = 0;
  double gearRatioToAbsEncoder = 0;
  double encoderZero = 0;
  double armEncOffset = 0;
  double startEncVal = 0;

  double closeEnough = Units.degreesToRadians(5); // radians
}
