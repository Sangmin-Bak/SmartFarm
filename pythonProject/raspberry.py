from datetime import datetime

import serial
import time
import pymysqldht_pin = board.get_pin('d:2:')


ser = serial.Serial(
        port="/dev/ttyACM0", \
        baudrate=9600, \
#        parity=serial.PARITY_NONE, \
#        sdht_pin = board.get_pin('d:2:')
topbits=serial.STOPBITS_ONE, \
#        bytesize=serial.EIGHBITS, \
        timeout=0)
print(ser.portstr)

def send_time():
    time = datetime.now()
    print(time.hour)
    ser.write(time.hour)

try:
    while True: #
        send_time()
        #res = ser.readline()
        #print(res,decode()[:len(res)-1])
       # print(res.decode()[:-2])
       # time.sleep(5)
    # print("Received : '{}'", format(res))
except KeyboardInterrupt:
    res.close()
