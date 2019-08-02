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

---

---

---

## ``de.htwberlin.lora_multihop_visualization.fragments``

**public class MapFragment extends Fragment implements OnMapReadyCallback**

#### Attribute

| Name                        | Typ                                                         | Access    | Funktion                                                     |
| --------------------------- | ----------------------------------------------------------- | --------- | ------------------------------------------------------------ |
|                             |                                                             |           |                                                              |
| mMap                        | com.google.android.gms.maps.GoogleMap                       | `private` | Map object                                                   |
|                             |                                                             |           |                                                              |
| mMap                        | com.google.android.gms.maps.GoogleMap                       | `private` | Map object                                                   |
|                             |                                                             |           |                                                              |
| mMap                        | com.google.android.gms.maps.GoogleMap                       | `private` | Map object                                                   |
|                             |                                                             |           |                                                              |
| fusedLocationProviderClient | com.google.android.gms.location.FusedLocationProviderClient | `private` | Location provider                                            |
|                             |                                                             |           |                                                              |
| mapView                     | com.google.android.gms.maps.MapView                         | `private` | Provides the view object of the map                          |
|                             |                                                             |           |                                                              |
| location                    | com.google.android.gms.maps.model.LatLng                    | `private` | Current location                                             |
|                             |                                                             |           |                                                              |
| markers                     | java.util.Map<String, Marker>                               | `private` | HashMap that contains the current markers on the map         |
|                             |                                                             |           |                                                              |
| circles                     | java.util.Map<String, Circle>                               | `private` | HashMap that contains the corresponding circle radius of each marker |
|                             |                                                             |           |                                                              |
| listener                    | `IMapListener`                                              | `private` | Listener to communicate with the activity                    |

#### Methoden-Liste

| Methodenkopf                                                 | Zweck                                                        | Testklasse |
| ------------------------------------------------------------ | ------------------------------------------------------------ | ---------- |
| public MapFragment()                                         | Constructor                                                  | -          |
|                                                              |                                                              |            |
| @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) | Will be called when the fragment is created                  | -          |
|                                                              |                                                              |            |
| @Override public void onMapReady(GoogleMap googleMap)        | Called when the GoogleMap is ready and sets the global variable `mMap` |            |
|                                                              |                                                              |            |
| public void getDeviceLocation()                              | Gets the location and sets the global `location`. <strong>This method only sets the global variable and should be called if the location is `null`, if you want to get the location use the `getLocation()` method</strong> | -          |
|                                                              |                                                              |            |
| public LatLng getLocation()                                  | Returns the current location. If the `location` variable is `null` the `getDeviceLocation()` will be called (asynchronously) and a default value will be returned | -          |
|                                                              |                                                              |            |
| public void addHostMarker(LatLng location, String id, int radius) | Adds a purple marker to the map                              | -          |
|                                                              |                                                              |            |
| public void addHostMarker(LatLng location, String id, int radius, String title, String description) | Method overload to pass a title and a description            | -          |
|                                                              |                                                              |            |
| public void addNeighbourMarker(LatLng location, String id, int radius) | Adds a green marker to the map                               | -          |
|                                                              |                                                              |            |
| public void addNeighbourMarker(LatLng location, String id, int radius, String title, String description) | Method overload to pass a title and a description            | -          |
|                                                              |                                                              |            |
| public void removeMarker(String id)                          | Removes a marker from the map                                | -          |
|                                                              |                                                              |            |
| public Map<String, Marker> getMarkers()                      | Returns all the markers on the map                           | -          |



**public interface MapFragment.IMapListener**

#### Methoden-Liste

| Methodenkopf      | Zweck                       | Testklasse |
| ----------------- | --------------------------- | ---------- |
| void onSetUpMap() | Update function for the map | -          |

---

### **public class TerminalFragment extends Fragment**

#### Attribute

