package frc.robot.subsystems;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Utils.Color;
import frc.robot.Utils.Convert;
import frc.robot.Utils.NT4;

public class LED extends SubsystemBase {

    public enum kStates {
        kSolid(0), kBlink(1), kSinWave(2), kSinFlow(3), kRainbowCycle(4), kRainbowBlink(5);

        kStates(int value) {
            state = value;
        }

        public final int state;
    }

    private static LED instance = null;

    private final ShuffleboardTab sb_tab;
    private final GenericEntry sb_colorState;

    private final Color primaryColor;
    private final Color secondaryColor;

    private kStates state;

    private double multivariate;

    private double blinkSpeed        = 0.25;
    private double sinSpeed          = 0.5;
    private double rainbowCycleSpeed = 0.5;
    private double rainbowBlinkSpeed = 0.5;

    private LED() {
        state = kStates.kSinWave;
        multivariate = -1;
        primaryColor = Color.kGold;
        secondaryColor = Color.kBlack;

        sb_tab = NT4.getInstance("LED_COMMUNICATION_TAB");
        sb_tab.addInteger("STATE", () -> getState().state);
        sb_tab.addIntegerArray("PRIMARY_COLOR", () -> Convert.intToLong(getPrimaryColor()));
        sb_tab.addIntegerArray("SECONDARY_COLOR", () -> Convert.intToLong(getSecondaryColor()));
        sb_tab.addDouble("MULTIVARIATE", () -> getMultivariate());

        sb_colorState = sb_tab.add("SENSOR_COLOR_STATE", -1).getEntry();
    }

    // Get subsystem
    public static LED getInstance() {
        if (instance == null) instance = new LED();

        return instance;
    }

    /**
     * Sets the current LED State
     */
    public void setState(kStates state) {
        this.state = state;
    }

    /**
     * Gets the current LED State
     */
    public kStates getState() {
        return state;
    }

    /**
     * Sets the primary color to a three long int array
     */
    public void setPrimaryColor(int[] color) {
        primaryColor.setColor(color[0], color[1], color[2]);
    }

    /**
     * Sets the primary color to a color object
     */
    public void setPrimaryColor(Color color) {
        primaryColor.setColor(color);
    }

    /**
     * Sets the secondary color to a three long int array
     */
    public void setSecondaryColor(int[] color) {
        secondaryColor.setColor(color[0], color[1], color[2]);
    }

    /**
     * Sets the secondary color to a color object
     */
    public void setSecondaryColor(Color color) {
        secondaryColor.setColor(color);
    }

    /**
     * Returns the primary color as an int array
     */
    public int[] getPrimaryColor() {
        return primaryColor.getColor();
    }

    /**
     * Returns the secondary color as an int array
     */
    public int[] getSecondaryColor() {
        return secondaryColor.getColor();
    }

    /**
     * Sets the multivariate var to parameter
     * This for example changes the blinking speed on LEDs when the LEDs state is set to kBlink
     */
    public void setMultivariate(double val) {
        multivariate = val;
    }

    /**
     * Get the multivariate
     */
    public double getMultivariate() {
        double var;

        if (state.state == kStates.kBlink.state)          var = blinkSpeed;
        if (state.state == kStates.kSinWave.state)        var = sinSpeed;
        if (state.state == kStates.kRainbowCycle.state)   var = rainbowCycleSpeed;
        if (state.state == kStates.kRainbowBlink.state)   var = rainbowBlinkSpeed;
        else 
        var = -1;

        multivariate = var;

        return multivariate;
    }

    public void changeLED(kStates state, Color primary, Color secondary) {
        setState(state);
        setPrimaryColor(primary);
        setSecondaryColor(secondary);
    }

    /**
     * Gets the REV Color Sensor V3 in from the raspi using I2C
     */
    public int getSensorColorState() {
        return (int) sb_colorState.getInteger(-1);
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
        if (getSensorColorState() == 0) changeLED(kStates.kSinFlow, Color.kYellow, Color.kDarkTurquoise);
        if (getSensorColorState() == 1) changeLED(kStates.kSolid, Color.kPureRed, Color.kRed);
        if (getSensorColorState() == 2) changeLED(kStates.kSolid, Color.kPureBlue, Color.kBlue);
    }

    @Override
    public void simulationPeriodic() {
        // This method will be called once per scheduler run during simulation
        
    }

}