package com.android.episodesguideapp.featureFlags

import android.app.Application
import android.util.Log
import com.launchdarkly.sdk.LDUser
import com.launchdarkly.sdk.android.LDClient
import com.launchdarkly.sdk.android.LDConfig

class LaunchDarklyFeatureProvider(application: Application) : FeatureProvider {
    private val client: LDClient

    init {
        val apiKey = TEST_MOBILE_KEY

        val ldConfig = LDConfig.Builder()
            .mobileKey(apiKey)
            .build()

        val user = LDUser.Builder("").build()

        client = LDClient.init(application, ldConfig, user, 5)
    }

    override fun isFeatureEnabled(feature: Feature): Boolean {
        if (!hasFeature(feature)) {
            Log.d(TAG,
                "Feature \"${feature.title}\" is not setup in the Launch Darkly console, using default value.")
        }
        return client.boolVariation(feature.key, feature.defaultValue)
    }

    override fun hasFeature(feature: Feature): Boolean = client.allFlags().containsKey(feature.key)

    override fun tearDown() = client.flush()

    private companion object {
        private val TAG = LaunchDarklyFeatureProvider::class.java.simpleName
        private const val TEST_MOBILE_KEY = "mob-389e27db-3ac2-4bc6-99f0-57a5a79e2938"
    }
}
