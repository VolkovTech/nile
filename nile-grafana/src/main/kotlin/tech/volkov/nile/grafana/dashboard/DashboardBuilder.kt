package tech.volkov.nile.grafana.dashboard

import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.web.client.postForEntity
import tech.volkov.nile.grafana.context.DashboardContext
import tech.volkov.nile.grafana.model.Panel.PanelType
import tech.volkov.nile.grafana.template.GrafanaTemplate
import tech.volkov.nile.grafana.template.GrafanaTemplate.Companion.alertTemplate
import tech.volkov.nile.grafana.template.GrafanaTemplate.Companion.graphTemplate
import tech.volkov.nile.grafana.template.GrafanaTemplate.Companion.statTemplate
import tech.volkov.nile.grafana.template.GrafanaTemplate.Companion.variableTemplate
import kotlin.random.Random

private val restTemplate = RestTemplateBuilder()
    .defaultHeader(
        "Authorization",
        "Bearer eyJrIjoieHZDVldpODE2U1BTMXJ5U1hGOWtQaUY3ZG5lV2NzT20iLCJuIjoibWFpbiIsImlkIjoxfQ=="
    )
    .defaultHeader("Content-Type", "application/json")
    .build()

fun grafana(block: DashboardContext.() -> Unit = {}) = with(DashboardContext().apply(block)) {
    val currentPanels = panels.joinToString(",") {
        val alertInTemplate = if (it.alert != null) {
            with(it.alert!!) {
                alertTemplate.format(above, offset, evaluateFor, evaluateEvery, name)
            }
        } else {
            ""
        }

        when (it.type) {
            PanelType.GRAPH -> {
                graphTemplate.format(
                    it.bars.toString(),
                    it.unit.value,
                    it.grid.x,
                    it.grid.y,
                    Random.nextInt(1000000),
                    it.lines.toString(),
                    it.expression,
                    it.minStep,
                    it.legendFormat,
                    it.title,
                    alertInTemplate
                )
            }
            PanelType.STAT -> {
                statTemplate.format(
                    it.unit.value,
                    it.grid.x,
                    it.grid.y,
                    Random.nextInt(1000000),
                    it.expression,
                    it.legendFormat,
                    it.title,
                    alertInTemplate
                )
            }
        }
    }

    val variables = variables.joinToString(",") {
        variableTemplate.format(it.query, it.name, it.query)
    }

    val dashboard = GrafanaTemplate.dashboardTemplate.format(dashboard.title, currentPanels, variables)

    runCatching { restTemplate.delete("http://localhost:3000/api/dashboards/uid/RQGKsU3Gz") }
    restTemplate.postForEntity<String>("http://localhost:3000/api/dashboards/db", dashboard)
}