| Name             | Typ                           | Access    | Funktion                                             |
| ---------------- | ----------------------------- | --------- | ---------------------------------------------------- |
| sendColor        | `int`                         | `private` | Text color                                           |
|                  |                               |           |                                                      |
| readColor        | `int`                         | `private` | Text color                                           |
|                  |                               |           |                                                      |
| AT_POSTFIX       | `String`                      | `private` | Postfix for each AT CMD                              |
|                  |                               |           |                                                      |
| TAG              | `String`                      | `private` | Fragment name                                        |
|                  |                               |           |                                                      |
| scrollView       | `android.widget.ScrollView`   | `private` | ScrollView element in the xml                        |
|                  |                               |           |                                                      |
| terminalMessages | `android.widget.LinearLayout` | `private` | LinearLayout that contains all the terminal messages |
|                  |                               |           |                                                      |
| sendButton       | `android.widget.Button`       | `private` | Button to send a message                             |
|                  |                               |           |                                                      |
| returnButton     | `android.widget.Button`       | `private` | Button to return to the `MapFragment`                |

#### Methoden-Liste

| Methodenkopf                                                 | Zweck                                                        | Testklasse |
| ------------------------------------------------------------ | ------------------------------------------------------------ | ---------- |
| public TerminalFragment()                                    | Constructor                                                  | -          |
|                                                              |                                                              |            |
| @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) | Will be called when the fragment is created. Contains the listeners for both buttons | -          |
|                                                              |                                                              |            |
| public synchronized boolean updateTerminalMessages(int color, String message, boolean isSendMessage) | Adds a message to the terminal                               | -          |

---

### **public class NeighbourSetTableFragment extends Fragment**

#### Attribute

| Name      | Typ                                           | Access    | Funktion                                                    |
| --------- | --------------------------------------------- | --------- | ----------------------------------------------------------- |
| table     | `android.widget.TableLayout`                  | `private` | Contains the table layout                                   |
|           |                                               |           |                                                             |
| tableData | `java.util.Map<String, NeighbourSetTableRow>` | `private` | Contains the table data                                     |
|           |                                               |           |                                                             |
| listener  | `ITableListener`                              | `private` | Listener to communicate with the Activity / other Fragments |

#### Methoden-Liste

| Methodenkopf                                                 | Zweck                                        | Testklasse |
| ------------------------------------------------------------ | -------------------------------------------- | ---------- |
| public NeighbourSetTableFragment()                           | Constructor                                  | -          |
|                                                              |                                              |            |
| @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) | Will be called when the fragment is created. | -          |
|                                                              |                                              |            |
| public void addRow(String id, String address, String dah, String latitude, String longitude, String state, String timestamp) | Adds a row to the table                      | -          |
|                                                              |                                              |            |
| public NeighbourSetTableRow getRow(String id)                | Returns a row                                | -          |
|                                                              |                                              |            |
| public Map<String, NeighbourSetTableRow> getTableData()      | Returns all the items on the table           | -          |
|                                                              |                                              |            |
| public void removeRow(String id)                             | Removes a row                                | -          |



**public interface NeighbourSetTableFragment.ITableListener**

#### Methoden-Liste

| Methodenkopf                              | Zweck                                               | Testklasse |
| ----------------------------------------- | --------------------------------------------------- | ---------- |
| void onRowAdded(NeighbourSetTableRow row) | Will be called when a row is added to the table     | -          |
|                                           |                                                     |            |
| void onRowRemoved(String id)              | Will be called when a row is removed from the table |            |

---

### **public class MainFragmentsAdapter extends FragmentStatePagerAdapter**

#### Attribute

| Name               | Typ              | Access    | Funktion                                  |
| ------------------ | ---------------- | --------- | ----------------------------------------- |
| mFragmentList      | `java.util.List` | `private` | Contains a list with every fragment       |
|                    |                  |           |                                           |
| mFragmentTitleList | `java.util.List` | `private` | Contains a list with every fragment title |

#### Methoden-Liste

| Methodenkopf                                             | Zweck                           | Testklasse |
| -------------------------------------------------------- | ------------------------------- | ---------- |
| public MainFragmentsAdapter(FragmentManager fm)          | Constructor                     | -          |
|                                                          |                                 |            |
| public void addFragment(Fragment fragment, String title) | Adds a fragment to the list     | -          |
|                                                          |                                 |            |
| @Override public Fragment getItem(int position)          | Returns a fragment              | -          |
|                                                          |                                 |            |
| @Override public int getCount()                          | Returns the number of fragments | -          |

