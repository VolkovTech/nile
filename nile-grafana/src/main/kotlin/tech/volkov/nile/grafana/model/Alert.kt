package tech.volkov.nile.grafana.model

data class Alert(
    var above: Double = 0.0,
    var offset: String = "15s",
    var evaluateFor: String = "15s",
    var evaluateEvery: String = "15s",
    var name: String = ""
)
