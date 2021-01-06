package tech.volkov.nile.grafana.model

data class Panel(
    var title: String = "",
    var expression: String = "",
    var minStep: String = "1m",
    var legendFormat: String = "",
    var type: PanelType = PanelType.STAT,
    var unit: Unit = Unit.PERCENT,
    var grid: Grid = Grid(),
    var bars: Boolean = true,
    var lines: Boolean = false,
    var alert: Alert? = null
) {
    data class Grid(
        var x: Int = 0,
        var y: Int = 0
    )

    enum class PanelType(value: String) {
        STAT("stat"),
        GRAPH("graph")
    }

    enum class Unit(val value: String) {
        CELSIUS("celsius"),
        PRESSURE_PA("pressurepa"),
        PERCENT("percent"),
        METERS_PER_SECOND("velocityms"),
        SHORT("short")
    }
}
