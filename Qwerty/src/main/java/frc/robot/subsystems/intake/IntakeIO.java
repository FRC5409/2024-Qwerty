package frc.robot.subsystems.intake;

import org.littletonrobotics.junction.LogTable;
import org.littletonrobotics.junction.inputs.LoggableInputs;

public interface IntakeIO {
    public static class IntakeIOInputs implements LoggableInputs {
        public boolean isIntaking = false;
        public double appliedVoltage = 0.0;
        public double intakeVelocity = 0.0;
        public double motorCurrent = 0.0;
        public double motorTemp = 0.0;

        public void toLog(LogTable table) {
            table.put("isIntaking", isIntaking);
            table.put("appliedVoltage", appliedVoltage);
            table.put("intakeVelocity", intakeVelocity);
            table.put("motorCurrent", motorCurrent);
            table.put("motorTemp", motorTemp);
        }

        public void fromLog(LogTable table) {
            isIntaking = table.get("isIntaking", false);
            appliedVoltage = table.get("appliedVoltage", 0.0);
            intakeVelocity = table.get("intakeVelocity", intakeVelocity);
            motorCurrent = table.get("motorCurrent", 0.0);
            motorTemp = table.get("motorTemp", 0.0);
        }
    }

    public default void updateInputs(IntakeIOInputs inputs) {}

    public default void setVoltage(double voltage) {}
}
