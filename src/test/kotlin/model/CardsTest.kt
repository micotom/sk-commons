package model

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test


internal class CardsTest {

    // TODO check that ordered descending

    @Test
    fun `correct number of cards`() {
        val expectedCount = 32
        assertEquals(expectedCount, Cards.values().toSet().size)
    }

    @Test
    fun `correct number of colors`() {
        val expectedColorCount = 8

        Cards.Color.values().map { c ->
            Cards.values().filter { it.color == c }
        }.map {
            it.size
        }.forEach {
            assertEquals(expectedColorCount, it)
        }
    }

    @Test
    fun `correct number of values`() {
        val expectedValueCount = 4

        Cards.Value.values().map { v ->
            Cards.values().filter { it.value == v }
        }.map {
            it.size
        }.forEach {
            assertEquals(expectedValueCount, it)
        }
    }

}