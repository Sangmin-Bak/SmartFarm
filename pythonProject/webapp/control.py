from flask import Flask, render_template
from pyfirmata import Arduino, util

app = Flask(__name__)

board = Arduino('/dev/ttyACM0')
#pinPWM = board.get_pin('d:5:p')

#def pinControl():
   # while True:
#     pinPWM.write(0.05)
#     board.pass_time(1)
#     pinPWM.write(1)
#     board.pass_time(1)
#     pinPWM.write(0)
#     board.pass_time(1)
#     return render_template('ledonoff.html', arduino_info=arduino_dict)

@app.route('/led/<action>')
def led_onoff(action):
    if(action == "on"):
        board.digital[6].write(1)
    if(action == "off"):
        board.digital[6].write(0)
    return (''), 204

@app.route('/')
def index():
    return render_template('ledonoff.html')

@app.route('/arduino')
def about():
    return ""

if __name__=="__main__":
    app.run(host="192.168.55.185", port=5000)
