package frc.robot.subsystems.drive;

// 5409: The Chargers
// http://github.com/FRC5409

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Drive extends SubsystemBase {

    private static Drive instance = null;

    private Drive() {
        
    }

    // Get subsystem
    public static Drive getInstance() {
        if (instance == null) instance = new Drive();

        return instance;
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