# Nile Project

Nile project - is a monitoring system, set of libraries, containing useful API for building, export, analyzing and visualizing business metrics of your Spring Java/Kotlin application. 

Nile project contains of 3 modules:

[`nile-micrometer`](#nile-micrometer)
  - building different types of business metrics (counter, timer, gauge, distribution summary)
  - building database metrics via executing select queries
  - collecting metrics by schedule

[`nile-anomaly`](#nile-anomaly)
  - analyzing business time series on anomalies
  - exporting anomalies metrics

[`nile-grafana`](#nile-grafana)
  - Kotlin DSL for building grafana dashboards (*grafana as a code*)
  - Grafana Dashboard management via Grafana API

Example of usage for all modules can be found in `nile-application` module.

## Table of contents

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

Increases micrometer counter each time when function calls  

*Function*

```kotlin
nileCounter(
    name = "dog_facts_counter",
    description = "Counts how many request were executed to dog facts API",
    tags = mapOf("tagName" to "tagValue"),
    isSuccess = isSuccess,
    amount = { 10.0 }
)
```

*Annotation*

```kotlin
@NileCounter(
    name = "dog_facts_counter",
    description = "Counts how many request were executed to dog facts API",
    tags = ["tagName", "tagValue"],
    amount = 10.0
)
fun getDogFacts() {
    // getting dogs facts
}
```

*Prometheus metrics*

```

```

<a name="nile-micrometer-timer"></a>
### Timer

```kotlin
val dogFactsResponse = nileTimer(
    name = "dog_facts_timer",
    description = "Response time for dog facts API",
    tags = mapOf("tagName" to "tagValue"),
    percentiles = listOf(0.5, 0.75, 0.9, 0.95, 0.99)
) {    
    getDogFacts(number)
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

```kotlin

```

*Prometheus metrics*

```

```

<a name="nile-micrometer-distribution-summary"></a>
### Distribution Summary

```kotlin

```

*Prometheus metrics*

```

```

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

<a name="nile-anomaly"></a>
## Nile Anomaly


<a name="nile-grafana"></a>
## Nile Grafana
