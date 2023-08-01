package model

import model.Main.{identifyBestHandPossible, winner}
import munit.FunSuite

class MainTest extends FunSuite {
  test("deck contains 52 cards") {
    assertEquals(Main.deck.cards.length, 52)
  }

  test("deck contains all suits") {
    val suitsInDeck = Main.deck.cards.map(_.suit).distinct
    assertEquals(suitsInDeck, List(Clubs, Diamonds, Hearts, Spades))
  }

  test("deck contains all ranks") {
    val ranksInDeck = Main.deck.cards.map(_.rank).distinct
    assertEquals(ranksInDeck, List(Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten, Jack, Queen, King, Ace))
  }

  test("deck contains exactly four cards of each rank") {
    val cardsPerRank = Main.deck.cards.groupBy(_.rank).mapValues(_.size)
    assert(cardsPerRank.values.forall(_ == 4))
  }

  test("deck contains exactly thirteen cards of each suit") {
    val cardsPerSuit = Main.deck.cards.groupBy(_.suit).mapValues(_.size)
    assert(cardsPerSuit.values.forall(_ == 13))
  }

  test("isPair") {
    val hand = List(Card(Clubs, Two), Card(Hearts, Two), Card(Spades, Three), Card(Diamonds, Four), Card(Hearts, Five))
    assert(Main.isPair(hand))

    val notPair = List(Card(Clubs, Two), Card(Hearts, Three), Card(Spades, Four), Card(Diamonds, Five), Card(Hearts, Six))
    assert(!Main.isPair(notPair))
  }

  test("isTwoPair") {
    val hand = List(Card(Clubs, Two), Card(Hearts, Two), Card(Spades, Three), Card(Diamonds, Three), Card(Hearts, Four))
    assert(Main.isTwoPair(hand))

    val notTwoPair = List(Card(Clubs, Two), Card(Hearts, Two), Card(Spades, Four), Card(Diamonds, Five), Card(Hearts, Six))
    assert(!Main.isTwoPair(notTwoPair))
  }

  test("isThreeOfAKind") {
    val hand = List(Card(Clubs, Two), Card(Hearts, Two), Card(Spades, Two), Card(Diamonds, Three), Card(Hearts, Four))
    assert(Main.isThreeOfAKind(hand))

    val notThreeOfAKind = List(Card(Clubs, Two), Card(Hearts, Three), Card(Spades, Four), Card(Diamonds, Five), Card(Hearts, Six))
    assert(!Main.isThreeOfAKind(notThreeOfAKind))
  }

  test("isStraight") {
    val hand = List(Card(Clubs, Two), Card(Hearts, Three), Card(Spades, Four), Card(Diamonds, Five), Card(Hearts, Six))
    assert(Main.isStraight(hand))

    val notStraight = List(Card(Clubs, Two), Card(Hearts, Three), Card(Spades, Four), Card(Diamonds, Five), Card(Hearts, Seven))
    assert(!Main.isStraight(notStraight))
  }

  test("isFlush") {
    val hand = List(Card(Clubs, Two), Card(Clubs, Three), Card(Clubs, Four), Card(Clubs, Five), Card(Clubs, Six))
    assert(Main.isFlush(hand))

    val notFlush = List(Card(Clubs, Two), Card(Hearts, Three), Card(Spades, Four), Card(Diamonds, Five), Card(Hearts, Six))
    assert(!Main.isFlush(notFlush))
  }

  test("isFullHouse") {
    val hand = List(Card(Clubs, Two), Card(Hearts, Two), Card(Spades, Two), Card(Diamonds, Three), Card(Hearts, Three))
    assert(Main.isFullHouse(hand))

    val notFullHouse = List(Card(Clubs, Two), Card(Hearts, Three), Card(Spades, Four), Card(Diamonds, Five), Card(Hearts, Six))
    assert(!Main.isFullHouse(notFullHouse))
  }

  test("isFourOfAKind") {
    val hand = List(Card(Clubs, Two), Card(Hearts, Two), Card(Spades, Two), Card(Diamonds, Two), Card(Hearts, Three))
    assert(Main.isFourOfAKind(hand))

    val notFourOfAKind = List(Card(Clubs, Two), Card(Hearts, Three), Card(Spades, Four), Card(Diamonds, Five), Card(Hearts, Six))
    assert(!Main.isFourOfAKind(notFourOfAKind))
  }

