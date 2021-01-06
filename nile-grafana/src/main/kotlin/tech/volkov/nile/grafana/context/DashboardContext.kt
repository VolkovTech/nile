package tech.volkov.nile.grafana.context

import tech.volkov.nile.grafana.model.Dashboard
import tech.volkov.nile.grafana.model.Panel
import tech.volkov.nile.grafana.model.Variable

class DashboardContext {
    lateinit var dashboard: Dashboard
        private set

    var panels: MutableList<Panel> = mutableListOf()
        private set

    var variables: MutableList<Variable> = mutableListOf()
        private set

    fun dashboard(init: Dashboard.() -> Unit) {
        dashboard = Dashboard().apply(init)
    }

    fun panel(init: Panel.() -> Unit) {
        panels.add(Panel().apply(init))
    }

    fun variable(init: Variable.() -> Unit) {
        variables.add(Variable().apply(init))
    }
}
