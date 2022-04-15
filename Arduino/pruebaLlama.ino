int Led = 13;
int digital = 3;
int analog = A3;
int val;
float sensor;

void setup() {
  pinMode(Led, OUTPUT);
  pinMode(digital, INPUT);
  pinMode(analog, INPUT);
  Serial.begin(9600);
  digitalWrite(Led, LOW);
  delay(5000);
}

void loop() {
      sensor = analogRead(analog);
      val = digitalRead(digital);
      if(val == HIGH){
        digitalWrite(Led, HIGH);
      }
      else{
        digitalWrite(Led, LOW);
      }
      Serial.print("Valor ");
      Serial.print(m);
      Serial.print(".");
      Serial.print(i);
      Serial.print(" = ");
      Serial.println(sensor);
      delay(10000);
    
}
