package frc.robot.subsystems.intake;

import org.littletonrobotics.junction.AutoLog;

//https://github.dev/Mechanical-Advantage/AdvantageKit

public interface IntakeIO {
    @AutoLog
    public static class IntakeIOInputs {
        public boolean isIntaking = false;
        public double appliedVoltage = 0.0;
        public double intakeVelocity = 0.0;
        public double motorCurrent = 0.0;
        public double motorTemp = 0.0;
    }

    public default void updateInputs(IntakeIOInputs inputs) {}

    public default void setVoltage(double voltage) {}
}
