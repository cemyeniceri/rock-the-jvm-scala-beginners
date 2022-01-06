package lectures.part3fp

object AnonymousFunctions extends App {

  /* Instead this OOP way, use anonymous way (the way more functional)
    val doubler = new Function[Int, Int] {
      override def apply(v1: Int): Int = v1 * 2
    }
    */

  // anonymous function (LAMBDA) in two ways
  val doubler = (x: Int) => x * 2
  val doubler2: Int => Int = x => x * 2

  // multiple params in a lambda
  val adder: (Int, Int) => Int = (x, y) => x + y

  // no params
  val justDoSth = () => 3
  val justDoSth2: () => Int = () => 3

  // be careful!!
  println(justDoSth) // function itself
  println(justDoSth()) // call lambda with no params

  // curly braces with lambdas
  val stringToInt = { (str: String) =>
    str.toInt
  }

  // MORE Syntactic sugar
  val niceIncrementer: Int => Int = _ + 1 // equivalent to x => x + 1
  val niceAdder: (Int, Int) => Int = _ + _ // equivalent to (a, b) => a + b

  val superAdder: (Int => (Int => Int)) = v1 => (v2 => v1 * v2)
  println(superAdder(3)(4))
}
