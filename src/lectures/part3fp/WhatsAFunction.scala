package lectures.part3fp

object WhatsAFunction extends App {

  // DREAM => use functions as first class elements
  // problem : oop

  val doubler = new MyFunction[Int, Int] {
    override def apply(element: Int): Int = element * 2
  }

  println(doubler(2))

  // function types = Function1[A, B]
  val stringToInt = new Function1[String, Int] {
    override def apply(v1: String): Int = v1.toInt
  }
  println(stringToInt("4") + 3)


  val adder: (Int, Int) => Int = new Function2[Int, Int, Int] {
    override def apply(v1: Int, v2: Int): Int = v1 + v2
  }

  // function types Function2[A, B, R] === (A, B) => R

  // ALL SCALA FUNCTIONS ARE OBJECTS


  /** Exercises
   * 1- A function takes 2 Strings and concetenate them
   * 2- Transform the MyPredicate and My Transformer into function types
   * 3- Define a function which takes an int and returns another function which takes an int and returns an int
   *    - what's the type of this function
   *    - how to do it
   * */

  val concetenator = new Function2[String, String, String] {
    override def apply(v1: String, v2: String): String = v1.concat(v2)
  }

  val superAdder = new Function1[Int, Function1[Int, Int]] {
    override def apply(v1: Int): Int => Int = new Function[Int, Int] {
      override def apply(v2: Int): Int = v1 + v2
    }
  }

  val adder3 = superAdder(3)
  println(adder3(4))
  println(superAdder(3)(4)) // curried function
  println(concetenator("Hello", "Cem"))
}

trait MyFunction[A, B] {
  def apply(element: A): B
}