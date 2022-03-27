package ca.rjreid.wizardcompanion.data.models

import ca.rjreid.wizardcompanion.R

enum class ThemeSetting(val value: Int, val stringResId: Int) {
    LIGHT(1, R.string.enum_theme_light),
    DARK(2, R.string.enum_theme_dark),
    SYSTEM(3, R.string.enum_theme_system);

    companion object {
        private val map = values().associateBy(ThemeSetting::value)
        fun fromInt(type: Int) = map[type]
    }
}