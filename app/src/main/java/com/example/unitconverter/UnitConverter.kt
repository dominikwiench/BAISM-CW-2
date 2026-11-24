package com.example.unitconverter

enum class Conversion(
    val label: String,
    val sourceUnit: String,
    val targetUnit: String
) {
    METERS_TO_FEET("Metry → stopy", "m", "ft"),
    FEET_TO_METERS("Stopy → metry", "ft", "m"),
    KILOGRAMS_TO_POUNDS("Kilogramy → funty", "kg", "lb"),
    POUNDS_TO_KILOGRAMS("Funty → kilogramy", "lb", "kg")
}

object UnitConverter {
    private const val METERS_PER_FOOT = 0.3048
    private const val KILOGRAMS_PER_POUND = 0.45359237

    fun convert(value: Double, conversion: Conversion): Double {
        require(value.isFinite()) {
            "Podaj skończoną wartość liczbową"
        }
        require(value >= 0.0) {
            "Wartość nie może być ujemna"
        }

        val result = when (conversion) {
            Conversion.METERS_TO_FEET ->
                value / METERS_PER_FOOT

            Conversion.FEET_TO_METERS ->
                value * METERS_PER_FOOT

            Conversion.KILOGRAMS_TO_POUNDS ->
                value / KILOGRAMS_PER_POUND

            Conversion.POUNDS_TO_KILOGRAMS ->
                value * KILOGRAMS_PER_POUND
        }

        require(result.isFinite()) {
            "Wartość jest zbyt duża"
        }

        return result
    }
}