package com.gumsiz.shared

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform