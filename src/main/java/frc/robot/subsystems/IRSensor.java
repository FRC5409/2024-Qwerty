package frc.robot.subsystems;

// 5409: The Chargers
// http://github.com/FRC5409

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class IRSensor extends SubsystemBase {

    private static IRSensor instance = null;

    private DigitalInput sensor;

    private IRSensor() {
        sensor = new DigitalInput(0);
    }

    // Get subsystem
    public static IRSensor getInstance() {
        if (instance == null) instance = new IRSensor();

        return instance;
    }

    /**
     * Returns true if object is in the range of the IR sensor
     */
    public boolean inRange() {
        return !sensor.get();
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