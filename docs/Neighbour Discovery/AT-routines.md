# AT-routines
Dieses Dokument beschreibt die einzelnen AT-Befehls Routinen, die für eine Neighbour Discovery notwendig sind.

## init
Diese Routine initialisiert und konfiguriert das Modul so, dass es anschließend für eine Neighbour-Discovery genutzt werden kann.

* ADDRESS= last 2 bytes from mac address of mobile phone (Range: 0000 - FFFF)

```
AT+RST
AT+ADDR=ADDRESS
AT+CFG=433000000,20,6,10,1,1,0,0,0,0,3000,8,4
```

## sendPositionToDestinationAddress
Versendet die Positionsdaten (latitude, longitude) zur Ziel-Addresse

* DESTINATION_ADDRESS = Address of destination peer
* LATITUDE = latitude   (Range: 0,000 - 180,000)
* LONGITUDE = longitude (Range: 0,000 - 180,000)

```
AT+DEST=DESTINATION_ADDRESS
AT+SEND=15
LATITUDE;LONGITUDE
```

## shutdown
Diese Routine ..

```
AT+RST
```
