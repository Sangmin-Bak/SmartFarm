from pyfirmata import Arduino, util
import time
import Adafruit_DHT 

board = Arduino('/dev/ttyACM0')
pinPWM = board.get_pin('d:5:p')

dht22 = Adafruit_DHT.DHT11

def ArduinoControl():
    while True:
        pinPWM.write(0.05)
        board.pass_time(1)
        pinPWM.write(1)
        board.pass_time(1)
        pinPWM.write(0)
        board.pass_time(1)

if __name__=="__main__":
    ArduinoControl()
