#from flask import Flask, render_template
from pyfirmata import Arduino, util

#app = Flask(__name__)

board = Arduino('/dev/ttyACM0')
pinPWM = board.get_pin('d:5:p')

def readPin():
    while True:
        pinPWM.write(0.05)
        board.pass_time(1)
        pinPWM.write(1)
        board.pass_time(1)
        pinPWM.write(0)
        board.pass_time(1)

if __name__=="__main__":
    readPin()
