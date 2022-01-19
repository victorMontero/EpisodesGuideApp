package com.android.episodesguideapp.featureFlags

interface FeatureProvider {
    fun isFeatureEnabled(feature: Feature): Boolean
    fun hasFeature(feature: Feature): Boolean
    fun tearDown()
}