package frc.robot.subsystems.arm;

import edu.wpi.first.math.util.Units;

public class ArmCals {

  double armLength = 0;
  double gearRatio = 0;
  double gearRatioToAbsEncoder = 0;
  double encoderZero = 0;
  double armEncOffset = 0;
  double startEncVal = 0;

  double closeEnough = Units.degreesToRadians(5); // radians
}
