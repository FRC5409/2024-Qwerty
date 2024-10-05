package frc.robot.subsystems.drive;

import frc.robot.subsystems.drive.ModuleIO.ModuleIOInputs;

public class Module {
    private ModuleIO io;
    private ModuleIOInputs inputs;

    public Module(ModuleIO io) {
        this.io = io;
    }

    public void updateInputs() {
        io.updateInputs(inputs);
    }
}
