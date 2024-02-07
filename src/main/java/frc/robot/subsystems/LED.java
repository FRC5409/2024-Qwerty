package frc.robot.subsystems;

// 5409: The Chargers
// http://github.com/FRC5409

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.kLED;
import frc.robot.Utils.Color;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;

public class LED extends SubsystemBase {

    public enum State {
        kSolid(0), kBlink(1), kSinWave(2), kSinFlow(3), kRainbowCycle(4), kRainbowBlink(5);

        State(int value) {
            state = value;
        }

        public final int state;
    }

    private static LED instance = null;

    private final AddressableLED led;
    private final AddressableLEDBuffer buffer;

    private State state;
    
    private Color primeColor;
    private Color secondColor;

    private double LEDTimer = 0.0;
    private double animationTime = 0.0;

    private LED() {
        led = new AddressableLED(0);
        buffer = new AddressableLEDBuffer(kLED.LEDCount);
        
        led.setLength(buffer.getLength());

        led.setData(buffer);
        led.start();

        setState(State.kSinFlow, Color.kCyan, Color.kBlack);

        reset();
    }

    public void setState(State state, Color prime, Color second) {
        this.state = state;
        primeColor = prime;
        secondColor = second;

        reset();
    }

    // Get subsystem
    public static LED getInstance() {
        if (instance == null) instance = new LED();

        return instance;
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run

        switch (state) {
            case kBlink:
                blinkLED();
                break;
            case kRainbowBlink:
                rainbowBlink();
                break;
            case kRainbowCycle:
                rainbowCycle();
                break;
            case kSinFlow:
                sinFlow();
                break;
            case kSinWave:
                sinWave();
                break;
            case kSolid:
                setLEDColor(primeColor);
                break;
        }
        
        led.setData(buffer);
    }

    public void reset() {
        LEDTimer = 0.0;
        animationTime = 0.0;
    }

    public void setLEDColor(Color color) {
        for (int i = 0; i < kLED.LEDCount; i++) {
            setLEDColorAt(color, i);
        }
    }

    public void setLEDColorAt(Color color, int index) {
        buffer.setRGB(index, color.red, color.green, color.blue);
    }    

    public void blinkLED() {
        if (LEDTimer <= kLED.BLINK_SPEED) {
            setLEDColor(primeColor);
        } else if (LEDTimer <= kLED.BLINK_SPEED * 2) {
            setLEDColor(secondColor);
        } else {
            LEDTimer = 0.0;
            animationTime = 0;
            return;
        }

        LEDTimer++;
    }

    // sinWave LED function (Doesn't actually use the sin function named it after Lex's function)
    public void sinWave() {
        LEDTimer += kLED.SIN_SPEED;

        for (int i = 0; i < kLED.LEDCount; i++) {
            if (((i + LEDTimer) % (kLED.SIN_COUNT * 2)) <= kLED.SIN_COUNT) {
                setLEDColorAt(primeColor, i);
            } else {
                setLEDColorAt(secondColor, i);
            }
        }
    }

    // sinFlow LED function, alternates the movement in a Sin fasion, closely related to the sineWave LED function
    public void sinFlow() {
        LEDTimer++;

        animationTime = Math.sin(LEDTimer * kLED.SIN_HS) * kLED.SIN_VS;

        // Makes the function smoother
        if (Math.abs(animationTime) >= kLED.SIN_VS - 0.5) LEDTimer += 2;
        
        animationTime = Math.floor(animationTime);

        for (int i = -kLED.SIN_COUNT; i < kLED.LEDCount + kLED.SIN_COUNT; i++) {
            if (((i + animationTime) % (kLED.SIN_COUNT * 2)) <= kLED.SIN_COUNT) {
                if (i >= 0 & i < kLED.LEDCount) setLEDColorAt(primeColor, i);
            } else {
                if (i >= 0 & i < kLED.LEDCount) setLEDColorAt(secondColor, i);
            }
        }
    }

    // rainbowCycle LED function is a rainbow on the LEDs that moves based on the specified speed
    public void rainbowCycle() {
        LEDTimer -= kLED.RAINBOW_CYCLE_SPEED;

        Color[] colors = {
            new Color(255, 0, 0),      // Red
            new Color(255, 69, 0),     // Orange
            new Color(255, 255, 0),    // Yellow
            new Color(0, 255, 0),      // Green
            new Color(0, 0, 255),      // Blue
            new Color(138, 43, 226)    // Violet
        };

        for (int i = 0; i < kLED.LEDCount; i++) {
            setLEDColorAt(colors[(int) (LEDTimer + i) % colors.length], i);
        }
    }

// rainbowBlink LED function cycles through the LEDs and sets the color of all the LEDs to the curret rainbow color
public void rainbowBlink() {
        LEDTimer += kLED.RAINBOW_BLINK_SPEEED;

        Color[] colors = {
            new Color(255, 0, 0),      // Red
            new Color(255, 69, 0),     // Orange
            new Color(255, 255, 0),    // Yellow
            new Color(0, 255, 0),      // Green
            new Color(0, 0, 255),      // Blue
            new Color(138, 43, 226)    // Violet
        };

        setLEDColor(colors[((int) LEDTimer) % colors.length]);
    }

    @Override
    public void simulationPeriodic() {
        // This method will be called once per scheduler run during simulation
        
    }

}
