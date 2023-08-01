package model

sealed trait PokerHand

case object RoyalFlush extends PokerHand
case object StraightFlush extends PokerHand
case object FourOfAKind extends PokerHand
case object FullHouse extends PokerHand
case object Flush extends PokerHand
case object Straight extends PokerHand
case object ThreeOfAKind extends PokerHand
case object TwoPair extends PokerHand
case object Pair extends PokerHand
case class HighCard(rank: Rank) extends PokerHand