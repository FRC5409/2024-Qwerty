package frc.robot.subsystems.drive;

import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.configs.CANcoderConfiguration;
import com.ctre.phoenix6.configs.CANcoderConfigurator;
import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.MagnetSensorConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.configs.TalonFXConfigurator;
import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.AbsoluteSensorRangeValue;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.RobotController;

public class FalconModuleIO implements ModuleIO {
    private final TalonFX driveMotor;
    private final TalonFX turnMotor;
    private final CANcoder absEncoder;

    private final TalonFXConfigurator driveConfig;
    private final TalonFXConfigurator turnConfig;
    private final CANcoderConfigurator encoderConfig;

    private final StatusSignal<Double> absPosition;
    private final StatusSignal<Double> driveAppliedCurrent;
    private final StatusSignal<Double> turnAppliedCurrent;

    public FalconModuleIO(FalconModuleConfig config) {
        driveMotor = new TalonFX(config.getDriveID());
        turnMotor = new TalonFX(config.getTurnID());
        absEncoder = new CANcoder(config.getCANCoderID());

        driveConfig = driveMotor.getConfigurator();
        turnConfig = turnMotor.getConfigurator();
        encoderConfig = absEncoder.getConfigurator();

        driveConfig.apply(new TalonFXConfiguration());
        turnConfig.apply(new TalonFXConfiguration());
        encoderConfig.apply(new CANcoderConfiguration());

        driveConfig.setPosition(0.0);
        turnConfig.setPosition(0);

        CurrentLimitsConfigs driveCurrent = new CurrentLimitsConfigs();
        driveCurrent.withSupplyCurrentLimit(config.getDriveCurrentLimit());
        driveCurrent.withSupplyCurrentLimitEnable(true);
        driveConfig.apply(driveCurrent);

        CurrentLimitsConfigs turnCurrent = new CurrentLimitsConfigs();
        turnCurrent.withSupplyCurrentLimit(config.getTurnCurrentLimit());
        turnCurrent.withSupplyCurrentLimitEnable(true);
        driveConfig.apply(turnCurrent);

        MagnetSensorConfigs magnetConfig = new MagnetSensorConfigs();
        magnetConfig.withMagnetOffset(config.getMagnetOffset());
        magnetConfig.withAbsoluteSensorRange(AbsoluteSensorRangeValue.Unsigned_0To1);
        encoderConfig.apply(magnetConfig);
        
        absPosition = absEncoder.getAbsolutePosition();
        driveAppliedCurrent = driveMotor.getSupplyCurrent();
        turnAppliedCurrent = turnMotor.getSupplyCurrent();

        absPosition.setUpdateFrequency(50);
        driveAppliedCurrent.setUpdateFrequency(50);
        turnAppliedCurrent.setUpdateFrequency(50);

        driveMotor.optimizeBusUtilization();
        turnMotor.optimizeBusUtilization();
        absEncoder.optimizeBusUtilization();
    }

    @Override
    public void updateInputs(ModuleIOInputs inputs) {
        inputs.absTurnPosition = Rotation2d.fromRotations(absPosition.getValueAsDouble());
        inputs.driveCurrent = driveAppliedCurrent.getValueAsDouble();
        inputs.turnCurrent = turnAppliedCurrent.getValueAsDouble();
        inputs.driveVolts = driveMotor.get() * RobotController.getBatteryVoltage();
        inputs.turnVolts = turnMotor.get() * RobotController.getBatteryVoltage();
    }

    @Override
    public void driveVolts(double voltage) {
        driveMotor.setVoltage(voltage);
    }

    @Override
    public void turnVolts(double voltage) {
        turnMotor.setVoltage(voltage);
    }
}
