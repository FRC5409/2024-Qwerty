package frc.robot.subsystems.intake;

import java.util.function.DoubleSupplier;
import org.littletonrobotics.junction.Logger;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;

// 5409: The Chargers
// http://github.com/FRC5409

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase {

    private final IntakeIO io;
    private final IntakeIOInputsAutoLogged inputs = new IntakeIOInputsAutoLogged();

    private static Intake instance = null;

    private Intake(IntakeIO io) {
        this.io = io;
    }

    public static Intake createInstance(IntakeIO io) {
        if (instance != null) throw new RuntimeException("Intake has already been created");

        return instance = new Intake(io);
    }

    // Get subsystem
    public static Intake getInstance() {
        if (instance == null) throw new RuntimeException("Intake hasn't been created yet");

        return instance;
    }

    public Command variableVoltage(DoubleSupplier voltage) {
        return Commands.run(() -> io.setVoltage(voltage.getAsDouble()), this);
    }

    public Command runIntake() {
        return Commands.runOnce(() -> io.setVoltage(2.0), this);
    }

    public Command stopIntake() {
        return Commands.runOnce(() -> io.setVoltage(0.0), this);
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
        io.updateInputs(inputs);
        Logger.processInputs("Intake", inputs);
    }

    @Override
    public void simulationPeriodic() {
        // This method will be called once per scheduler run during simulation
        
    }

}
