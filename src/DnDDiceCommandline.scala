package org.frankversnel.dnddice

object DnDDice extends App {
	val Prompt = "DnDDice> "
	val Examples = "examples: 'd20', '4d8', '5d10+6'"

	Console.println(Examples)

	var isRunning = true
	while(isRunning) {
		Console.print(Prompt)

		val input = Console.readLine.toLowerCase

		if(input.equals("exit")) {
			isRunning = false
		} else {
			Roll(input) match {
				case Some(roll) => Console.println(roll.perform.toString)
				case _ => Console.println("Wrong input, try again.")
			}
		}
	}
}
