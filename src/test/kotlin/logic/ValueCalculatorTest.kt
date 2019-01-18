package logic

import model.CardSet
import model.Cards
import model.GameType
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class ValueCalculatorTest {

    @Test
    fun `wenz - correct beating order`() {

        fun testGameType(gameType: GameType) {
            println("TEST: $gameType")
            // upper color beats lower color
            Cards.Color.values().forEach { colorUnderTest ->
                val colorCards = gameType.nonTrumps.filter {
                    it.color == colorUnderTest
                }
                colorCards.forEachIndexed { index, lowerCard ->
                    colorCards.filterIndexed { otherIndex, _ -> otherIndex < index }.forEach { upperCard ->
                        assertTrue(upperCard.beats(lowerCard, gameType))
                    }

                }
            }
            // trumps beat all colors
            gameType.nonTrumps.forEach { colorCard ->
                gameType.trumps.forEach { trump ->
                    assertTrue(trump.beats(colorCard, gameType))
                }
            }
            // upper trump beats lower trump
            // TODO!
            /*
            val sortedTrumps = gameType.trumps.sortedBy { it.color.asValueTrumpRank + (2 * it.value.defaultRank) }
            sortedTrumps.forEachIndexed { lowerCardIndex, lowerCard ->
                sortedTrumps.filterIndexed { index, _ -> index > lowerCardIndex }.forEach { upperCard ->
                    println("\t$upperCard vs. $lowerCard")
                    assertTrue(upperCard.beats(lowerCard, gameType))
                }
            }
            */
            // colors do not beat each other
            gameType.nonTrumps.forEach { firstCard ->
                gameType.nonTrumps.filter { it.color != firstCard.color }.forEach { otherCard ->
                    assertFalse(firstCard.beats(otherCard, gameType))
                    assertFalse(otherCard.beats(firstCard, gameType))
                }
            }
        }

        GameType::class.sealedSubclasses.forEach { firstLevelGameType ->
            when (firstLevelGameType) {
                GameType.SauSpiel::class -> {
                    testGameType(GameType.SauSpiel(Cards.EICHEL_AS, CardSet.Long))
                }
                GameType.Solo::class -> GameType.Solo::class.sealedSubclasses.forEach { soloGameType ->

                    when (soloGameType) {
                        GameType.Solo.Wenz::class -> testGameType(GameType.Solo.Wenz(CardSet.Long))
                        GameType.Solo.Geier::class -> testGameType(GameType.Solo.Geier(CardSet.Long))
                        else -> {
                            Cards.Color.values().forEach { color ->
                                when (soloGameType) {
                                    GameType.Solo.FarbSolo::class -> testGameType(GameType.Solo.FarbSolo(color, CardSet.Long))
                                    GameType.Solo.FarbWenz::class -> testGameType(GameType.Solo.FarbWenz(color, CardSet.Long))
                                    GameType.Solo.FarbGeier::class -> testGameType(GameType.Solo.FarbGeier(color, CardSet.Long))
                                }
                            }
                        }
                    }
                }
            }
        }


    }

    @Test
    fun `geier colors beat each other and get beaten by ober`() {
        val cardSet = CardSet.Long
        val gameType = GameType.Solo.Geier(cardSet)

        Cards.Color.values().forEach { color ->
            val colorCards = cardSet.filter {
                !gameType.trumps.contains(it) && it.color == color
            }
            colorCards.forEachIndexed { index, lowerCard ->
                assertTrue(Cards.SCHELLEN_OBER.beats(lowerCard, gameType))
                assertTrue(Cards.HERZ_OBER.beats(lowerCard, gameType))
                assertTrue(Cards.GRAS_OBER.beats(lowerCard, gameType))
                assertTrue(Cards.EICHEL_OBER.beats(lowerCard, gameType))
                colorCards.filterIndexed { otherIndex, _ -> otherIndex < index }.forEach { upperCard ->
                    assertTrue(upperCard.beats(lowerCard, gameType))
                }
            }
        }
    }

}