from pyfirmata import Arduino, util
import time
import Adafruit_DHT 

board = Arduino('/dev/ttyACM0')
dht_pin = board.get_pin('d:2:o')
# 토양수분센서와 연결된 핀 설정
Moisture_pin = board.get_pin('a:0:i')

RH, T = Adafruit_DHT.read_retry(Adafruit_DHT.DHT22, board.digital[2])

# 아두이노 loop함수 역할
def ArduinoControl():
    while True:
        # 토양수분센서로 값 읽기
        Moisture = Moisture_pin.read()
        print(Moisture)
#        pinPWM.write(0.05)
        board.pass_time(1)

#def fan():
#    if temp > 25:
#        board.digital

if __name__=="__main__":
    ArduinoControl()
