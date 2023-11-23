import ntcore
import time
import math
from rpi_ws281x import PixelStrip, ws
import adafruit_tcs34725 as adaBoard
import ColorSensor

# ssh pi@chargerspi.local
# frc5409
# scp C:\Users\aslsz\OneDrive\Documents\Github\2023-Qwerty\RasberryPi\LED.py pi@chargerspi.local:
# sudo python LED.py

BLUE_TO_RED_RATIO = 2.8
BRIGHTNESS_THRESHOLD = 40

LED_CHANNEL = 0
LED_COUNT = 60              # How many LEDs to light.
LED_FREQ_HZ = 800000        # Frequency of the LED signal.  Should be 800khz or 400khz.
LED_DMA_NUM = 10            # DMA channel to use, can be 0-14.
LED_GPIO = 18               # GPIO connected to the LED signal line.  Must support PWM!
LED_BRIGHTNESS = 155

REFRESH_TIME = 0.05

BLINK_SPEED = 0.25          # in REFRESH_TIME intervals

SIN_COUNT = 8
SIN_SPEED = 0.5

SIN_HS = 0.05
SIN_VS = 30.0

RAINBOW_CYCLE_SPEED = 0.5   # negative speed to reverse
RAINBOW_BLINK_SPEEED = 0.5; # negative speed to reverse

LED_STRIP = ws.SK6812W_STRIP

led = ws.new_ws2811_t()

# Initialize all channels to off
for channum in range(2):
    channel = ws.ws2811_channel_get(led, channum)
    ws.ws2811_channel_t_count_set(channel, 0)
    ws.ws2811_channel_t_gpionum_set(channel, 0)
    ws.ws2811_channel_t_invert_set(channel, 0)
    ws.ws2811_channel_t_brightness_set(channel, 0)

channel = ws.ws2811_channel_get(led, LED_CHANNEL)

ws.ws2811_channel_t_count_set(channel, LED_COUNT)
ws.ws2811_channel_t_gpionum_set(channel, LED_GPIO)
ws.ws2811_channel_t_invert_set(channel, 0)
ws.ws2811_channel_t_brightness_set(channel, LED_BRIGHTNESS)
ws.ws2811_channel_t_strip_type_set(channel, LED_STRIP)

ws.ws2811_t_freq_set(led, LED_FREQ_HZ)
ws.ws2811_t_dmanum_set(led, LED_DMA_NUM)

ws.ws2811_init(led)


inst = ntcore.NetworkTableInstance.getDefault()
inst.startClient4("10.54.9.2") # 10.TE.AM.2
inst.setServer("10.54.9.2") #    10.TE.AM.2

table = inst.getTable("Shuffleboard").getSubTable("LED_COMMUNICATION_TAB")

LEDstate = table.getIntegerTopic("STATE").subscribe(3)
LEDPrimeColor = table.getIntegerArrayTopic("PRIMARY_COLOR").subscribe([242, 242, 5])
LEDSecondColor = table.getIntegerArrayTopic("SECONDARY_COLOR").subscribe([0, 0, 0])

MultiVariate = table.getDoubleTopic("MULTIVARIATE").subscribe(0)

sensorObject = table.getIntegerTopic("SENSOR_COLOR_STATE").publish()

sensorState = -1

while not inst.isConnected():
    time.sleep(0.5)
    sensorObject.set(0)
    sensorState = 0



lastState = -1
state = -1

LEDTimer = 0
animationTime = 0

def Color(r, g, b):
    return int('{:02x}{:02x}{:02x}'.format(r, g, b), 16)

def clamp(n, min, max): 
    if n < min: 
        return min
    elif n > max: 
        return max
    else: 
        return n 

def getMultivariate(multivariateContester):
    if (inst.isConnected()):
        mul = 0
        if MultiVariate.get(0) == -1:
            mul = multivariateContester
        else:
            mul = MultiVariate.get(0)

        return mul
    else:
        return multivariateContester

primaryColor = Color(242, 242, 5)
secondaryColor = Color(0, 0, 0)

# Sets all the LEDs to a colr
def setLEDColor(color):
    for i in range(0, LED_COUNT):
        ws.ws2811_led_set(channel, i, color)

# Sets a single LED at an index to a color
def setLEDColorAt(index, color):
    ws.ws2811_led_set(channel, index, color)

# Blinks the LEDs at a set speed using the primary color and secondary color
def blinkLEDs():
    global LEDTimer, animationTime, primaryColor, secondaryColor, BLINK_SPEED

    BLINK_SPEED = getMultivariate(BLINK_SPEED)

    if LEDTimer <= BLINK_SPEED:
        setLEDColor(primaryColor)
    elif LEDTimer <= BLINK_SPEED * 2:
        setLEDColor(secondaryColor)
    else:
        LEDTimer = 0.0
        animationTime = 0
        return

    LEDTimer += REFRESH_TIME

