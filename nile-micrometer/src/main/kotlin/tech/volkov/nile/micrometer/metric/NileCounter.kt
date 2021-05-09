package tech.volkov.nile.micrometer.metric

import io.micrometer.core.instrument.Counter
import io.micrometer.core.instrument.Metrics
import io.micrometer.core.instrument.Tag
import tech.volkov.nile.micrometer.global.STATUS_FAILURE
import tech.volkov.nile.micrometer.global.STATUS_SUCCESS
import tech.volkov.nile.micrometer.global.STATUS_TAG_NAME
import tech.volkov.nile.micrometer.util.runBlockAndCatchError

fun <T> nileCounter(
    name: String,
    description: String = "",
    tags: Map<String, String> = emptyMap(),
    amount: Double = 1.0,
    block: () -> T
) = runBlockAndCatchError(block) { _: Double?, isSuccess: Boolean ->
    Counter
        .builder(name)
        .description(description)
        .tags(tags.map { tag -> Tag.of(tag.key, tag.value) })
        .tag(STATUS_TAG_NAME, if (isSuccess) STATUS_SUCCESS else STATUS_FAILURE)
        .register(Metrics.globalRegistry)
        .increment(amount)
}
