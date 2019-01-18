package model

import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.api.Test


internal class CardSetTest {

    @Test
    fun `long set is complete`() {
        assertArrayEquals(Cards.values(), CardSet.Long.toTypedArray())
    }

    @Test
    fun `short set is complete`() {
        val expected = Cards.values().filter { it.value != Cards.Value.SIEBENER && it.value != Cards.Value.ACHTER }
            .toTypedArray()
        assertArrayEquals(expected, CardSet.Short.toTypedArray())
    }

}