---

---

---

## ``de.htwberlin.lora_multihop_visualization.custom``

### **public class NeighbourSetTableHead extends TableRow**

#### Attribute

| Name      | Typ                       | Access   | Funktion                          |
| --------- | ------------------------- | -------- | --------------------------------- |
| uid       | `android.widget.TextView` | `public` | Text which contains the uid       |
|           |                           |          |                                   |
| address   | `android.widget.TextView` | `public` | Text which contains the address   |
|           |                           |          |                                   |
| dah       | `android.widget.TextView` | `public` | Text which contains the dah       |
|           |                           |          |                                   |
| latitude  | `android.widget.TextView` | `public` | Text which contains the latitude  |
|           |                           |          |                                   |
| longitude | `android.widget.TextView` | `public` | Text which contains the longitude |
|           |                           |          |                                   |
| status    | `android.widget.TextView` | `public` | Text which contains the status    |
|           |                           |          |                                   |
| timestamp | `android.widget.TextView` | `public` | Text which contains the timestamp |

#### Methoden-Liste

| Methodenkopf                                  | Zweck      | Testklasse |
| --------------------------------------------- | ---------- | ---------- |
| public NeighbourSetTableHead(Context context) | Contructor | -          |

### **Zusammenwirken**

* Component thats models the table head of the `NeighbourSetTable`



---

### **public class NeighbourSetTableRow extends TableRow**

#### Attribute

| Name      | Typ                       | Access   | Funktion                          |
| --------- | ------------------------- | -------- | --------------------------------- |
| uid       | `android.widget.TextView` | `public` | Text which contains the uid       |
|           |                           |          |                                   |
| address   | `android.widget.TextView` | `public` | Text which contains the address   |
|           |                           |          |                                   |
| dah       | `android.widget.TextView` | `public` | Text which contains the dah       |
|           |                           |          |                                   |
| latitude  | `android.widget.TextView` | `public` | Text which contains the latitude  |
|           |                           |          |                                   |
| longitude | `android.widget.TextView` | `public` | Text which contains the longitude |
|           |                           |          |                                   |
| status    | `android.widget.TextView` | `public` | Text which contains the status    |
|           |                           |          |                                   |
| timestamp | `android.widget.TextView` | `public` | Text which contains the timestamp |

#### Methoden-Liste

| Methodenkopf                                   | Zweck                    | Testklasse |
| ---------------------------------------------- | ------------------------ | ---------- |
| public NeighbourSetTableRow(Context context)   | Constructor              | -          |
|                                                |                          |            |
| public void setUidText(String uid)             | Setter for the UID       | -          |
|                                                |                          |            |
| public void setAddressText(String address)     | Setter for the address   | -          |
|                                                |                          |            |
| public void setDahText(String dah)             | Setter for the DAH       | -          |
|                                                |                          |            |
| public void setLatitudeText(String latitude)   | Setter for the latitude  | -          |
|                                                |                          |            |
| public void setLongitudeText(String longitude) | Setter for the longitude | -          |
|                                                |                          |            |
| public void setStatusText(String status)       | Setter for the status    | -          |
|                                                |                          |            |
| public void setTimestampText(String timestamp) | Setter for the timestamp | -          |
|                                                |                          |            |
| public String getUid()                         | Getter for the UID       | -          |
|                                                |                          |            |
| public String getAddress()                     | Getter for the address   | -          |
|                                                |                          |            |
| public String getDah()                         | Getter for the DAH       | -          |
|                                                |                          |            |
| public Double getLatitude()                    | Getter for the latitude  | -          |
|                                                |                          |            |
| public Double getLongitude()                   | Getter for the longitude | -          |
|                                                |                          |            |
| public String getStatus()                      | Getter for the status    | -          |
|                                                |                          |            |
| public String getTimestamp()                   | Getter for the timestamp | -          |