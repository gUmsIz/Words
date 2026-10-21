package com.gumsiz.shared.data.settings

import android.content.Context
import android.content.SharedPreferences

class AndroidSettingsStorage(context: Context) : SettingsStorage {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("words_prefs", Context.MODE_PRIVATE)

    override fun hasClickedCrossPromotion(): Boolean =
        prefs.getBoolean(KEY_CROSS_PROMOTION, false)

    override fun setCrossPromotionClicked(clicked: Boolean) {
        prefs.edit().putBoolean(KEY_CROSS_PROMOTION, clicked).apply()
    }

    companion object {
        private const val KEY_CROSS_PROMOTION = "has_clicked_derdiedas_link"
    }
}
