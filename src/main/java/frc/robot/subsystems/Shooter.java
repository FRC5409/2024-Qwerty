package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;

// 5409: The Chargers
// http://github.com/FRC5409

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class Shooter extends SubsystemBase {

    private final CANSparkMax leftMot;
    private final CANSparkMax rightMot;

    private static Shooter instance = null;

    private Shooter() {
        leftMot = new CANSparkMax(4, MotorType.kBrushless);
        rightMot = new CANSparkMax(3, MotorType.kBrushless);

        leftMot.restoreFactoryDefaults();
        leftMot.setSmartCurrentLimit(20);
        leftMot.setIdleMode(IdleMode.kBrake);

        rightMot.restoreFactoryDefaults();
        rightMot.setSmartCurrentLimit(20);
        rightMot.setIdleMode(IdleMode.kBrake);
        rightMot.setInverted(true);        

        leftMot.burnFlash();
        rightMot.burnFlash();
    }

    // Get subsystem
    public static Shooter getInstance() {
        if (instance == null) instance = new Shooter();

        return instance;
    }

    public void setSpeed(double speed) {
        leftMot.set(speed);
    }

    public Command shoot() {
        return 
        Commands.runOnce(() -> this.setSpeed(0.2), this)
            .andThen(
                new WaitCommand(0.5)
            .andThen(
                Commands.runOnce(() -> this.setSpeed(0), this)
            )
        );
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
        
    }

    @Override
    public void simulationPeriodic() {
        // This method will be called once per scheduler run during simulation
        
    }

}
