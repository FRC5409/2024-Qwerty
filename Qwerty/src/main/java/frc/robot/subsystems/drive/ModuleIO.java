package frc.robot.subsystems.drive;

import org.littletonrobotics.junction.AutoLog;
import edu.wpi.first.math.geometry.Rotation2d;

public interface ModuleIO {
    @AutoLog
    public static class ModuleIOInputs {
        public double driveVolts = 0.0;
        public double turnVolts = 0.0;
        public double driveCurrent = 0.0;
        public double turnCurrent = 0.0;

        public Rotation2d absTurnPosition = Rotation2d.fromDegrees(0.0);
    }

    public default void updateInputs(ModuleIOInputs inputs) {}

    public default void driveVolts(double voltage) {}
    public default void turnVolts(double voltage) {}
}
