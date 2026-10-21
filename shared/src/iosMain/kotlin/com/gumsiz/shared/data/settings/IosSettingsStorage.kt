package com.gumsiz.shared.data.settings

import platform.Foundation.NSUserDefaults

class IosSettingsStorage : SettingsStorage {
    private val defaults = NSUserDefaults.standardUserDefaults

    override fun hasClickedCrossPromotion(): Boolean =
        defaults.boolForKey(KEY_CROSS_PROMOTION)

    override fun setCrossPromotionClicked(clicked: Boolean) {
        defaults.setBool(clicked, forKey = KEY_CROSS_PROMOTION)
    }

    companion object {
        private const val KEY_CROSS_PROMOTION = "has_clicked_derdiedas_link"
    }
}
