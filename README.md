# Poker Technical Challenge

This project is a solution for a poker technical challenge. It is a standard SBT project that compiles and shows an understanding of testing practices.

## Overview

This challenge involves modeling a deck of cards, dealing hands, and identifying the types of poker hands.

### Part One: Modelling a Deck of Cards

This section involves designing a domain model for a deck of cards. Each card belongs to one of the four suits (Clubs, Diamonds, Hearts, Spades) and has one of the following ranks (Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten, Jack, Queen, King, Ace).

There are 52 cards in a deck, each representing a unique combination of a suit and a rank. We also provide a function to deal a random 5 card hand from the deck.

### Part Two: Determine the Type of Poker Hand

Various types of poker hands with different conditions exist. This project implements functions to determine whether a set of cards meet the conditions of the following poker hands:

- High card: The highest Ranked card
- Pair: Two cards with the same Rank
- Two Pair: Two pairs in the same hand
- Three of a Kind: Three cards with the same Rank
- Straight: Five cards of sequential rank
- Flush: Five cards of the same suit but not sequential rank
- Full House: Three cards of one Rank and two cards of another Rank
- Four of a Kind: Four cards of the same Rank
- Straight Flush: Five cards of the same suit and sequential rank

### Part Three: Determine the Best Hand and Winners

We also provide a function to determine the best hand available using the order of hands provided above.

There is a function that, given two hands of cards (one for each player), determines the winning player based on the order in the list in Part Two.

## Build, Run and Test

To build the project, navigate to the project directory and use SBT:

```shell
sbt compile
```

```shell
sbt run
```

```shell
sbt test
```
