# AT-routines
Dieses Dokument beschreibt die einzelnen AT-Befehls Routinen, die für eine Neighbour Discovery notwendig sind.

## Routinen:
- init
- shutdown

### init
Diese Routine initialisiert und konfiguriert das Modul so, dass es anschließend für eine Neighbour-Discovery genutzt werden kann.

```
AT+RST
AT+ADDR=LAST_2BYTE_MAC_ADDRESS
AT+CFG=433000000,20,6,10,1,1,0,0,0,0,3000,8,4
```

### shutdown
Diese Routine ..

```
AT+RST
```
