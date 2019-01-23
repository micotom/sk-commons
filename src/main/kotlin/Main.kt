import logic.History
import logic.orderBy
import model.*
import util.CardsShuffler

fun main(args: Array<String>) {

    val p1 = Player.ComputerPlayer("P1")
    val p2 = Player.ComputerPlayer("P2")
    val p3 = Player.ComputerPlayer("P3")
    val p4 = Player.ComputerPlayer("P4")

    val playerList = ArrayList<Player>().apply {
        add(p1)
        add(p2)
        add(p3)
        add(p4)
    }

    val cardSet = CardSet.Short

    val table = Table(p1, p2, p3, p4, cardSet)

    val gameType = GameType.SauSpiel(Cards.EICHEL_AS, cardSet)

    val shuffler = CardsShuffler(cardSet)

    // start round
    val shuffledCards = shuffler.shuffle().toList()
    playerList.forEachIndexed { index, player ->
        println("$player: ${shuffledCards[index].orderBy(gameType).reversed().joinToString()}")
        player.handOverCards(shuffledCards[index])
    }

    val gameState = GameState(table)
    println("-")
    (0..6).forEach {
        println("round $it")
        playerList.forEach { player ->
            val cardPlayed = player.playCard(gameState.cardsOnTable.values.toList(), History(), gameType)
            println("$player played: $cardPlayed")
            gameState.cardPlayed(player, cardPlayed)
        }
    }

}