package frc.robot.subsystems.drive;

public class FalconModuleConfig {
    private int driveID;
    private int turnID;
    private int encoderID;

    private double driveCurrentLimit;
    private double turnCurrentLimit;

    private boolean turnInverted = false;

    private double magnetOffset;

    public FalconModuleConfig withDriveID(int driveID) {
        this.driveID = driveID;

        return this;
    }

    public FalconModuleConfig withTurnID(int turnID) {
        this.turnID = turnID;
        
        return this;
    }

    public FalconModuleConfig withEncoderID(int encoderID) {
        this.encoderID = encoderID;
        
        return this;
    }

    public FalconModuleConfig withDriveCurrentLimit(double limit) {
        this.driveCurrentLimit = limit;
        
        return this;
    }

    public FalconModuleConfig withTurnCurrentLimit(double limit) {
        this.turnCurrentLimit = limit;
        
        return this;
    }

    public FalconModuleConfig withTurnInverted() {
        this.turnInverted = !this.turnInverted;
        
        return this;
    }

    public FalconModuleConfig withMagnetOffset(double offset) {
        this.magnetOffset = offset;
        
        return this;
    }

    public int getDriveID() {
        return driveID;
    }

    public int getTurnID() {
        return turnID;
    }

    public int getCANCoderID() {
        return encoderID;
    }

    public double getDriveCurrentLimit() {
        return driveCurrentLimit;
    }

    public double getTurnCurrentLimit() {
        return turnCurrentLimit;
    }

    public boolean turnInverted() {
        return turnInverted;
    }

    public double getMagnetOffset() {
        return magnetOffset;
    }
}
