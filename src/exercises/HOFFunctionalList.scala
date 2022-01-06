package exercises

/** Exercises
 * 1- Expand the List with followings:
 *    - foreach function A => Unit
 *      [1,2,3].foreach(x => println(x))
 *    - sort function ((A, A) => Int) => HOFFunctionalList
 *      [1,2,3].sort((x,y) => y- x) => [3,2,1]
 *    - zipWith (list, (A, A) => B) => HOFFunctionalList[B]
 *      [1,2,3].zipWith([4,5,6], x * y) => [4,10,18]
 *    - fold(start)(function) >= a value
 *      [1,2,3].fold(0)(x + y) => 6
 * */

abstract class HOFFunctionalList[+A] {
  /*
  * head => first element of the list
  * tail => rest of the list
  * isEmpty => is the list empty
  * add(int) => new list with this element added
  * toString => a string representation of the list
  */

  def head: A

  def tail: HOFFunctionalList[A]

  def isEmpty: Boolean

  def add[B >: A](item: B): HOFFunctionalList[B]

  def printElements: String

  override def toString: String = "[" + printElements + "]"

  // higher-order functions
  def map[B](transform: A => B): HOFFunctionalList[B]

  def flatMap[B](transform: A => HOFFunctionalList[B]): HOFFunctionalList[B]

  def filter(predicate: A => Boolean): HOFFunctionalList[A]

  // concatenation
  def ++[B >: A](list: HOFFunctionalList[B]): HOFFunctionalList[B]

  //hofs
  def foreach(f: A => Unit): Unit

  def sort(compare: (A, A) => Int): HOFFunctionalList[A]

  def zipWith[B, C](list: HOFFunctionalList[B], zipFunc: (A, B) => C): HOFFunctionalList[C]

  def fold[B](start: B)(operator: (B, A) => B): B
}

object Empty6 extends HOFFunctionalList[Nothing] {
  override def head: Nothing = throw new NoSuchElementException

  override def tail: HOFFunctionalList[Nothing] = throw new NoSuchElementException

  override def isEmpty: Boolean = true

  override def add[B >: Nothing](item: B): HOFFunctionalList[B] = new Cons6(item, Empty6)

  override def printElements: String = ""

  override def map[B](transformer: Nothing => B): HOFFunctionalList[B] = Empty6

  override def flatMap[B](transformer: Nothing => HOFFunctionalList[B]): HOFFunctionalList[B] = Empty6

  override def filter(predicate: Nothing => Boolean): HOFFunctionalList[Nothing] = Empty6

  override def ++[B >: Nothing](list: HOFFunctionalList[B]): HOFFunctionalList[B] = list

  override def foreach(f: Nothing => Unit): Unit = ()

  override def sort(compare: (Nothing, Nothing) => Int): HOFFunctionalList[Nothing] = Empty6

  override def zipWith[B, C](list: HOFFunctionalList[B], zipFunc: (Nothing, B) => C): HOFFunctionalList[C] =
    if (!list.isEmpty) throw new RuntimeException("Lists do not have the same length!")
    else Empty6

  override def fold[B](start: B)(operator: (B, Nothing) => B): B = start
}

