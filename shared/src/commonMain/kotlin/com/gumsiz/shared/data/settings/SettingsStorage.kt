package com.gumsiz.shared.data.settings

interface SettingsStorage {
    fun hasClickedCrossPromotion(): Boolean
    fun setCrossPromotionClicked(clicked: Boolean)
}
