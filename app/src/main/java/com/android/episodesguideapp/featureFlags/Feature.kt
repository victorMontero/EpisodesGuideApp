package com.android.episodesguideapp.featureFlags

sealed class Feature(
    val key: String,
    val title: String,
    val defaultValue: Boolean,
) {
    object NewFeatureOne : Feature("use-flutter-module", "use-flutter-module", false)
}