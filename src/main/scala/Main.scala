package model

import scala.util.Random

object Main {

  // Please note, I have never played poker in my life so my have misinterpreted some of the tasks and rules.

  def deck: Deck = Deck(
    for {
      suit <- List(Clubs, Diamonds, Hearts, Spades)
      rank <- List(Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten, Jack, Queen, King, Ace)
    } yield Card(suit, rank)
  )

  def shuffle(deck: Deck): Deck = Deck(Random.shuffle(deck.cards))

  def deal(deck: Deck): (List[Card], List[Card]) = {
    val (handOne, remaingDeck) = deck.cards.splitAt(5)
    val (handTwo, _) = remaingDeck.splitAt(5)
    (handOne, handTwo)
  }

  def isPair(hand: List[Card]): Boolean =
    hand.groupBy(_.rank).values.exists(_.size == 2)

  def isTwoPair(hand: List[Card]): Boolean =
    hand.groupBy(_.rank).values.count(_.size == 2) == 2

  def isThreeOfAKind(hand: List[Card]): Boolean =
    hand.groupBy(_.rank).values.exists(_.size == 3)

  def isStraight(hand: List[Card]): Boolean = {
    val ranks = hand.map(_.rank.order).sorted
    ranks.zip(ranks.tail).forall { case (a, b) => b == a + 1 }
  }

  def isFlush(hand: List[Card]): Boolean =
    hand.groupBy(_.suit).values.size == 1

  def isFullHouse(hand: List[Card]): Boolean =
    hand.groupBy(_.rank).values.toList.map(_.size).sorted == List(2, 3)

  def isFourOfAKind(hand: List[Card]): Boolean =
    hand.groupBy(_.rank).values.exists(_.size == 4)

  def isStraightFlush(hand: List[Card]): Boolean =
    isStraight(hand) && isFlush(hand)

  def isHighCard(hand: List[Card]): Boolean =
    !List(isPair(hand), isTwoPair(hand), isThreeOfAKind(hand), isStraight(hand), isFlush(hand),
      isFullHouse(hand), isFourOfAKind(hand), isStraightFlush(hand)).exists(identity)

  def isRoyalFlush(hand: List[Card]): Boolean =
    isStraightFlush(hand) && hand.map(_.rank).toSet == Set(Ace, King, Queen, Jack, Ten)

  implicit val rankOrdering: Ordering[Rank] = Ordering.by(_.order)

  implicit val handOrdering: Ordering[List[Card]] = Ordering.by { hand =>
    if (isRoyalFlush(hand)) 10
    else if (isStraightFlush(hand)) 9
    else if (isFourOfAKind(hand)) 8
    else if (isFullHouse(hand)) 7
    else if (isFlush(hand)) 6
    else if (isStraight(hand)) 5
    else if (isThreeOfAKind(hand)) 4
    else if (isTwoPair(hand)) 3
    else if (isPair(hand)) 2
    else if (isHighCard(hand)) 1
    else 0
  }

  def identifyBestHandPossible(hand: List[Card]): PokerHand = hand match {
    case _ if isRoyalFlush(hand) => RoyalFlush
    case _ if isStraightFlush(hand) => StraightFlush
    case _ if isFourOfAKind(hand) => FourOfAKind
    case _ if isFullHouse(hand) => FullHouse
    case _ if isFlush(hand) => Flush
    case _ if isStraight(hand) => Straight
    case _ if isThreeOfAKind(hand) => ThreeOfAKind
    case _ if isTwoPair(hand) => TwoPair
    case _ if isPair(hand) => Pair
    case _ => HighCard(hand.map(_.rank).max(rankOrdering))
  }

  def winner(player1: List[Card], player2: List[Card]): Option[List[Card]] =
    if (handOrdering.gt(player1, player2)) Some(player1)
    else if (handOrdering.lt(player1, player2)) Some(player2)
    else None

  def main(args: Array[String]): Unit = {
    val shuffledDeck = shuffle(deck)

    // Part One: write a function to deal a random 5 card hand from a deck of cards
    val (hand1, hand2) = deal(shuffledDeck)
    println(s"Hand 1: $hand1")
    println(s"Hand 2: $hand2")

    // Part Two: write functions to determine whether a set of cards meet the conditions of the hands above
    println(s"Is High Card: ${isHighCard(hand1)}, ${isHighCard(hand2)}")
    println(s"Is Pair: ${isPair(hand1)}, ${isPair(hand2)}")
    println(s"Is Two Pair: ${isTwoPair(hand1)}, ${isTwoPair(hand2)}")
    println(s"Is Three Of A Kind: ${isThreeOfAKind(hand1)}, ${isThreeOfAKind(hand2)}")
    println(s"Is Straight: ${isStraight(hand1)}, ${isStraight(hand2)}")
    println(s"Is Flush: ${isFlush(hand1)}, ${isFlush(hand2)}")
    println(s"Is Full House: ${isFullHouse(hand1)}, ${isFullHouse(hand2)}")
    println(s"Is Four Of A Kind: ${isFourOfAKind(hand1)}, ${isFourOfAKind(hand2)}")
    println(s"Is Straight Flush: ${isStraightFlush(hand1)}, ${isStraightFlush(hand2)}")
    println(s"Is Royal Flush: ${isRoyalFlush(hand1)}, ${isRoyalFlush(hand2)}")

    // Part Three: Write a function to determine the best hand out of a set of hands
    val bestHandResult = identifyBestHandPossible(hand1)
    println(s"Best Hand Possible: $bestHandResult")

    // Part Four: Write a function to determine the winner of a game of poker
    val winnerResult = winner(hand1, hand2)
    winnerResult match {
      case Some(winner) => println(s"Winner: $winner")
      case None => println("It's a tie!")
    }
  }

}
