#include <SD.h>
#include "DHT.h"

const int chipSelect = 4;
const int pinDHT22 = 7;

#define DHTTYPE DHT22
DHT dht(pinDHT22, DHTTYPE);

String dataSD;
int id = 0;
unsigned long t0;

void setup(){
  Serial.begin(9600);
  
  Serial.print("Iniciando SD ...");
  if (!SD.begin(chipSelect)) {
    Serial.println("Error en la SD");
    return;
  }
  Serial.println("inicializacion exitosa");

  dht.begin();

  delay(1000);
  t0 = millis();
}

void loop(){
    id++;
    dataSD = "";

    float temp = dht.readTemperature();
    float hum = dht.readHumidity(); 

    dataSD += String(id);
    dataSD += ",";
    dataSD += String(temp);
    dataSD += ",";
    dataSD += String(hum);
    dataSD += ",";
    dataSD += String(millis()-t0);

    File dataFile = SD.open("dht22.csv", FILE_WRITE);
    if (dataFile){
      Serial.println(dataSD);
      dataFile.println(dataSD);
      dataFile.close();
    }

    delay(599637);
}
