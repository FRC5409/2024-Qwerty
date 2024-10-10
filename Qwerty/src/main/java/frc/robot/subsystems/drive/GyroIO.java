package frc.robot.subsystems.drive;

import org.littletonrobotics.junction.AutoLog;
import edu.wpi.first.math.geometry.Rotation2d;

public interface GyroIO {
  @AutoLog
    public class GyroIOInputs {
        public boolean isConnected = false;
        public Rotation2d yaw = Rotation2d.fromDegrees(0.0);
        public Rotation2d pitch = Rotation2d.fromDegrees(0.0);
        public Rotation2d roll = Rotation2d.fromDegrees(0.0);
    }

    public default void updateInputs(GyroIOInputs inputs) {}
}
