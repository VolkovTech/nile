![Maintaner](https://img.shields.io/badge/maintainer-VolkovTech-blue)
[![GitHub license](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg?style=flat)](https://www.apache.org/licenses/LICENSE-2.0)
[![GitHub tag](https://img.shields.io/github/tag/Naereen/StrapDown.js.svg)](https://github.com/VolkovTech/nile/tags)

# Nile Project

Nile project - is an open-source project, monitoring system, analysis of anomalies and timely warning for modern IT
companies. Light-weight kotlin library, using coroutines.

`nile-micrometer` - basic functions and utils for building `Gauge Counter` & `Gauge Timer` metrics

`nile-anomaly` - tools for anomaly analyzing, exporting anomaly metrics

`nile-grafana` - implementation of *grafana as a code* approach containing all grafana configuration in code

`nile-application` - contains an example of using nile project

Table of contents

- [Dependency](#Dependency)
- [Nile micrometer](#nile-micrometer)
    - [Counter](#nile-micrometer-counter)
    - [Timer](#nile-micrometer-timer)
    - [Gauge](#nile-micrometer-gauge)
    - [Distribution Summary](#nile-micrometer-distribution-summary)
    - [Scheduled metric](#nile-micrometer-scheduled-metric)
- [Nile anomaly](#nile-anomaly)
- [Nile grafana](#nile-grafana)

## Dependency

#### Gradle (.kts)

```kotlin
val nileVersion = "0.0.1"

implementation(group = "tech.volkov.nile", name = "nile-micrometer", version = nileVersion)
implementation(group = "tech.volkov.nile", name = "nile-anomaly", version = nileVersion)
implementation(group = "tech.volkov.nile", name = "nile-grafana", version = nileVersion)
```

#### Maven

```xml

<dependencies>
  <dependency>
    <groupId>tech.volkov.nile</groupId>
    <artifactId>nile-micrometer</artifactId>
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

<a name="nile-micrometer"></a>
## Nile Micrometer

<a name="nile-micrometer-counter"></a>
### Counter


<a name="nile-micrometer-timer"></a>
### Timer

```kotlin
fun getCatFact(): CatFact? {
    withTimer(
        name = "cat_fact",
        description = "Time to execute call to cat fact API"
    ) {
        webClient
            .get()
            .uri {
                UriComponentsBuilder.fromHttpUrl("https://catfact.ninja")
                    .pathSegment("fact")
                    .build()
                    .toUri()
            }
            .exchangeToMono { it.toEntity(CatFact::class.java) }
            .block()
            .body
    }
}
```

*Prometheus metrics*

```
# HELP cat_fact_seconds Time to execute call to cat fact API
# TYPE cat_fact_seconds summary
cat_fact_seconds_count{application="nile-application",status="OK",} 5.0
cat_fact_seconds_sum{application="nile-application",status="OK",} 17.0398064
# HELP cat_fact_seconds_max Time to execute call to cat fact API
# TYPE cat_fact_seconds_max gauge
cat_fact_seconds_max{application="nile-application",status="OK",} 7.1959512
```

<a name="nile-micrometer-gauge"></a>
### Gauge

<a name="nile-micrometer-distribution-summary"></a>
### Distribution Summary

<a name="nile-micrometer-scheduled-metric"></a>
### Scheduled metric

#### Nile Scheduler Spring Configuration
```kotlin
@Configuration
class NileConfiguration {

    @Bean
    fun nileScheduler() = NileScheduler.builder()
        .corePoolSize(5)
        .maximumPoolSize(10)
        .keepAliveTime(5000)
        .queueCapacity(10)
        .defaultScrapeInterval(Duration.ofSeconds(30))
        .build()
}
```

#### Scheduled metric

```kotlin
fun scheduleMetric(): MetricDto {
    nileScheduler.scheduleMetric(
        name = "random_number",
        description = "Returns random number between 0 to 9",
        scrapeInterval = Duration.ofSeconds(5)
    ) {
        Random.nextDouble(10.0)
    }
}
```

*Prometheus metrics (the value updates automatically every 5 seconds)*

```
# HELP random_number Returns random number between 0 to 9
# TYPE random_number gauge
random_number{application="nile-application",} 5.295077022881081
```
