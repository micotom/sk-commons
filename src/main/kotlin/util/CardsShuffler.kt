package util

import model.CardSet
import model.Cards

class CardsShuffler(private val cards: CardSet) {

    fun shuffle(): Set<Set<Cards>> {
        val nrOfCardsPerSet = when (cards) {
            is CardSet.Long -> 8
            is CardSet.Short -> 6
        }
        return create4RandomSetsFrom(cards.toList(), nrOfCardsPerSet)
    }

    private tailrec fun <T> create4RandomSetsFrom(
        sourceList: List<T>,
        nrOfCardsPerSet: Int,
        targetIndex: Int = 0,
        accSets: MutableList<Set<T>> = mutableListOf(emptySet(), emptySet(), emptySet(), emptySet())
    ): Set<Set<T>> =
        when (sourceList.isEmpty()) {
            true -> accSets.toSet()
            false -> {
                val randomSet = pickRandom(nrOfCardsPerSet, sourceList)
                create4RandomSetsFrom(
                    sourceList - randomSet,
                    nrOfCardsPerSet,
                    targetIndex + 1,
                    accSets.apply { this[targetIndex] = randomSet }
                )
            }
        }

    private tailrec fun <T> pickRandom(n: Int, sourceList: List<T>, accSet: Set<T> = emptySet()): Set<T> =
        when (accSet.size) {
            n -> accSet
            else -> {
                val r = (0..(sourceList.size - 1)).random()
                val element = sourceList[r]
                pickRandom(n, sourceList - element, accSet + element)
            }
        }

}