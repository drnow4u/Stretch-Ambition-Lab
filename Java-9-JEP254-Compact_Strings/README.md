#Compact Strings

Play and testing [JEP 254: Compact Strings](https://openjdk.java.net/jeps/254) introduced in JDK 9.

## Build

```shell
docker build -t compact-strings .
```

## Run

```shell
docker run --rm --memory=256m compact-strings | tail -n 1
```

## Results

Host MacBook Pro, CPU i7, 2,2 GHz, RAM 16 GB 1600 MHz DDR3

| Docker Image              | bits    | --memory | Number of object instances     |
|---------------------------|---------|----------|--------------------------------|
| openjdk:8u242             | 64-bit  | 256m     | 205844, 205849, 205841, 205845 |
| openjdk:8u242             | 64-bit  | 512m     | 209150, 209145, 209158, 209147 |
| openjdk:8u242             | 64-bit  | 1024m    | 418579, 418577, 418563, 418585 |
| openjdk:15.0.2            | 64-bit  | 256m     | 216496, 216509, 216501, 216501 |
| openjdk:15.0.2            | 64-bit  | 512m     | 219993, 219988, 219985, 219992 |
| openjdk:15.0.2            | 64-bit  | 1024m    | 440935, 440938, 440930, 440928 |
| i386/openjdk:8u212-alpine | 32-bit  | 256m     | 163860, 163861, 163855, 163861 |
| i386/openjdk:8u212-alpine | 32-bit  | 512m     | 219225, 219232, 219227, 219229 |
| i386/openjdk:8u212-alpine | 32-bit  | 1024m    | 438645, 438657, 438651, 438660 |