# sinWave LED function (Doesn't actually use the sin function named it after Lex's function)
def sinWave(): 
    global LEDTimer, primaryColor, secondaryColor, SIN_SPEED
    SIN_SPEED = getMultivariate(SIN_SPEED)
    LEDTimer += SIN_SPEED

    for i in range(0, LED_COUNT):
        if (((i + LEDTimer) % (SIN_COUNT * 2)) <= SIN_COUNT):
            setLEDColorAt(i, primaryColor)
        else:
            setLEDColorAt(i, secondaryColor)

# sinFlow LED function, alternates the movement in a Sin fasion, closely related to the sineWave LED function
def sinFlow():
    global LEDTimer, animationTime, primaryColor, secondaryColor
    LEDTimer += 1

    animationTime = math.sin(LEDTimer * SIN_HS) * SIN_VS

    # Makes the function smoother
    if (abs(animationTime) >= SIN_VS - 0.5):
        LEDTimer += 2
    
    animationTime = math.floor(animationTime)

    for i in range(-SIN_COUNT, LED_COUNT + SIN_COUNT):
        if (((i + animationTime) % (SIN_COUNT * 2)) <= SIN_COUNT):
            if (i >= 0 & i < LED_COUNT):
                setLEDColorAt(i, primaryColor)
        else:
            if (i >= 0 & i < LED_COUNT):
                setLEDColorAt(i, secondaryColor)

# rainbowCycle LED function is a rainbow on the LEDs that moves based on the specified speed
def rainbowCycle():
    global LEDTimer, RAINBOW_CYCLE_SPEED
    RAINBOW_CYCLE_SPEED = getMultivariate(RAINBOW_CYCLE_SPEED)
    LEDTimer -= RAINBOW_CYCLE_SPEED

    rainbow_colors_rgb = [
        Color(255, 0, 0),      # Red
        Color(255, 69, 0),     # Orange
        Color(255, 255, 0),    # Yellow
        Color(0, 255, 0),      # Green
        Color(0, 0, 255),      # Blue
        Color(138, 43, 226)    # Violet
    ]

    for i in range(LED_COUNT):
            setLEDColorAt(i, rainbow_colors_rgb[int(LEDTimer + i) % len(rainbow_colors_rgb)])

# rainbowBlink LED function cycles through the LEDs and sets the color of all the LEDs to the curret rainbow color
def rainbowBlink():
    global LEDTimer, RAINBOW_BLINK_SPEED
    RAINBOW_BLINK_SPEED = getMultivariate(RAINBOW_BLINK_SPEED)
    LEDTimer += RAINBOW_BLINK_SPEED

    rainbow_colors_rgb = [
        Color(255, 0, 0),      # Red
        Color(255, 69, 0),     # Orange
        Color(255, 255, 0),    # Yellow
        Color(0, 255, 0),      # Green
        Color(0, 0, 255),      # Blue
        Color(138, 43, 226)    # Violet
    ]

    setLEDColor(rainbow_colors_rgb[(int(LEDTimer)) % len(rainbow_colors_rgb)])

def setSensorEntry(data):
    global sensorState
    if sensorState != data:
        sensorState = data
        if inst.isConnected():
            sensorObject.set(data)
        else:
            

while True:
    if inst.isConnected():
        lastState = state
        state = LEDstate.get()

        nt = LEDPrimeColor.get()

        primaryColor = Color(nt[0], nt[1], nt[2])

        nt = LEDSecondColor.get()

        secondaryColor = Color(nt[0], nt[1], nt[2])

        if lastState != state:
            LEDTimer = 0.0

        if state == 0:
            setLEDColor(primaryColor)
        elif state == 1:
            blinkLEDs()
        elif state == 2:
            sinWave()
        elif state == 3:
            sinFlow()
        elif state == 4:
            rainbowCycle()
        elif state == 5:
            rainbowBlink()
    else:
        # Default animation before connected
        rainbowCycle()

    currentColor = ColorSensor.getCurrentColor()

    if ColorSensor.getBrightness() < BRIGHTNESS_THRESHOLD:
        # Object detected, check color

        if currentColor[0] > currentColor[2] * BLUE_TO_RED_RATIO:
            # Red object detected
            setSensorEntry(1)
            if not inst.isConnected():
                setLEDColor(Color(255, 0, 0))
        else:
            # Blue object detected
            setSensorEntry(2)
            if not inst.isConnected():
                setLEDColor(Color(0, 0, 255))
    else:
        setSensorEntry(0)

    # Display color
    # setLEDColor(
    #     Color(
    #         clamp(currentColor[0], 0, 255),
    #         clamp(currentColor[1], 0, 255),
    #         clamp(currentColor[2], 0, 255)
    #         )
    #     )

    ws.ws2811_render(led)
    time.sleep(REFRESH_TIME)