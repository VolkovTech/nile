package tech.volkov.nile.micrometer.util

internal fun <T> runBlockAndCatchError(
    businessBlock: () -> T,
    metricBlock: (metricValue: Double?, isSuccess: Boolean) -> Unit
) = runCatching(businessBlock)
    .also { metricBlock(it.getOrNull() as? Double, it.isSuccess) }
    .getOrThrow()
