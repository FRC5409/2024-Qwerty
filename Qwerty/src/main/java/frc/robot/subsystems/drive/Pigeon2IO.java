package frc.robot.subsystems.drive;

import com.ctre.phoenix6.BaseStatusSignal;
import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.configs.Pigeon2Configuration;
import com.ctre.phoenix6.hardware.Pigeon2;
import edu.wpi.first.math.geometry.Rotation2d;

public class Pigeon2IO implements GyroIO {
    private final Pigeon2 pigeon;
    private final StatusSignal<Double> yaw;
    private final StatusSignal<Double> pitch;
    private final StatusSignal<Double> roll;

    public Pigeon2IO(int ID) {
        pigeon = new Pigeon2(ID);

        pigeon.getConfigurator().apply(new Pigeon2Configuration());
        pigeon.getConfigurator().setYaw(0.0);

        yaw   = pigeon.getYaw();
        pitch = pigeon.getPitch();
        roll  = pigeon.getRoll();

        yaw  .setUpdateFrequency(100);
        pitch.setUpdateFrequency(100);
        roll .setUpdateFrequency(100);

        pigeon.optimizeBusUtilization();
    }
    
    @Override
    public void updateInputs(GyroIOInputs inputs) {
        inputs.isConnected = BaseStatusSignal.refreshAll(yaw, pitch, roll).isOK();

        inputs.yaw   = Rotation2d.fromDegrees(yaw.getValueAsDouble());
        inputs.pitch = Rotation2d.fromDegrees(pitch.getValueAsDouble());
        inputs.roll  = Rotation2d.fromDegrees(roll.getValueAsDouble());
    }
}
