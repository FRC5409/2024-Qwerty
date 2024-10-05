package frc.robot.subsystems.intake;

import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import edu.wpi.first.wpilibj.RobotController;

public class IntakeIOSparkMAX implements IntakeIO {
    private final CANSparkMax intakeMotor;
    private final RelativeEncoder intakeEncoder;

    public IntakeIOSparkMAX(int ID) {
        intakeMotor = new CANSparkMax(ID, MotorType.kBrushless);
        intakeEncoder = intakeMotor.getEncoder();

        intakeMotor.restoreFactoryDefaults();
        intakeMotor.setSmartCurrentLimit(30);
        intakeMotor.setIdleMode(IdleMode.kBrake);
        intakeMotor.burnFlash();
    }
    
    @Override
    public void updateInputs(IntakeIOInputs inputs) {
        inputs.isIntaking = intakeMotor.getAppliedOutput() != 0.0;
        inputs.appliedVoltage = intakeMotor.getAppliedOutput() * RobotController.getBatteryVoltage();
        inputs.motorCurrent = intakeMotor.getOutputCurrent();
        inputs.motorTemp = intakeMotor.getMotorTemperature();

        inputs.intakeVelocity = intakeEncoder.getVelocity();
    }

    @Override
    public void setVoltage(double voltage) {
        intakeMotor.setVoltage(voltage);
    }
}
