package lectures.part1basics

object ValuesVariablesTypes extends App {

  val x: Int = 42
  println(x)


  // vals are immutable
  // compiler can infer types

  val aString: String = "hello"
  val anotherString = "Scala"

  val aBoolean: Boolean = false
  val aChar: Char = 'a'
  val anInt: Int = x
  val aShort: Short = 4613
  val aLong: Long = 527232323232323232L
  val aFloat: Float = 2.0f
  val aDouble: Double = 3.14

  // compiler can infer types
  var aVariable: Int = 5

  aVariable = 7
}
