package tech.volkov.nile.grafana.template

class GrafanaTemplate private constructor() {

    companion object {
        const val dashboardTemplate = """{
            "dashboard": {
                "id": null,
                "uid": "RQGKsU3Gz",
                "title": "%s",
                "tags": [
                    "templated"
                ],
                "timezone": "browser",
                "schemaVersion": 27,
                "version": 0,
                "editable": true,
                "gnetId": null,
                "graphTooltip": 0,
                "iteration": 1622077473023,
                "links": [],
                "panels": [%s],
                "style": "dark",
                "templating": {
                  "list": [%s]
                },
                "time": {
                    "from": "now-5m",
                    "to": "now"
                },
                "timepicker": {}
                },
                "folderId": 0,
                "message": "Created a dashboard",
                "overwrite": false
            }
        """
        val graphTemplate = """
            {
              "bars": %s,
              "dashLength": 10,
              "dashes": false,
              "fieldConfig": {
                "defaults": {
                  "unit": "%s"
                }
              },
              "fill": 1,
              "gridPos": {
                "h": 8,
                "w": 12,
                "x": %s,
                "y": %s
              },
              "hiddenSeries": false,
              "id": %s,
              "legend": {
                "alignAsTable": true,
                "avg": false,
                "current": true,
                "max": false,
                "min": false,
                "rightSide": true,
                "show": true,
                "sort": "current",
                "sortDesc": true,
                "total": false,
                "values": true
              },
              "lines": %s,
              "linewidth": 1,
              "nullPointMode": "null",
              "options": {
                "alertThreshold": true
              },
              "percentage": false,
              "pluginVersion": "7.5.5",
              "pointradius": 2,
              "points": false,
              "renderer": "flot",
              "seriesOverrides": [],
              "spaceLength": 10,
              "stack": false,
              "steppedLine": false,
              "targets": [
                {
                  "exemplar": true,
                  "expr": "%s",
                  "interval": "%s",
                  "legendFormat": "%s",
                  "refId": "A"
                }
              ],
              "thresholds": [],
              "timeFrom": null,
              "timeRegions": [],
              "timeShift": null,
              "title": "%s",
              "tooltip": {
                "shared": true,
                "sort": 0,
                "value_type": "individual"
              },
              "type": "graph",
              "xaxis": {
                "buckets": null,
                "mode": "time",
                "name": null,
                "show": true,
                "values": []
              }
              %s
            }
        """.trimIndent()
        val statTemplate = """
            {
              "fieldConfig": {
                "defaults": {
                  "unit": "%s"
                }
              },
              "gridPos": {
                "h": 8,
                "w": 12,
                "x": %s,
                "y": %s
              },
              "id": %s,
              "pluginVersion": "7.5.5",
              "targets": [
                {
                  "exemplar": true,
                  "expr": "%s",
                  "interval": "",
                  "legendFormat": "%s",
                  "refId": "A"
                }
              ],
              "title": "%s",
              "type": "stat"
              %s
            }
        """.trimIndent()
        val variableTemplate = """
            {
                "allValue": ".*",
                "datasource": null,
                "definition": "%s",
                "description": null,
                "error": null,
                "hide": 0,
                "includeAll": true,
                "label": null,
                "multi": true,
                "name": "%s",
                "query": {
                    "query": "%s",
                    "refId": "StandardVariableQuery"
                },
                "refresh": 1,
                "regex": "",
                "skipUrlSync": false,
                "sort": 0,
                "tagValuesQuery": "",
                "tags": [],
                "tagsQuery": "",
                "type": "query",
                "useTags": false
            }
        """.trimIndent()
        val alertTemplate = """,
            "alert": {
              "alertRuleTags": {},
              "conditions": [
                {
                  "evaluator": {
                    "params": [
                      %s
                    ],
                    "type": "gt"
                  },
                  "operator": {
                    "type": "and"
                  },
                  "query": {
                    "params": [
                      "A",
                      "%s",
                      "now"
                    ]
                  },
                  "reducer": {
                    "params": [],
                    "type": "avg"
                  },
                  "type": "query"
                }
              ],
              "executionErrorState": "alerting",
              "for": "%s",
              "frequency": "%s",
              "handler": 1,
              "name": "%s",
              "noDataState": "ok",
              "notifications": [
                {
                  "uid": "pLAHDwqMk"
                }
              ]
            }
        """.trimIndent()
    }
}
