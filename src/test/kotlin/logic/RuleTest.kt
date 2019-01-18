package logic

import model.CardSet
import model.Cards
import model.GameType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class RuleTest {

    @Test
    fun `at least one top rule applies for every combination`() {

        (0..1000000).forEach { _ ->
            var cardsToChose = CardSet.Long.toList()
            val cardsOnHand = mutableSetOf<Cards>()
            while (cardsOnHand.size < 8) {
                val chosenCard = cardsToChose[(0..(cardsToChose.size - 1)).random()]
                cardsOnHand.add(chosenCard)
                cardsToChose -= chosenCard
            }

            var firstCard: Cards? = null
            while (firstCard == null) {
                firstCard = CardSet.Long.toList()[(0..(CardSet.Long.size - 1)).random()]
                if (cardsOnHand.contains(firstCard)) {
                    firstCard = null
                }
            }

            val gameType = when ((0..5).random()) {
                0 -> {
                    val color = Cards.Color.values()[(0..(Cards.Color.values().size - 1)).random()]
                    GameType.Solo.FarbSolo(color, CardSet.Long)
                }
                1 -> GameType.Solo.Wenz(CardSet.Long)
                2 -> {
                    val color = Cards.Color.values()[(0..(Cards.Color.values().size - 1)).random()]
                    GameType.Solo.FarbWenz(color, CardSet.Long)
                }
                3 -> GameType.Solo.Geier(CardSet.Long)
                4 -> {
                    val color = Cards.Color.values()[(0..(Cards.Color.values().size - 1)).random()]
                    GameType.Solo.FarbGeier(color, CardSet.Long)
                }
                5 -> GameType.SauSpiel(Cards.SCHELLEN_AS, CardSet.Long)
                else -> throw IllegalArgumentException()
            }

            val giveColorRule = Rule.GiveColor(firstCard, gameType, cardsOnHand)
            val giveTrumpRule = Rule.GiveTrump(firstCard, gameType, cardsOnHand)
            val giveFreeRule = Rule.GiveFree(firstCard, gameType, cardsOnHand)

            var trueCount = 0
            if (giveColorRule.applies()) ++trueCount
            if (giveTrumpRule.applies()) ++trueCount
            if (giveFreeRule.applies()) ++trueCount

            assertEquals(1, trueCount)
        }
    }

}