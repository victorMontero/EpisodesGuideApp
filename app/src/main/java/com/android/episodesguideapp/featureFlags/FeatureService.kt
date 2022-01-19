package com.android.episodesguideapp.featureFlags

import android.app.Application

class FeatureService(application: Application) : FeatureProvider {
    private val provider = LaunchDarklyFeatureProvider(application)

    override fun isFeatureEnabled(feature: Feature): Boolean = provider.isFeatureEnabled(feature)

    override fun hasFeature(feature: Feature): Boolean = provider.hasFeature(feature)

    override fun tearDown() = provider.tearDown()
}