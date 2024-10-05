package frc.robot.subsystems.drive;

import org.littletonrobotics.junction.LogTable;
import org.littletonrobotics.junction.inputs.LoggableInputs;

public interface GyroIO {
    public static class GyroIOInputs implements LoggableInputs {
        public boolean isConnected = false;
        public double roll = 0.0;
        public double pitch = 0.0;
        public double yaw = 0.0;

        public void toLog(LogTable table) {
            table.put("isConnected", isConnected);
            table.put("roll", roll);
            table.put("pitch", pitch);
            table.put("yaw", yaw);
        }

        public void fromLog(LogTable table) {
            isConnected = table.get("isConnected", false);
            roll = table.get("roll", 0.0);
            pitch = table.get("pitch", 0.0);
            yaw = table.get("yaw", 0.0);
        }
    }

    public default void updateInputs(GyroIOInputs inputs) {}
}
