package tech.volkov.nile.micrometer.global

internal fun getTags(counterTags: Array<String>): Map<String, String> = if (counterTags.size % 2 == 0) {
    mutableMapOf<String, String>().also {
        counterTags.forEachIndexed { index, tag ->
            if (index % 2 == 0) {
                it[tag] = counterTags[index + 1]
            }
        }
    }
} else {
    emptyMap()
}
