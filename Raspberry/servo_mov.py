import sys
import RPi.GPIO as GPIO
import time

mov = float(sys.argv[1])

GPIO.setmode(GPIO.BOARD)
GPIO.setup(21,GPIO.OUT)
p = GPIO.PWM(21,50)
p.start(7.5)

p.ChangeDutyCycle(mov)
time.sleep(1)
p.stop()
GPIO.cleanup()
