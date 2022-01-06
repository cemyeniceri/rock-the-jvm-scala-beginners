package lectures.part1basics

object Expressions extends App {

  val x = 1 + 2 // EXPRESSION
  println(x)

  println(2 + 3 * 4)

  println(1 == x)

  println(!(1 == x ))

  var aVariable = 2
  aVariable +=3 // ..... side-effects
  println(aVariable)

  // Instructions (DO) vs Expressions (VALUE)

  //IF EXPRESSION
  val aCondition = true
  val aConditionedValue = if(aCondition) 5 else 3 // IF EXPRESSION
  println(aConditionedValue)
  println(if(aCondition) 5 else 3)
  println(1 + 3)

  var i = 0
  while (i < 10) {
    println(i)
    i += 1
  }

  // NEVER WRITE THIS AGAIN

  // Everything in Scala is an Expression!

  val aWeirdValue = (aVariable = 3) // Unit === void
  println(aWeirdValue)


  // SIDE EFFECTS : println(), while, reassignments

  // CODE BLOCKS

  val aCodeBlock = {
    val y = 2
    val z = y + 1
    if (z > 3 ) "hello" else "goodbye"
  }
}
