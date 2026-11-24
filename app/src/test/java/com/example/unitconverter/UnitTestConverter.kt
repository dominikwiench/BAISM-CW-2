package com.example.unitconverter

import org.junit.Assert.assertEquals
import org.junit.Test

class UnitConverterTest {
    private val tolerance = 0.000000001

    @Test
    fun metersToFeet_returnsCorrectResult() {
        val result = UnitConverter.convert(
            1.0,
            Conversion.METERS_TO_FEET
        )
        assertEquals(3.280839895013123, result, tolerance)
    }

    @Test
    fun feetToMeters_returnsCorrectResult() {
        val result = UnitConverter.convert(
            10.0,
            Conversion.FEET_TO_METERS
        )
        assertEquals(3.048, result, tolerance)
    }

    @Test
    fun kilogramsToPounds_returnsCorrectResult() {
        val result = UnitConverter.convert(
            1.0,
            Conversion.KILOGRAMS_TO_POUNDS
        )
        assertEquals(2.2046226218487757, result, tolerance)
    }

    @Test
    fun poundsToKilograms_returnsCorrectResult() {
        val result = UnitConverter.convert(
            10.0,
            Conversion.POUNDS_TO_KILOGRAMS
        )
        assertEquals(4.5359237, result, tolerance)
    }

    @Test
    fun zero_returnsZeroForEveryConversion() {
        Conversion.entries.forEach { conversion ->
            val result = UnitConverter.convert(0.0, conversion)
            assertEquals(0.0, result, tolerance)
        }
    }

    @Test(expected = IllegalArgumentException::class)
    fun negativeValue_isRejected() {
        UnitConverter.convert(
            -1.0,
            Conversion.METERS_TO_FEET
        )
    }

}