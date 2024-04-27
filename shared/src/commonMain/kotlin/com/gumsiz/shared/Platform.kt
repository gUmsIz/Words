package com.gumsiz.shared

import io.ktor.client.engine.HttpClientEngine

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform

expect fun getEngine(): HttpClientEngine