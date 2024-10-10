package frc.robot.subsystems.drive;

import org.littletonrobotics.junction.AutoLogOutput;
import org.littletonrobotics.junction.Logger;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;

// 5409: The Chargers
// http://github.com/FRC5409

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Gyro extends SubsystemBase {

    private final GyroIO io;
    private final GyroIOInputsAutoLogged inputs = new GyroIOInputsAutoLogged();

    private final Pose2d robotPose;

    @AutoLogOutput(key = "Drive/3dPose")
    private Pose3d pose;

    private final ShuffleboardTab sb_gyro;
    private final Field2d sb_field;

    private static Gyro instance = null;

    private Gyro(GyroIO io) {
        this.io = io;

        robotPose = new Pose2d(0, 0, inputs.yaw);
        pose = new Pose3d(0, 0, 0, new Rotation3d(0, 0, 0));

        sb_gyro = Shuffleboard.getTab("Drive");

        sb_field = new Field2d();
        sb_field.setRobotPose(robotPose);

        sb_gyro.add("Field", sb_field);
    }

    public static Gyro createInstance(GyroIO io) {
        if (instance != null) throw new RuntimeException("Gyro has already been created");

        return instance = new Gyro(io);
    }

    // Get subsystem
    public static Gyro getInstance() {
        if (instance == null) throw new RuntimeException("Gyro hasn't been created yet");

        return instance;
    }

    @AutoLogOutput(key =  "Drive/Pose")
    public Pose2d getRobotPose() {
        return new Pose2d(0, 0, inputs.yaw);
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
        sb_field.setRobotPose(getRobotPose());
        io.updateInputs(inputs);
        Logger.processInputs("Drive/Gyro", inputs);

        pose = new Pose3d(0, 0, 0, new Rotation3d(inputs.roll.getRadians(), inputs.pitch.getRadians(), inputs.yaw.getRadians()));
    }

    @Override
    public void simulationPeriodic() {
        // This method will be called once per scheduler run during simulation
        
    }

}
