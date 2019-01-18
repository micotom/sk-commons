package logic

import model.CardSet
import model.Cards
import model.GameType

fun Cards.beats(otherCard: Cards, gameType: GameType) =
    when (gameType.trumps.contains(this) || gameType.trumps.contains(otherCard) || this.color == otherCard.color) {
        true -> ValueCalculator.getValueFor(this, setOf(otherCard, this), gameType) >
                    ValueCalculator.getValueFor(otherCard, setOf(this, otherCard), gameType)
        false -> false
    }

object ValueCalculator {

    private fun GameType.ranking(): Ranking = when (this) {
        is GameType.Solo.FarbSolo -> Ranking.Solo.Colored.FarbSolo(cardSet, color)
        is GameType.Solo.FarbGeier -> Ranking.Solo.Colored.FarbGeier(cardSet, color)
        is GameType.Solo.FarbWenz -> Ranking.Solo.Colored.FarbWenz(cardSet, color)
        is GameType.Solo.Geier -> Ranking.Solo.Geier(cardSet)
        is GameType.Solo.Wenz -> Ranking.Solo.Wenz(cardSet)
        is GameType.SauSpiel -> Ranking.SauSpiel(cardSet)
    }

    fun getValueFor(card: Cards, remainingCards: Set<Cards>, gameType: GameType): Int {
        val ranking = gameType.ranking()

        val nrOfNonTrumps = remainingCards.filter {
            !gameType.trumps.contains(it)
        }.size

        return with(ranking) {
            when (gameType.trumps.contains(card)) {
                true -> this.trumpsRanking.filter { remainingCards.contains(it) }
                    .indexOf(card) + nrOfNonTrumps + 1
                false -> this.nonTrumpsRanking[card.color]!!.filter { remainingCards.contains(it) }
                    .indexOf(card) + 1
            }
        }

    }

    private sealed class Ranking {

        abstract val trumpsRanking: List<Cards>
        abstract val nonTrumpsRanking: Map<Cards.Color, List<Cards>>

        protected fun List<Cards>.sortAsNonTrumpsInColorMap() =
            groupBy { it.color }.mapValues { (_, cards) -> cards.sortedBy { it.value.defaultRank } }


        sealed class Solo : Ranking() {

            data class Wenz(private val cardSet: CardSet) : Solo() {

                override val trumpsRanking = listOf(
                    Cards.SCHELLEN_UNTER,
                    Cards.HERZ_UNTER,
                    Cards.GRAS_UNTER,
                    Cards.EICHEL_UNTER
                )

                override val nonTrumpsRanking =
                    cardSet.filter { it.value != Cards.Value.UNTER }.sortAsNonTrumpsInColorMap()

            }

            data class Geier(private val cardSet: CardSet) : Solo() {

                override val trumpsRanking = listOf(
                    Cards.SCHELLEN_OBER,
                    Cards.HERZ_OBER,
                    Cards.GRAS_OBER,
                    Cards.EICHEL_OBER
                )

                override val nonTrumpsRanking =
                    cardSet.filter { it.value != Cards.Value.OBER }.sortAsNonTrumpsInColorMap()

            }

            sealed class Colored : Solo() {

                data class FarbSolo(private val cardSet: CardSet, private val soloColor: Cards.Color) : Colored() {

                    override val trumpsRanking =
                        cardSet.filter {
                            it.color == soloColor && it.value != Cards.Value.OBER && it.value != Cards.Value.UNTER
                        }.sortedBy { it.value.defaultRank } + listOf(
                            Cards.SCHELLEN_UNTER,
                            Cards.HERZ_UNTER,
                            Cards.GRAS_UNTER,
                            Cards.EICHEL_UNTER,
                            Cards.SCHELLEN_OBER,
                            Cards.HERZ_OBER,
                            Cards.GRAS_OBER,
                            Cards.EICHEL_OBER
                        )

                    override val nonTrumpsRanking: Map<Cards.Color, List<Cards>> = cardSet.filter {
                        it.color != soloColor && it.value != Cards.Value.OBER && it.value != Cards.Value.UNTER
                    }.sortAsNonTrumpsInColorMap()

                }

                data class FarbWenz(private val cardSet: CardSet, private val soloColor: Cards.Color) : Colored() {

                    override val trumpsRanking =
                        cardSet.filter {
                            it.color == soloColor && it.value != Cards.Value.UNTER
                        }.sortedBy { it.value.defaultRank } + listOf(
                            Cards.SCHELLEN_UNTER,
                            Cards.HERZ_UNTER,
                            Cards.GRAS_UNTER,
                            Cards.EICHEL_UNTER
                        )

                    override val nonTrumpsRanking: Map<Cards.Color, List<Cards>> = cardSet.filter {
                        it.color != soloColor && it.value != Cards.Value.UNTER
                    }.sortAsNonTrumpsInColorMap()

                }

                data class FarbGeier(private val cardSet: CardSet, private val soloColor: Cards.Color) : Colored() {

                    override val trumpsRanking =
                        cardSet.filter {
                            it.color == soloColor && it.value != Cards.Value.OBER
                        }.sortedBy { it.value.defaultRank } + listOf(
                            Cards.SCHELLEN_OBER,
                            Cards.HERZ_OBER,
                            Cards.GRAS_OBER,
                            Cards.EICHEL_OBER
                        )

                    override val nonTrumpsRanking: Map<Cards.Color, List<Cards>> = cardSet.filter {
                        it.color != soloColor && it.value != Cards.Value.OBER
                    }.sortAsNonTrumpsInColorMap()

                }

            }

        }

        data class SauSpiel(private val cardSet: CardSet) : Ranking() {

            override val trumpsRanking =
                listOf(
                    Cards.HERZ_SIEBENER,
                    Cards.HERZ_ACHTER,
                    Cards.HERZ_NEUNER,
                    Cards.HERZ_KOENIG,
                    Cards.HERZ_ZEHNER,
                    Cards.HERZ_AS,
                    Cards.SCHELLEN_UNTER,
                    Cards.HERZ_UNTER,
                    Cards.GRAS_UNTER,
                    Cards.EICHEL_UNTER,
                    Cards.SCHELLEN_OBER,
                    Cards.HERZ_OBER,
                    Cards.GRAS_OBER,
                    Cards.EICHEL_OBER
                )

            override val nonTrumpsRanking: Map<Cards.Color, List<Cards>> =
                mapOf(
                    Cards.Color.EICHEL to listOf(
                        Cards.EICHEL_SIEBENER,
                        Cards.EICHEL_ACHTER,
                        Cards.EICHEL_NEUNER,
                        Cards.EICHEL_KOENIG,
                        Cards.EICHEL_ZEHNER,
                        Cards.EICHEL_AS
                    ),
                    Cards.Color.GRAS to listOf(
                        Cards.GRAS_SIEBENER,
                        Cards.GRAS_ACHTER,
                        Cards.GRAS_NEUNER,
                        Cards.GRAS_KOENIG,
                        Cards.GRAS_ZEHNER,
                        Cards.GRAS_AS
                    ),
                    Cards.Color.SCHELLEN to listOf(
                        Cards.SCHELLEN_SIEBENER,
                        Cards.SCHELLEN_ACHTER,
                        Cards.SCHELLEN_NEUNER,
                        Cards.SCHELLEN_KOENIG,
                        Cards.SCHELLEN_ZEHNER,
                        Cards.SCHELLEN_AS
                    )
                )

        }

    }

}