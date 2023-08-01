package model

sealed trait Rank {
  def order: Int
}
case object Two extends Rank {
  val order = 2
}
case object Three extends Rank {
  val order = 3
}
case object Four extends Rank {
  val order = 4
}
case object Five extends Rank {
  val order = 5
}
case object Six extends Rank {
  val order = 6
}
case object Seven extends Rank {
  val order = 7
}
case object Eight extends Rank {
  val order = 8
}
case object Nine extends Rank {
  val order = 9
}
case object Ten extends Rank {
  val order = 10
}
case object Jack extends Rank {
  val order = 11
}
case object Queen extends Rank {
  val order = 12
}
case object King extends Rank {
  val order = 13
}
case object Ace extends Rank {
  val order = 14
}
