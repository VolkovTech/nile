![Maintaner](https://img.shields.io/badge/maintainer-VolkovTech-blue)
[![GitHub license](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg?style=flat)](https://www.apache.org/licenses/LICENSE-2.0)
[![GitHub tag](https://img.shields.io/github/tag/Naereen/StrapDown.js.svg)](https://github.com/VolkovTech/nile/tags)

# Nile Project

Nile project - is an open-source project, monitoring system, analysis of anomalies and timely warning for modern IT
companies.

`nile-core` - basic functions and utils for building `Gauge Counter` & `Gauge Timer` metrics

`nile-scheduler` - tools for scheduled metrics

`nile-anomaly` - tools for anomaly analyzing, exporting anomaly metrics

`nile-grafana` - tools for use *grafana as a code* approach and contain all grafana configuration in code

## Dependency

#### Gradle (.kts)

```kotlin
val nileVersion = "0.0.1"

implementation(group = "tech.volkov.nile", name = "nile-core", version = nileVersion)
implementation(group = "tech.volkov.nile", name = "nile-scheduler", version = nileVersion)
implementation(group = "tech.volkov.nile", name = "nile-anomaly", version = nileVersion)
implementation(group = "tech.volkov.nile", name = "nile-grafana", version = nileVersion)
```

#### Maven

```xml
<dependencies>
  <dependency>
    <groupId>tech.volkov.nile</groupId>
    <artifactId>nile-core</artifactId>
    <version>0.0.1</version>
  </dependency>
  <dependency>
    <groupId>tech.volkov.nile</groupId>
    <artifactId>nile-scheduler</artifactId>
    <version>0.0.1</version>
  </dependency>
  <dependency>
    <groupId>tech.volkov.nile</groupId>
    <artifactId>nile-anomaly</artifactId>
    <version>0.0.1</version>
  </dependency>
  <dependency>
    <groupId>tech.volkov.nile</groupId>
    <artifactId>nile-grafana</artifactId>
    <version>0.0.1</version>
  </dependency>
</dependencies>
```
