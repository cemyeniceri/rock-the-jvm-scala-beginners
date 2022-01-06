package lectures.part3fp

object MapFlatMapFilterFor extends App {

  val list = List(1, 2, 3)
  println(list.head)
  println(list.tail)

  // map
  println(list.map(_ + 1))
  println(list.map(_ + " is a number"))

  // filter
  println(list.filter(_ % 2 == 0))

  // flatMap
  val toPair = (x: Int) => List(x, x + 1)
  println(list.flatMap(toPair))

  // print all combinations between two lists
  val numbers = List(1, 2, 3, 4)
  val chars = List('a', 'b', 'c', 'd')
  // List("a1", "a2", "a3", "a4",....."d3", "d4")

  println(chars.flatMap(x => numbers.filter(t => t % 2 == 0) map (y => "" + x + y)))

  // foreach
  list.foreach(println)

  // for-comprehensions
  val forCombinations = for {
    char <- chars
    number <- numbers if number % 2 == 0
  } yield "" + char + number

  println(forCombinations)

  for {
    n <- numbers
  } println(n)

  // syntax overload
  list.map { x =>
    x * 2
  }

  /**
   * 1- Does my HOFFunctionalList support for-comprehension ?
   *     If collection supports followings, YES!!
   *     map(f: A => B) => HOFFunctionalList[B]
   *     flatMap(f: A => HOFFunctionalList[B]) => HOFFunctionalList[B]
   *     filter(f: A => Boolean) => HOFFunctionalList[A]
   *
   * 2- A small collection of at most ONE element - MAYBE[T+]
   *    - map, flatMap, filter
   * */
}
