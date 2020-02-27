# heart-rate-calculator

![Java CI](https://github.com/l-lin/heart-rate-calculator/workflows/Java%20CI/badge.svg)

> Small Java application that computes the derivative heart rate signals given a file
> containing the heart beats and write the output into a file. 

## Getting started
### Prerequisites

- Java 11
- Maven 3

### Build

Using maven:

```bash
mvn package
```

There are two jar generated:

- `heart-rate-calculator-spring-boot.jar`: executable jar you can use with or without the `java` command
- `heart-rate-calculator.jar`: classic jar to use to execute the application

### Usage

Once the jar are generated, you can use it with the following:

- either directly as an executable jar

```bash
./target/heart-rate-calculator-spring-boot.jar /path/to/heartbeat.in /path/to/heartrate.out
```

- or using Java
```bash
java -jar target/heart-rate-calculator-spring-boot.jar /path/to/heartbeat.in /path/to/heartrate.out
# or
java -jar target/heart-rate-calculator.jar /path/to/heartbeat.in /path/to/heartrate.out
```

You can override properties by adding flags (check the [properties section](#properties) to check the exhaustive list of
properties):

```bash
# e.g. smaller gap duration and you want to generate a CSV instead
java -jar target/heart-rate-calculator.jar \
    /path/to/heartbeat.in \
    /path/to/heartrate.csv \
    --heart-rate.gap-duration=2s \
    --heart-rate.separator=","
```

### Properties

The application has default properties you can override to suit your needs.
You can check them in the [application.yml](src/main/resources/application.yml) file or with the following:

| Property name               | Description                                                            | Default value |
|-----------------------------|------------------------------------------------------------------------|---------------|
| `heart-beat.separator`      | Separator used to parse the heart beat String representation           | " "           |
| `heart-rate.separator`      | Separator used between the heart rate timestamp and its value          | " "           |
| `heart-rate.gap-duration`   | Duration between two consecutive heart beats to be considered as a Gap | 5s            |
| `heart-rate.hri.min`        | Min HRI (Instant Heart Rate) value a heart beat must have              | 0             |
| `heart-rate.hri.max`        | Max HRI (Instant Heart Rate) value a heart beat must have              | 250           |
| `heart-rate.nb-heart-beats` | Number of heart beats to use to compute a heart rate signal            | 8             |

## Contributing

The project is structured in the following way:

```text
.
├── heartbeat                           # contains the classes needed to handle heart beats business
│   ├── config                          # spring config related to heart beat business
│   └── HeartBeatConverter.java         # converts the heart beat string representation into a Java Object one
├── heartrate                           # contains the casses needed to handle heart rates business
│   ├── config                          # spring config related to heart rate business
│   ├── HeartRateComputor.java          # computes the heart rate value from a list of heart beats
│   ├── HeartRateConverter.java         # converts a heart rate Java Object representation into a String one
│   ├── HeartRateFactory.java           # create heart rate from given heart beats
│   ├── HeartRateGenerator.java         # reads the heart beats from InputStream, generate and write the heart rates in OutputStream
│   ├── HeartRateValueConverter.java    # converts the heart rate value from double to String representation
│   └── reset                           # contains the classes that check if a heart rate is reset
└── HeartRateCalculatorApplication.java # main class that execute the whole application code
```

## Why use Spring Boot?

This project serves as a showcase project, hence the use of the framework. Otherwise, it's totally possible to just Java
to develop the project.
