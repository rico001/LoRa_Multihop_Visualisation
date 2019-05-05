# Action Items
Hier sollen grob die Funktionalitäten der Applikation geplant und konzipiert werden.

## Variablen
- `List<String> availableBTDevices`
- `Integer BT_STATUS` oder evtl. `BluetoothStatus BT_STATUS`


## Methoden
### Bluetooth
Folgendlich werden mögliche Methoden sowie deren Implementierungen dokumentiert. 

#### `List<String> getAllBluetoothDevices()`
- listet alle verfügbaren Blueetooth Geräte auf
- es soll der Bluetooth Name sowie eventuell deren MAC-Adresse dargstellt werden
- Verfügbaren Geräte sollen in einer String-Liste returned werden
- Die Variable `List<String> availableBTDevices` soll mit der Ausgabe gefüllt werden

#### `void connectToBlueetoothDevice(String macAddress) throws XXXX`
- Initiiert eine BT Verbindung mit einem Bluetooth Device
- Ist der `macAddress` nicht in `availableBTDevices` so wird eine Exception geworfen 
- Kann die Verbindung nicht aufgebaut werden, wird die entsprechende Exception geworfen

#### `int getBluetoothConnectionStatus()` 
- Informationen über den aktuellen Verbindungsstatus
- mögliche return-codes:


    -1  =>  NO_CONNECTION
    
    0   =>  CONNECTED
    
    1   =>  ATTEMPTING_CONNECTION
    
    2   => CONNECTION_LOST
    
    tba ...
    
- die Return Codes können in einer Enum Deklariert werden oder als Konstanten, dann bspw:
    -> `BluetoothStatus getBluetoothConnectionStatus()`
    
- der Status der Blueetooth Connection, soll in 
`private static Integer BT_STATUS` geführt werden (oder evtl. `BluetoothStatus BT_STATUS`)

[https://stackoverflow.com/questions/4715865/how-to-programmatically-tell-if-a-bluetooth-device-is-connected-android-2-2
](nützlicher Link)    

#### `void closeBlueetoothConnection()`
- Beendet die aktuelle Blueetooth Verbindung
- ist `BT_STATUS` == 0 (=> NO_CONNECTION) wird die Methode beendet mit `return`.

    
#### `boolean isModulValidAddres(String address)`
- liefert `True` zurück wenn `address` zwischen `0000` und `FFFF` liegt
- ansonsten `False`

### LoRA-Modul Abläufe
In diesem Abschnitt werden Routingen für mögliche Modulkonfigurationen beschrieben

#### `String getVersion()`
`AT+VER`

=> Antwort bei Erfolg: `AT,V0.4,OK`

=> Antwort bei Fehler: noch nicht bekannt
 
- liefert die Version des aktuellen Moduls zurück
- `null` bei Fehler

#### `void sendReset() throws Exception`
`AT+RST` 

=> Antwort bei Erfolg: `AT,OK`

=> Antwort bei Fehler: noch nicht bekannt

- resettet das aktuelle Modul
- Exception werfen bei Fehler

#### `void sendOK() throws Exception`
`AT`

=> Antwort bei Erfolg: `AT,OK`

=> Antwort bei Fehler: noch nicht bekannt

- sendet AT-Kommando zum Modul, welches als eine Art Validierung für eine intakte Modulkommunikation genutzt werden kann
- Exception werfen bei Fehler

#### `void sendConfig(String config) throws Exception`
`AT+CFG=433000000,20,6,10,1,1,0,0,0,0,3000,8,4`

=> Antwort bei Erfolg: `AT,OK`

=> Antwort bei Fehler: `AT,ERR:PARA`

- konfiguriert das jeweilige Modul anhand der mitgegebenen Parameter
- für die Parameter, siehe AT_COMMAND_SET.pdf Seite 5
- Exception werfen bei Fehler

#### `String getAddress()`
`AT+ADDR?`

=> Antwort bei Erfolg: `AT,*ADDRESSE*,OK` 
- Addresse ist im Bereich 0000 - FFFF
- st die Addresse FFFF, so kann das Modul die komplette Kommunikation auf der Frequenz monitorn

=> Antwort bei Fehler: noch nicht bekannt

- liefert die aktuelle Addresse des Moduls zurück
- `null` bei Fehler

#### `void setAddress(String address)`
`AT+ADDR=*address*`

=> Antwort bei Erfolg: `AT,OK` 
- selbst wenn die Addresse nicht valid ist(bspw: 30430303) wird diese in eine valid Addresse übersetzt
- von daher ist notwendig den Parameter `address` auf das richtige Format zu testen (siehe: `isValidModulAddress(String address)`)

=> Antwort bei Fehler: noch nicht bekannt

- setzt die Addresse des Moduls dem Parameter entsprechend

#### `void sendData(Int bytes, String data) throws Exception`

`AT+SEND=*bytes*`

`AT+SEND=*data*`

=> Antwort bei Erfolg: `AT,SENDED`

=> Antwort bei Fehler: noch nicht bekannt

- sendet Daten zu Endpoint
- bytes beschreibt die Größe der zusendenen Daten (zwischen 1 -250 Bytes), ist `data` größer als die 
spezifizieren `bytes` wird der Rest der Daten abgetrennt und nicht versendet


### Neighbour Discovery
In diesem Abschnitt werden benötigte Methoden für eine Neighbour Discovery erläutert

###  `void sendCurrentPosition(Double latitude, Double longitude) throws Exception`
- vermittelt die aktuelle Position des Mobiltelefons
- nutzt für Versand: `sendData(Int bytes, String data)`
- wirft Exception falls VOrgang fehlschlug




      
      
  
