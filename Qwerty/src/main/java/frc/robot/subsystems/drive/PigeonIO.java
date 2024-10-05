package frc.robot.subsystems.drive;

import com.ctre.phoenix6.BaseStatusSignal;
import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.configs.Pigeon2Configuration;
import com.ctre.phoenix6.hardware.Pigeon2;

public class PigeonIO implements GyroIO {
    private final Pigeon2 gyro;
    private final StatusSignal<Double> yaw;
    private final StatusSignal<Double> pitch;
    private final StatusSignal<Double> roll;

    public PigeonIO(int ID) {
        gyro = new Pigeon2(ID);

        Pigeon2Configuration config = new Pigeon2Configuration();
        gyro.getConfigurator().apply(config);
        gyro.getConfigurator().setYaw(0.0);

        yaw = gyro.getYaw();
        pitch = gyro.getPitch();
        roll = gyro.getRoll();

        yaw.setUpdateFrequency(50.0);   // They set it to 100, we only run at 20ms ticks (1000 * 0.02)
        pitch.setUpdateFrequency(50.0); // They set it to 100, we only run at 20ms ticks (1000 * 0.02)
        roll.setUpdateFrequency(50.0);  // They set it to 100, we only run at 20ms ticks (1000 * 0.02)

        gyro.optimizeBusUtilization();
    }

    public Pigeon2 getPigeon() {
        return gyro;
    }

    @Override
    public void updateInputs(GyroIOInputs inputs) {
        inputs.isConnected = BaseStatusSignal.refreshAll(yaw, pitch, roll).isOK();
        inputs.pitch = gyro.getPitch().getValueAsDouble();
        inputs.roll = gyro.getRoll().getValueAsDouble();
        inputs.yaw = gyro.getYaw().getValueAsDouble();
    }
}
