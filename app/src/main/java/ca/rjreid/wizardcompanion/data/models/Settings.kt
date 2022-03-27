package ca.rjreid.wizardcompanion.data.models

enum class ThemeSetting(val value: Int) {
    LIGHT(1),
    DARK(2),
    SYSTEM(3);

    companion object {
        private val map = values().associateBy(ThemeSetting::value)
        fun fromInt(type: Int) = map[type]
    }
}