# Struktur - Klassen - & Methodenübersicht

## ``Package``

### **Klasse**

* Klassenname

#### Attribute

| Name | Typ | Funktion |
| --- | --- | --- |
| beispielFeld |  de.htw.lora.package.class | stellt Klasse mit Funktion zur Verfügung |
||||||

#### Methoden-Liste (Tabelleform)

| Methodenkopf | Zweck | AT-Command | AT-Response | Implementiert| Tests |
| --- | --- | --- | --- | --- | --- |
| void beispielMethode(String param1) throws Exception |  erläutert die Tabelle | `AT+OK` | `AT+OK`  | [BeispielMethode.java](https://github.com/rico001/LoRa_Multihop_Visualisation/blob/master/app/src/main/java/de/htwberlin/lora_multihop_visualisation/LoraSettingsActivity.java)  |   [BeispielMethodeTest.java](https://github.com/rico001/LoRa_Multihop_Visualisation/blob/master/app/src/main/java/de/htwberlin/lora_multihop_visualisation/LoraSettingsActivity.java) |
||||||

**oder**

| Methodenkopf | Zweck | Testklasse |
| --- | --- | --- |
| void beispielMethode(String param1) throws Exception | erläutert die Tabelle | [Testklasse.BeispielMethodeTest.java](https://github.com/rico001/LoRa_Multihop_Visualisation/blob/master/app/src/main/java/de/htwberlin/lora_multihop_visualisation/LoraSettingsActivity.java) |
||||||

### **Zusammenwirken**

* Info über das Zusammenspiel einzelner Komponenten

---
---
---

## ``de.htwberlin.lora_multihop_implementation.utils``

### **StringHexConverter**

#### Attribute

| Name | Typ | Funktion |
| --- | --- | --- |
| - |  - | - |
||||||

#### Methoden-Liste

| Methodenkopf | Zweck | Testklasse |
| --- | --- | --- |
| public static String convertStringToHex(String str) | Util zum Umwandlen eines Strings in das HEX Format | - |
||||||
| public static String convertHexToString(String hex) | Util zum Umwandlen von HEX Data in einen String | - |

### **Zusammenwirken**

* kann als Static an allen möglichen Stellen verwendet werden

---
---
---

## ``de.htwberlin.lora_multihop_implementation.interfaces``

### **public interface ILoraCommands**

#### Attribute

| Name | Typ | Funktion |
| --- | --- | --- |
| - |  - | - |
||||||

#### Methoden-Liste

| Methodenkopf | Zweck | Testklasse |
| --- | --- | --- |
| void test(); | - | - |
||||||
| void reset(); | - | - |
||||||
| void getVersion(); | - | - |
||||||
| void enterIdleMode(); | - | - |
||||||
| void enterSleepMode(); | - | - |
||||||
| void exitSleepMode(); | - | - |
||||||
| void enterReceiveMode(); | - | - |
||||||
| void receiveData(); | - | - |
||||||
| void receiveDataTimeout(); | - | - |
||||||
| void getRssiValue(); | - | - |
||||||
| void setAddress(String addr); | - | - |
||||||
| void getAddress(); | - | - |
||||||
| void setTargetAddress(String addr); | - | - |
||||||
| void getTargetAddress(); | - | - |
||||||
| void setAddressFilter(int enable); | - | - |
||||||
| void getAddressFilter(); | - | - |
||||||
| void configure(int frequency, int power, int signalBw, int spreadingFactor, int errorCoding, int crc, int implicitHeaderOn, int rxSingleOn, int frequencyHopOn, int hopPeriod, int rxPacketTimeout, int payloadLength, int preambleLength); | - | - |
||||||
| void save(); | - | - |
||||||
| void send(); | - | - |
||||||
| void send(String data); | - | - |
||||||
| void enterDirectTransmission(String data); | - | - |
||||||
| void exitDirectTransmission(String data); | - | - |
||||||

### **Zusammenwirken**

* bildet die möglichen Commands des HIMO-01M LoRa Moduls ab

---

### **public interface LoraCommandsConstants**

#### Attribute (Konstanten)

| Name | Typ | Acceess | Funktion |
| --- | --- | --- | --- |
| TEST_CMD_MSG | String | - | ``"AT\\r\\n"`` |
||||||
| RESET_CMD_MSG | String | - | ``"AT+RST\\r\\n"`` |
||||||
| GET_VERSION_CMD_MSG | String | - | ``"AT+VER\\r\\n"`` |
||||||
| ENTER_IDLE_MODE_CMD_MSG | String | - | ``"AT+IDLE\\r\\n"`` |
||||||
| ENTER_SLEEP_MODE_CMD_MSG | String | - | ``"AT+SLEEP=1\\r\\n"`` |
||||||
| ENTER_RECEIVE_MODE_CMD_MSG | String | - | ``"AT+RX\\r\\n"`` |
||||||
| GET_RSSI_VALUE_CMD_MSG | String | - | ``"AT+RSSI?\\r\\n"`` |
||||||
| SET_ADDRESS_CMD_MSG | String | - | ``"AT+ADDR=XXXX\\r"`` |
||||||
| GET_ADDRESS_CMD_MSG | String | - | ``"AT+ADDR?\\r\\n"`` |
||||||
| SET_TARGET_ADDRESS_CMD_MSG | String | - | ``"AT+DEST=XXXX\\r\\n"`` |
||||||
| GET_TARGET_ADDRESS_CMD_MSG | String | - | ``"AT+DEST?\\r\\n"`` |
||||||
| SET_ADDRESS_FILTER_CMD_MSG | String | - | ``"AT+ADDREN=X\\r\\n"`` |
||||||
| GET_ADDRESS_FILTER_CMD_MSG | String | - | ``"AT+ADDREN?\\r\\n"`` |
||||||
| CONFIGURE_CMD_MSG | String | - | ``"AT+CFG=frequency,power,signalBw,spreadingFactor,errorCoding,crc,implicitHeaderOn,rxSingleOn,frequencyHopOn,hopPeriod,rxPacketTimeout,payloadLength,preambleLength\\r\\n"`` |
||||||
| SAVE_CMD_MSG | String | - | ``"AT+SAVE\\r\\n"`` |
||||||
| SEND_CMD_MSG | String | - | ``"AT+SEND=XX\\r\\n"`` |
||||||
| ENTER_DIRECT_TRANSMISSION_CMD_MSG | String | - | ``"AT+TSP\\r\\n"`` |
||||||
| EXIT_DIRECT_TRANSMISSION_CMD_MSG | String | - | ``"+++"`` |
||||||
| - | - | - | - |
||||||
| OK_REPLY_MSG | String | - | ``"AT,OK\\r\\n"`` |
||||||
| VERSION_REPLY_MSG | String | - | ``"AT,VERSION,OK\\r\\n"`` |
||||||
| EXIT_SLEEP_MODE_REPLY_MSG | String | - | ``"AT,WakeUp\\r\\n"`` |
||||||
| RECEIVE_DATA_REPLY_MSG | String | - | ``"LR,XXXX,XX,ASFASDFASFD"`` |
||||||
| RECEIVE_DATA_TIMEOUT_REPLY_MSG | String | - | ``"AT,TimeOut\\r\\n"`` |
||||||
| GET_RSSI_VALUE_REPLY_MSG | String | - | ``"AT+RSSI?\\r\\n"`` |
||||||
| GET_ADDRESS_REPLY_MSG | String | - | ``"AT,XXXX,OK\\r\\n"`` |
||||||
| GET_TARGET_ADDRESS_REPLY_MSG | String | - | ``"AT,XXXX,OK\\r\\n"`` |
||||||
| GET_ADDRESS_FILTER_REPLY_MSG | String | - | ``"AT,X,OK\\r\\n"`` |
||||||
| SENDING_DATA_REPLY_MSG | String | - | ``"AT,SENDING\\r\\n"`` |
||||||
| SENDED_DATA_REPLY_MSG | String | - | ``"AT,SENDED\\r\\n"`` |
||||||
| EXIT_DIRECT_TRANSMISSION_ERROR_REPLY_MSG | String | - | ``"AT,busy...\\r\\n"`` |
||||||

#### Methoden-Liste

| Methodenkopf | Zweck | Testklasse |
| --- | --- | --- |
| - | - | - |
||||||

### **Zusammenwirken**

* Interface stellt die eigentlichen LoRa Commands im String-Format als Konstanten für globalen Zugriff bereit

---
---
---

## ``de.htwberlin.lora_multihop_implementation.components.lora``

### **public class LoraCommandsExecutor implements ILoraCommands**

#### Attribute

| Name | Typ | Acceess | Funktion |
| --- | --- | --- | --- |
| btService |  BluetoothService  | private | notwendig zum Senden der LoRa-Befehle über das BT-Modul |
||||||

#### Methoden-Liste

| Methodenkopf | Zweck | Testklasse |
| --- | --- | --- |
| public LoraCommandsExecutor(BluetoothService btService) | Objekterzeugung - BTService als Übergabeparameter | - |
||||||
| @Override public void test() | Wenn mit BT-Modul verbunden, write ``LoraCommandsConstants.TEST_CMD_MSG.getBytes()`` via Bluetooth Service | - |
||||||
| @Override public void reset() | Wenn mit BT-Modul verbunden, write ``LoraCommandsConstants.RESET_CMD_MSG.getBytes()`` via Bluetooth Service | - |
||||||
| @Override public void getVersion() | Wenn mit BT-Modul verbunden, write ``LoraCommandsConstants.GET_VERSION_CMD_MSG.getBytes()`` via Bluetooth Service | - |
||||||
| @Override public void enterIdleMode() | Wenn mit BT-Modul verbunden, write ``LoraCommandsConstants.ENTER_IDLE_MODE_CMD_MSG.getBytes()`` via Bluetooth Service | - |
||||||
| @Override public void enterSleepMode() | Wenn mit BT-Modul verbunden, write ``LoraCommandsConstants.ENTER_SLEEP_MODE_CMD_MSG.getBytes()`` via Bluetooth Service | - |
||||||
| @Override public void exitSleepMode() | - | - |
||||||
| @Override public void enterReceiveMode() | Wenn mit BT-Modul verbunden, write ``LoraCommandsConstants.ENTER_RECEIVE_MODE_CMD_MSG.getBytes()`` via Bluetooth Service | - |
||||||
| @Override public void receiveData() | - | - |
||||||
| @Override public void receiveDataTimeout() | - | - |
||||||
| @Override public void getRssiValue() | Wenn mit BT-Modul verbunden, write ``LoraCommandsConstants.GET_RSSI_VALUE_CMD_MSG.getBytes()`` via Bluetooth Service | - |
||||||
| @Override public void setAddress(String addr) | completeCommand loads ``LoraCommandsConstants.SET_ADDRESS_CMD_MSG`` -> replace placeholder in command with passed addr-param -> wenn mit BT-Modul verbunden, write ``completeCommand.getBytes()`` via Bluetooth Service | - |
||||||
| @Override public void getAddress() | Wenn mit BT-Modul verbunden, write ``LoraCommandsConstants.GET_ADDRESS_CMD_MSG.getBytes()`` via Bluetooth Service | - |
||||||
| @Override public void setTargetAddress(String addr) | completeCommand loads ``LoraCommandsConstants.SET_TARGET_ADDRESS_CMD_MSG`` -> replace placeholder in command with passed addr-param -> wenn mit BT-Modul verbunden, write ``completeCommand.getBytes()`` via Bluetooth Service | - |
||||||
| @Override public void getTargetAddress() | Wenn mit BT-Modul verbunden, write ``LoraCommandsConstants.GET_TARGET_ADDRESS_CMD_MSG.getBytes()`` via Bluetooth Service | - |
||||||
| @Override public void setAddressFilter(int enable) | completeCommand loads ``LoraCommandsConstants.SET_ADDRESS_FILTER_CMD_MSG`` -> replace placeholder in command with passed enable-param (Integer.toString(enable)) -> wenn mit BT-Modul verbunden, write ``completeCommand.getBytes()`` via Bluetooth Service | - |
||||||
| @Override public void getAddressFilter() | Wenn mit BT-Modul verbunden, write ``LoraCommandsConstants.GET_ADDRESS_FILTER_CMD_MSG.getBytes()`` via Bluetooth Service | - |
||||||
| @Override public void configure(int frequency, int power, int signalBw, int spreadingFactor, int errorCoding, int crc, int implicitHeaderOn, int rxSingleOn, int frequencyHopOn, int hopPeriod, int rxPacketTimeout, int payloadLength, int preambleLength) | load ``LoraCommandsConstants.CONFIGURE_CMD_MSG`` in ``String completeCommand`` -> replace placeholder with passed-param in completeCommand -> wenn mit BT-Modul verbunden, write ``completeCommand.getBytes()`` via Bluetooth Service | - |
||||||
| @Override public void save() | Wenn mit BT-Modul verbunden, write ``LoraCommandsConstants.SAVE_CMD_MSG.getBytes()`` via Bluetooth Service | - |
||||||
| @Override public void send() | Wenn mit BT-Modul verbunden, write ``LoraCommandsConstants.SEND_CMD_MSG.getBytes()`` via Bluetooth Service | - |
||||||
| @Override public void send(String data) | Wenn mit BT-Modul verbunden, write ``data.getBytes()`` via Bluetooth Service | - |
||||||
| @Override public void enterDirectTransmission(String data) | Wenn mit BT-Modul verbunden, write ``LoraCommandsConstants.ENTER_DIRECT_TRANSMISSION_CMD_MSG.getBytes()`` via Bluetooth Service | - |
||||||
| @Override public void exitDirectTransmission(String data) | Wenn mit BT-Modul verbunden, write ``LoraCommandsConstants.EXIT_DIRECT_TRANSMISSION_CMD_MSG.getBytes()`` via Bluetooth Service | - |
||||||

### **Zusammenwirken**

* implementiert ``ILoraCommands`` Methoden - möglich: senden der LoRa Commands via BT-Modul (passed via Konstruktor)

---

### **public class LoraHandler extends AppCompatActivity implements MessageConstants**

#### Attribute

| Name | Typ | Acceess | Funktion |
| --- | --- | --- | --- |
| btService |  BluetoothService  | private | Initialisierung + Übergabe an ILoraCommands Implementierung Attribut |
||||||
| loraCommandsExecutor |  ILoraCommands  | private | Objekt um Senden von LoRa Commands via Bluetooth Modul (Attribut: Allgemeines Interface -> Erzeugung: Implementierung von Interface) |
||||||
| msgHandler |  Handler  | private final | notwendig um die LoRa Responses zu empfangen & weiter zu verarbeiten (ähnliche Implementierung wie in Terminal Activity) |
||||||

#### Methoden-Liste

| Methodenkopf | Zweck | Testklasse |
| --- | --- | --- |
| public LoraHandler() | Objekterzeugung | - |
||||||
| private void initBluetoothService() | New BT-Service(this, msgHandler, SingletonDevice.getBluetoothDevice()) Erzeugung + .connectWithBluetoothDevice() | - |
||||||
| private static void processLoraResponse(String responseMsg) | Methode zur Verarbeitung einer vom Handler empfangenen LoRa Response (String responseMsg) | - |
||||||
| public BluetoothService getBtService() | Getter | - |
||||||
| public ILoraCommands getLoraCommandsExecutor() | Getter | - |
||||||
| public Handler getMsgHandler() | Getter | - |
||||||


### **Zusammenwirken**

* Verbindet LoRaCommandsExecutor & BluetoothService
* BluetoothService-Objekt wird erzeugt & verbindet sich mit Bluetooth Modul via .connectWithBluetoothDevice()
* LoRaCommandsExecutor wird mit BluetoothService-Objekt als Übergabeparameter erzeugt
* static Handler kümmert sich über eintreffende LoRa-Responses & ruft .processLoraResponse(String responseMsg) auf
