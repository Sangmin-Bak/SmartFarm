import time
import serial

ser = serial.Serial(
        port="/dev/ttyACM0", 
        baudrate=9600, 
        timeout=0)
print(ser.portstr)

def get_CO2():
    if ser.readable():
        ppm = ser.readline()
        print(ppm.decode()[:len(ppm)-1])

def get_dht():
    if ser.readable():
        dht = ser.readline()
        print(dht.decode()[:len(dht)-1])

def get_water():
    if ser.readable():
        water = ser.readline()
        print(water.decode()[:len(water)-1])

def main():
    while True:
        get_CO2()
        time.sleep(1)
        get_dht()
        time.sleep(1)
        get_water()
        time.sleep(1)

if __name__=="__main__":
    main()
