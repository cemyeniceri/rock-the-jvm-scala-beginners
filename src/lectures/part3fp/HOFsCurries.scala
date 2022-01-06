package lectures.part3fp

object HOFsCurries extends App {

  val superFunction: (Int, (String, (Int => Boolean)) => Int) => (Int => Int) = null
  // higher order function (HOF)

  // map, flatMap, filter in MyList

  // function that applies a function n times over a value x
  // nTimes(f, n, x)
  // nTimes(f, 3, x) = f(f(f(x))) = nTimes(f, 2, f(x))
  // nTimes(f, n, x) = f(f(.....(x))) = nTimes(f, n-1, f(x))
  def nTimes(f: Int => Int, n: Int, x: Int): Int = {
    if (n <= 0) x
    else nTimes(f, n - 1, f(x))
  }

  val plusOne = (x: Int) => x + 1
  println(nTimes(plusOne, 10, 1))

  def nTimesBetter(f: Int => Int, n: Int): (Int => Int) = {
    if (n <= 0) (x => x)
    else x => nTimesBetter(f, n - 1)(f(x))
  }

  println(nTimesBetter(plusOne, 10)(1))

  // curried functions
  val superAdder: Int => Int => Int = (x: Int) => (y: Int) => x + y
  val add3 = superAdder(3)
  println(add3(4))
  println(superAdder(3)(4))

  // functions with multiple parameter lists
  def curriedFormatter(c: String)(x: Double): String = c.format(x)

  val standartFormatter: (Double => String) = curriedFormatter("%4.2f")
  val preicseFormatter: (Double => String) = curriedFormatter("%10.8f")

  println(standartFormatter(Math.PI))
  println(preicseFormatter(Math.PI))

  /**
   * 1- toCurry(f: (Int, Int) => Int) => (Int => Int => Int)
   * fromCurry(f: (Int => Int => Int)) => (Int, Int) => Int
   *
   * 2- compose(f,g) => f(g(x))
   * andThen(f,g) => g(f(x))
   * */

  def toCurry(f: (Int, Int) => Int): (Int => Int => Int) =
    x => y => f(x, y)

  def fromCurry(f: (Int => Int => Int)): (Int, Int) => Int =
    (x, y) => f(x)(y)

  def compose[A, B, C](f: A => B, g: C => A): C => B =
    x => f(g(x))

  def andThen[A, B, C](f: A => B, g: B => C): A => C =
    x => g(f(x))

  def superAdder2: (Int => Int => Int) = toCurry(_ + _)

  def add4 = superAdder2(4)

  println(add4(17))

  val simpleAdder = fromCurry(superAdder2)
  println(simpleAdder(4, 17))

  val add2 = (x: Int) => x + 2
  val times3 = (x: Int) => x * 3

  val composed = compose(add2, times3)
  val ordered = andThen(add2, times3)

  println(composed(2))
  println(ordered(2))
}