  test("isStraightFlush") {
    val hand = List(Card(Clubs, Two), Card(Clubs, Three), Card(Clubs, Four), Card(Clubs, Five), Card(Clubs, Six))
    assert(Main.isStraightFlush(hand))

    val notStraightFlush = List(Card(Clubs, Two), Card(Hearts, Three), Card(Spades, Four), Card(Diamonds, Five), Card(Hearts, Six))
    assert(!Main.isStraightFlush(notStraightFlush))
  }

  test("isHighCard") {
    val highCardHand = List(
      Card(Clubs, Two),
      Card(Diamonds, Five),
      Card(Hearts, Queen),
      Card(Spades, Seven),
      Card(Clubs, Jack)
    )

    val pairHand = List(
      Card(Clubs, Two),
      Card(Diamonds, Two),
      Card(Hearts, Queen),
      Card(Spades, Seven),
      Card(Clubs, Jack)
    )

    assert(Main.isHighCard(highCardHand))
    assert(!Main.isHighCard(pairHand))
  }

  test("identifyHand") {
    val hand1 = List(Card(Hearts, Ace), Card(Hearts, King), Card(Hearts, Queen), Card(Hearts, Jack), Card(Hearts, Ten)) // Royal Flush
    val hand2 = List(Card(Hearts, Nine), Card(Hearts, Eight), Card(Hearts, Seven), Card(Hearts, Six), Card(Hearts, Five)) // Straight Flush
    val hand3 = List(Card(Hearts, Four), Card(Diamonds, Four), Card(Clubs, Four), Card(Spades, Four), Card(Hearts, Ace)) // Four of a kind
    val hand4 = List(Card(Hearts, Four), Card(Diamonds, Four), Card(Clubs, Four), Card(Spades, Three), Card(Hearts, Three)) // Full House
    val hand5 = List(Card(Hearts, Four), Card(Hearts, Seven), Card(Hearts, Ten), Card(Hearts, Jack), Card(Hearts, King)) // Flush
    val hand6 = List(Card(Hearts, Four), Card(Diamonds, Five), Card(Clubs, Six), Card(Spades, Seven), Card(Hearts, Eight)) // Straight
    val hand7 = List(Card(Hearts, Four), Card(Diamonds, Four), Card(Clubs, Four), Card(Spades, Three), Card(Hearts, Two)) // Three of a Kind
    val hand8 = List(Card(Hearts, Four), Card(Diamonds, Four), Card(Clubs, Three), Card(Spades, Three), Card(Hearts, Two)) // Two Pair
    val hand9 = List(Card(Hearts, Four), Card(Diamonds, Four), Card(Clubs, Three), Card(Spades, Two), Card(Hearts, Ace)) // Pair
    val hand10 = List(Card(Hearts, Ace), Card(Diamonds, Two), Card(Clubs, Three), Card(Spades, Four), Card(Hearts, Five)) // High Card

    assertEquals(identifyBestHandPossible(hand1), RoyalFlush)
    assertEquals(identifyBestHandPossible(hand2), StraightFlush)
    assertEquals(identifyBestHandPossible(hand3), FourOfAKind)
    assertEquals(identifyBestHandPossible(hand4), FullHouse)
    assertEquals(identifyBestHandPossible(hand5), Flush)
    assertEquals(identifyBestHandPossible(hand6), Straight)
    assertEquals(identifyBestHandPossible(hand7), ThreeOfAKind)
    assertEquals(identifyBestHandPossible(hand8), TwoPair)
    assertEquals(identifyBestHandPossible(hand9), Pair)
    assertEquals(identifyBestHandPossible(hand10), HighCard(Ace))
  }

  test("winner") {
    val hand1 = List(Card(Hearts, Ace), Card(Hearts, King), Card(Hearts, Queen), Card(Hearts, Jack), Card(Hearts, Ten)) // Royal Flush
    val hand2 = List(Card(Hearts, Nine), Card(Hearts, Eight), Card(Hearts, Seven), Card(Hearts, Six), Card(Hearts, Five)) // Straight Flush


    assertEquals(winner(hand1, hand2), Some(hand1)) // Winner should be the hand with Royal Flush
    assertEquals(winner(hand2, hand1), Some(hand1)) // Winner should be the hand with Royal Flush

    val hand3 = List(Card(Hearts, Ace), Card(Hearts, King), Card(Hearts, Queen), Card(Hearts, Jack), Card(Hearts, Ten)) // Royal Flush again

    assertEquals(winner(hand1, hand3), None) // This should be a tie
  }



}