case class Cons6[+A](h: A, t: HOFFunctionalList[A]) extends HOFFunctionalList[A] {
  override def head: A = h

  override def tail: HOFFunctionalList[A] = t

  override def isEmpty: Boolean = false

  override def add[B >: A](item: B): HOFFunctionalList[B] = new Cons6(item, this)

  override def printElements: String = {
    if (t.isEmpty) "" + h
    else "" + h + " " + t.printElements
  }


  /*
  * [1,2,3].map(n * 2)
  * = new Cons6(2, [2,3].map(n * 2))
  * = new Cons6(2, new Cons6(4, [3].map(n * 2)))
  * = new Cons6(2, new Cons6(4, new Cons6(6, Empty.map(n * 2))))
  * = new Cons6(2, new Cons6(4, new Cons6(6, Empty)))
  * */
  override def map[B](transformer: A => B): HOFFunctionalList[B] = {
    new Cons6[B](transformer(h), t.map(transformer))
  }

  /*
  * [1,2].flatMap(n => [n, n+1])
  * = [1,2] ++ [2].flatMap(n => [n, n+1])
  * = [1,2] ++ [2,3] ++ Empty.flatMap(n => [n, n+1])
  * = [1,2] ++ [2,3] ++ Empty
  * = [1,2,2,3]
  * */
  override def flatMap[B](transformer: A => HOFFunctionalList[B]): HOFFunctionalList[B] =
    transformer(h) ++ t.flatMap(transformer)

  /*
  * [1,2,3].filter(n % 2 == 0)
  * [2,3].filter(n % 2 == 0)
  * new Cons6(2, [3].filter(n % 2 == 0))
  * new Cons6(2, Empty.filter(n % 2 == 0))
  * new Cons6(2, Empty)
  * */
  override def filter(predicate: A => Boolean): HOFFunctionalList[A] = {
    if (predicate(h)) new Cons6(h, tail.filter(predicate))
    else t.filter(predicate)
  }

  /*
  * [1,2] ++ [3,4,5]
  * = new Cons6(1, [2] ++ [3,4,5])
  * = new Cons6(1, new Cons6(2 Empty ++ [3,4,5]))
  * = new Cons6(1, new Cons6(2, new Cons6(3 ,new Cons6(4, new Cons6(5, Empty)))))
  * */
  override def ++[B >: A](list: HOFFunctionalList[B]): HOFFunctionalList[B] = new Cons6[B](h, t ++ list)

  override def foreach(f: A => Unit): Unit = {
    f(h)
    t.foreach(f)
  }

  override def sort(compare: (A, A) => Int): HOFFunctionalList[A] = {

    def insert(x: A, sortedList: HOFFunctionalList[A]): HOFFunctionalList[A] = {
      if (sortedList.isEmpty) new Cons6(x, Empty6)
      else if (compare(x, sortedList.head) <= 0) new Cons6(x, sortedList)
      else new Cons6(sortedList.head, insert(x, sortedList.tail))
    }

    val sortedTail = t.sort(compare)
    insert(h, sortedTail)
  }

  override def zipWith[B, C](list: HOFFunctionalList[B], zipFunc: (A, B) => C): HOFFunctionalList[C] = {
    if (list.isEmpty) throw new RuntimeException("Lists do not have the same length!")
    else new Cons6(zipFunc(h, list.head), t.zipWith(list.tail, zipFunc))
  }

  override def fold[B](start: B)(operator: (B, A) => B): B = {
    val newStart = operator(start, h)
    t.fold(newStart)(operator)
  }
}

object ListTest6 extends App {
  val listOfIntegers: HOFFunctionalList[Int] = new Cons6(1, new Cons6(2, new Cons6(3, Empty6)))
  val listOfStrings: HOFFunctionalList[String] = new Cons6("Hello", new Cons6("Scala", new Cons6("World", Empty6)))

  println(listOfIntegers.toString)
  println(listOfStrings.toString)

  println(listOfIntegers.map(new Function1[Int, Int] {
    override def apply(elem: Int): Int = elem * 2
  }))

  println(listOfIntegers.filter(new Function1[Int, Boolean] {
    override def apply(elem: Int): Boolean = elem % 2 == 0
  }))

  println(listOfIntegers.flatMap(new Function1[Int, HOFFunctionalList[Int]] {
    override def apply(elem: Int): HOFFunctionalList[Int] = new Cons6(elem, new Cons6(elem + 1, Empty6))
  }))

  println("-----foreach----")
  listOfIntegers.foreach(println)

  println("-----sort----")
  println(listOfIntegers.sort((x, y) => y - x))

  println("-----zipWith----")
  println(listOfIntegers.zipWith(listOfStrings, _ + "-" + _))

  println("-----fold----")
  println(listOfIntegers.fold(0)(_ + _))

  // for-comprehensions
  val combinations = for {
    n <- listOfIntegers
    s <- listOfStrings
  } yield s + "-" + n

  println(combinations)
}