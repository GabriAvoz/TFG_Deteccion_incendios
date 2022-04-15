//Declaración de pines
const int pinDHT22 = 7;
const int Led = 13;
const int digFlame = 3;
const int anFlame = A3;
const int chipSelect = 4;

//Tarjeta microSD
#include <SD.h>

//Elementos necesarios para el sensor de humedad y temperatura
#include "DHT.h"
#define DHTTYPE DHT22
DHT dht(pinDHT22, DHTTYPE);

//Elementos necesarios para la conexion
String server = "192.168.1.57";
String cadena="";

//Declaración de otras variables
int val;
float flame;
unsigned long t0;
unsigned long interval = 10000;
String dataSD;
int id = 0;


void setup() {
  Serial.begin(9600);
  pinMode(Led, OUTPUT);
  pinMode(digFlame, INPUT);
  pinMode(anFlame, INPUT);
  digitalWrite(Led, LOW);

  //Inicializamos DHT22
  dht.begin();

  //Comprobamos SD
  if(!SD.begin(chipSelect)){
    Serial.println("Error en la SD");
  }

  //Inicializar ESP
  Serial1.begin(9600);
  Serial1.setTimeout(2000);

  //Verificamos si el ESP8266 responde
  Serial1.println("AT");
  if(Serial1.find("OK"))
    Serial.println("Respuesta AT correcto");
  else
    Serial.println("Error en ESP8266");

    //ESP8266 en modo estación (nos conectaremos a una red existente)
    Serial1.println("AT+CWMODE=1");
    if(Serial1.find("OK"))
      Serial.println("ESP8266 en modo Estacion");
      
    //Nos conectamos a una red wifi 
    Serial1.println("AT+CWJAP=\"JARVIS\",\"B4by Gr00t\"");
    Serial.println("Conectandose a la red ...");
    Serial1.setTimeout(10000); //Aumentar si demora la conexion
    if(Serial1.find("OK"))
      Serial.println("WIFI conectado");
    else
      Serial.println("Error al conectarse en la red");
    Serial1.setTimeout(2000);
    //Desabilitamos las conexiones multiples
    Serial1.println("AT+CIPMUX=0");
    if(Serial1.find("OK"))
      Serial.println("Multiconexiones deshabilitadas");
  

  delay(1000);
  t0 = millis();

}

void loop() { 
    //Medida
    id++;
    dataSD = "";
    
    //Sensor de humedad y temperatura
    float temp = dht.readTemperature();
    float hum = dht.readHumidity();  
  
    //Sensor de llama
    Serial.println("----------------¿Tienes fuego?--------------------");
    delay(5000);
    
    flame = analogRead(anFlame);
    if(flame < 200){
      digitalWrite(Led, HIGH);
    }
    else{
      digitalWrite(Led, LOW);
    }
  
    //Mostramos los print
    Serial.println("");
    Serial.print("Sensor de humedad y temperatura: ");
    Serial.print(temp);
    Serial.print(" *C, ");
    Serial.print(hum);
    Serial.println(" %");
    Serial.print("Sensor de llama: ");
    Serial.println(flame);
    Serial.print("Tiempo = ");
    Serial.print(millis()- t0);
    Serial.println(" ms");
  
    //Guardamos los datos en la SD:
    //Preparamos el String
    dataSD += String(id);
    dataSD += ",";
    dataSD += String(temp);
    dataSD += ",";
    dataSD += String(hum);
    dataSD += ",";
    dataSD += String(flame);
    dataSD += ",";
    dataSD += String(millis()-t0);
    dataSD += ";";
  
    //Abrimos el archivo.csv
    File dataFile = SD.open("datalog.csv", FILE_WRITE);
    if (dataFile){
      //Serial.println(dataSD);
      dataFile.println(dataSD);
      dataFile.close();
    }
  
    //Subimos los datos a MySQL
    //Nos conectamos con el servidor:       
    Serial1.println("AT+CIPSTART=\"TCP\",\"" + server + "\",80");
    if( Serial1.find("OK")){  
      Serial.println("ESP8266 conectado con el servidor...");
  
      //Armamos el encabezado de la peticion http
      String peticionHTTP= "GET /tfg_db/add_measurements_node1.php?temperature=";
      peticionHTTP=peticionHTTP+String(temp)+"&humidity="+String(hum)+"&flame=";
      peticionHTTP=peticionHTTP+String(flame)+" HTTP/1.1\r\n";
      peticionHTTP=peticionHTTP+"Host: " + server + "\r\n\r\n";
    
      //Enviamos el tamaño en caracteres de la peticion http:  
      Serial1.print("AT+CIPSEND=");
      Serial1.println(peticionHTTP.length());
  
      //esperamos a ">" para enviar la petcion  http
      if(Serial1.find(">")) // ">" indica que podemos enviar la peticion http{
        Serial.println("Enviando HTTP . . .");
        Serial1.println(peticionHTTP);
        if( Serial1.find("SEND OK")){  
          //Serial.println("Peticion HTTP enviada:");
          //Serial.println();
          //Serial.println(peticionHTTP);
          //Serial.println("Esperando respuesta...");
              
          boolean fin_respuesta=false; 
          long tiempo_inicio=millis(); 
          cadena="";
              
          while(fin_respuesta==false){
            while(Serial1.available()>0){
              char c=Serial1.read();
              //Serial.write(c);
              cadena.concat(c);  //guardamos la respuesta en el string "cadena"
            }
            //finalizamos si la respuesta es mayor a 500 caracteres
            if(cadena.length()>500){      //Pueden aumentar si tenen suficiente espacio en la memoria
              Serial.println("La respuesta ha excedido el tamaño maximo");               
              Serial1.println("AT+CIPCLOSE");
              if( Serial1.find("OK"))
                  Serial.println("Conexion finalizada");
                  fin_respuesta=true;
              }
              if((millis()-tiempo_inicio)>10000){      //Finalizamos si ya han transcurrido 10 seg
                Serial.println("Tiempo de espera agotado");
                Serial1.println("AT+CIPCLOSE");
                if( Serial1.find("OK"))
                  Serial.println("Conexion finalizada");
                fin_respuesta=true;
              }
              if(cadena.indexOf("CLOSED")>0){    //si recibimos un CLOSED significa que ha finalizado la respuesta
                Serial.println();
                Serial.println("Cadena recibida correctamente, conexion finalizada");         
                fin_respuesta=true;
              }
            }
          }
          else{
            Serial.println("No se ha podido enviar HTTP.....");
          }            
        }
        else{
          Serial.println("No se ha podido conectarse con el servidor");
        }
        Serial.print("Tiempo final = ");
        Serial.print(millis()- t0);
        Serial.println(" ms");
        Serial.print("Vuelta = ");
        Serial.println(id);
        delay(108000);
}
