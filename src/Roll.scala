package org.frankversnel.dnddice

import scala.util.Random

class RollResult (val rolls: List[Int], val modifier: Modifier) {

	def dieCount: Int = rolls.size
	def total: Int = rolls.sum + modifier.value

	override def toString: String = {
		val rollsString = rolls.map(_.toString).reduce(_ + " " + _)
		val modifierString = if (modifier.nonNeutral) " (" + modifier + ")" else ""
		val totalString = if (dieCount > 1 || modifier.nonNeutral) " [Total: " + total + "]" else ""
		return rollsString + modifierString + totalString
	}
}

class Roll private(val dieCount: Int, val die: Die, val modifier: Modifier) {

	def perform: RollResult = {
		val rolls = for(i <- 1 to dieCount) yield die.roll
		return new RollResult(rolls.toList, modifier)
	}

	override def toString = dieCount.toString + die.toString + modifier.toString
}
object Roll extends RollParser {
	private val oneThrow = 1

	def apply(input: String): Option[Roll] = parse(input)
	def apply(die: Die) = new Roll(oneThrow, die, Modifier.Neutral)
	def apply(dieCount: Int, die: Die) = new Roll(dieCount, die, Modifier.Neutral)
	def apply(die: Die, modifier: Modifier) = new Roll(oneThrow, die, modifier)
	def apply(dieCount: Int, die: Die, modifier: Modifier) = new Roll(dieCount, die, modifier)
}

class Modifier private(val value: Int) {
	def nonNeutral = value != 0
	override def toString = if (value >= 0) "+" + value else value.toString
}
object Modifier {
	val Neutral = new Modifier(0)
	def apply(value: Int) = new Modifier(value)
}

class Die private(val sides: Int, val numberGenerator: Random) {
	private val SideOffset = 1

	def roll = numberGenerator.nextInt(sides) + SideOffset
	override def toString = "d" + sides
}
object Die {
	private val NumberGenerator = new Random(System.currentTimeMillis)

	def apply(sides: Int) = new Die(sides, NumberGenerator)
}
