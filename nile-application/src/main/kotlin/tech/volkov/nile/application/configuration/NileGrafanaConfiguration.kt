package tech.volkov.nile.application.configuration

import org.springframework.context.annotation.Configuration
import tech.volkov.nile.grafana.NileGrafanaConfiguration
import tech.volkov.nile.grafana.dashboard.grafana
import tech.volkov.nile.grafana.model.Alert
import tech.volkov.nile.grafana.model.Panel.Grid
import tech.volkov.nile.grafana.model.Panel.PanelType
import tech.volkov.nile.grafana.model.Panel.Unit

@Configuration
class NileGrafanaConfiguration {

    @NileGrafanaConfiguration
    fun nileGrafana() = grafana {
        dashboard {
            title = "Whether Monitoring Dashboard"
        }

        panel {
            title = "Temperature"
            expression = "sum(temperature{city=~\\\"\$city\\\"}) by (city)"
            legendFormat = "{{city}}"
            type = PanelType.STAT
            unit = Unit.CELSIUS
            grid = Grid(x = 0, y = 0)
        }

        panel {
            title = "Pressure"
            expression = "sum(pressure{city=~\\\"\$city\\\"}) by (city)"
            legendFormat = "{{city}}"
            type = PanelType.GRAPH
            unit = Unit.PRESSURE_PA
            grid = Grid(x = 12, y = 0)
        }

        panel {
            title = "Humidity"
            expression = "sum(humidity{city=~\\\"\$city\\\"}) by (city)"
            legendFormat = "{{city}}"
            type = PanelType.GRAPH
            unit = Unit.PERCENT
            grid = Grid(x = 0, y = 8)
        }

        panel {
            title = "Wind speed"
            expression = "sum(wind_speed{city=~\\\"\$city\\\"}) by (city)"
            legendFormat = "{{city}}"
            type = PanelType.GRAPH
            unit = Unit.METERS_PER_SECOND
            grid = Grid(x = 12, y = 8)
        }

        panel {
            title = "sin"
            expression = "sum(sin)"
            legendFormat = "sin"
            type = PanelType.GRAPH
            unit = Unit.SHORT
            grid = Grid(x = 0, y = 16)
            minStep = "15s"
            bars = false
            lines = true

            alert = Alert(
                name = "Sin Alert",
                above = 0.75,
                offset = "10s",
                evaluateFor = "10s",
                evaluateEvery = "10s"
            )
        }

        variable {
            name = "city"
            query = "label_values(temperature, city)"
        }
    }
}
