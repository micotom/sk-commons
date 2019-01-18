package model

import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows


internal class GameTypeTest {

    @Test
    fun `farb solo contains correct trumps`() {
        Cards.Color.values().forEach { color ->

            val soloShort = GameType.Solo.FarbSolo(color, CardSet.Short)
            val expectedShortTrumps = CardSet.Short.filter {
                it.color == color || it.value == Cards.Value.OBER || it.value == Cards.Value.UNTER
            }
            assertArrayEquals(expectedShortTrumps.toTypedArray(), soloShort.trumps.toTypedArray())

            val soloLong = GameType.Solo.FarbSolo(color, CardSet.Long)
            val expectedLongTrumps = CardSet.Long.filter {
                it.color == color || it.value == Cards.Value.OBER || it.value == Cards.Value.UNTER
            }
            assertArrayEquals(expectedLongTrumps.toTypedArray(), soloLong.trumps.toTypedArray())

        }
    }

    @Test
    fun `wenz contains correct trumps`() {
        val wenzShort = GameType.Solo.Wenz(CardSet.Short)
        val expectedShortTrumps = CardSet.Short.filter {
            it.value == Cards.Value.UNTER
        }
        assertArrayEquals(expectedShortTrumps.toTypedArray(), wenzShort.trumps.toTypedArray())

        val wenzLong = GameType.Solo.Wenz(CardSet.Long)
        val expectedLongTrumps = CardSet.Long.filter {
            it.value == Cards.Value.UNTER
        }
        assertArrayEquals(expectedLongTrumps.toTypedArray(), wenzLong.trumps.toTypedArray())
    }

    @Test
    fun `farb wenz contains correct trumps`() {
        Cards.Color.values().forEach { color ->

            val soloShort = GameType.Solo.FarbWenz(color, CardSet.Short)
            val expectedShortTrumps = CardSet.Short.filter {
                it.color == color || it.value == Cards.Value.UNTER
            }
            assertArrayEquals(expectedShortTrumps.toTypedArray(), soloShort.trumps.toTypedArray())

            val soloLong = GameType.Solo.FarbWenz(color, CardSet.Long)
            val expectedLongTrumps = CardSet.Long.filter {
                it.color == color || it.value == Cards.Value.UNTER
            }
            assertArrayEquals(expectedLongTrumps.toTypedArray(), soloLong.trumps.toTypedArray())

        }
    }

    @Test
    fun `geier contains correct trumps`() {
        val geierShort = GameType.Solo.Geier(CardSet.Short)
        val expectedShortTrumps = CardSet.Short.filter {
            it.value == Cards.Value.OBER
        }
        assertArrayEquals(expectedShortTrumps.toTypedArray(), geierShort.trumps.toTypedArray())

        val geierLong = GameType.Solo.Geier(CardSet.Long)
        val expectedLongTrumps = CardSet.Long.filter {
            it.value == Cards.Value.OBER
        }
        assertArrayEquals(expectedLongTrumps.toTypedArray(), geierLong.trumps.toTypedArray())
    }

    @Test
    fun `farb geier contains correct trumps`() {
        Cards.Color.values().forEach { color ->

            val geierShort = GameType.Solo.FarbGeier(color, CardSet.Short)
            val expectedShortTrumps = CardSet.Short.filter {
                it.color == color || it.value == Cards.Value.OBER
            }
            assertArrayEquals(expectedShortTrumps.toTypedArray(), geierShort.trumps.toTypedArray())

            val geierLong = GameType.Solo.FarbGeier(color, CardSet.Long)
            val expectedLongTrumps = CardSet.Long.filter {
                it.color == color || it.value == Cards.Value.OBER
            }
            assertArrayEquals(expectedLongTrumps.toTypedArray(), geierLong.trumps.toTypedArray())

        }
    }

    @Test
    fun `sauspiel contains correct trumps`() {
        val sauSpielShort = GameType.SauSpiel(Cards.EICHEL_AS, CardSet.Short)
        val expectedShortTrumps = CardSet.Short.filter {
            it.color == Cards.Color.HERZ || it.value == Cards.Value.OBER || it.value == Cards.Value.UNTER
        }
        assertArrayEquals(expectedShortTrumps.toTypedArray(), sauSpielShort.trumps.toTypedArray())

        val sauSpielLong = GameType.SauSpiel(Cards.EICHEL_AS, CardSet.Long)
        val expectedLongTrumps = CardSet.Long.filter {
            it.color == Cards.Color.HERZ || it.value == Cards.Value.OBER || it.value == Cards.Value.UNTER
        }
        assertArrayEquals(expectedLongTrumps.toTypedArray(), sauSpielLong.trumps.toTypedArray())
    }

    @Test
    fun `sauspiel throws exception with non-sau as constructor param`() {

        val allNonSauCards = CardSet.Long.filter { it.value != Cards.Value.AS }
        allNonSauCards.forEach { c ->
            assertThrows<IllegalArgumentException> {
                GameType.SauSpiel(c, CardSet.Long)
            }
        }

    }

}