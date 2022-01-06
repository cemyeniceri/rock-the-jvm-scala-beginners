package exercises

/** Exercises
 * 1- A function takes 2 Strings and concetenate them
 * 2- Transform the MyPredicate and My Transformer into function types
 * */

abstract class FunctionalList[+A] {
  /*
  * head => first element of the list
  * tail => rest of the list
  * isEmpty => is the list empty
  * add(int) => new list with this element added
  * toString => a string representation of the list
  */

  def head: A

  def tail: FunctionalList[A]

  def isEmpty: Boolean

  def add[B >: A](item: B): FunctionalList[B]

  def printElements: String

  override def toString: String = "[" + printElements + "]"

  // higher-order functions
  def map[B](transform: A => B): FunctionalList[B]
  def flatMap[B](transform: A => FunctionalList[B]): FunctionalList[B]
  def filter(predicate: A => Boolean): FunctionalList[A]

  // concatenation
  def ++[B >: A](list: FunctionalList[B]): FunctionalList[B]
}

object Empty4 extends FunctionalList[Nothing] {
  override def head: Nothing = throw new NoSuchElementException

  override def tail: FunctionalList[Nothing] = throw new NoSuchElementException

  override def isEmpty: Boolean = true

  override def add[B >: Nothing](item: B): FunctionalList[B] = new Cons4(item, Empty4)

  override def printElements: String = ""

  override def map[B](transformer: Nothing => B): FunctionalList[B] = Empty4

  override def flatMap[B](transformer: Nothing => FunctionalList[B]): FunctionalList[B] = Empty4

  override def filter(predicate: Nothing => Boolean): FunctionalList[Nothing] = Empty4

  override def ++[B >: Nothing](list: FunctionalList[B]): FunctionalList[B] = list
}

class Cons4[+A](h: A, t: FunctionalList[A]) extends FunctionalList[A] {
  override def head: A = h

  override def tail: FunctionalList[A] = t

  override def isEmpty: Boolean = false

  override def add[B >: A](item: B): FunctionalList[B] = new Cons4(item, this)

  override def printElements: String = {
    if (t.isEmpty) "" + h
    else "" + h + " " + t.printElements
  }


  /*
  * [1,2,3].map(n * 2)
  * = new Cons4(2, [2,3].map(n * 2))
  * = new Cons4(2, new Cons4(4, [3].map(n * 2)))
  * = new Cons4(2, new Cons4(4, new Cons4(6, Empty.map(n * 2))))
  * = new Cons4(2, new Cons4(4, new Cons4(6, Empty)))
  * */
  override def map[B](transformer: A => B): FunctionalList[B] = {
    new Cons4[B](transformer(h), t.map(transformer))
  }

  /*
  * [1,2].flatMap(n => [n, n+1])
  * = [1,2] ++ [2].flatMap(n => [n, n+1])
  * = [1,2] ++ [2,3] ++ Empty.flatMap(n => [n, n+1])
  * = [1,2] ++ [2,3] ++ Empty
  * = [1,2,2,3]
  * */
  override def flatMap[B](transformer: A => FunctionalList[B]): FunctionalList[B] =
    transformer(h) ++ t.flatMap(transformer)

  /*
  * [1,2,3].filter(n % 2 == 0)
  * [2,3].filter(n % 2 == 0)
  * new Cons4(2, [3].filter(n % 2 == 0))
  * new Cons4(2, Empty.filter(n % 2 == 0))
  * new Cons4(2, Empty)
  * */
  override def filter(predicate: A => Boolean): FunctionalList[A] = {
    if (predicate(h)) new Cons4(h, tail.filter(predicate))
    else t.filter(predicate)
  }

  /*
  * [1,2] ++ [3,4,5]
  * = new Cons4(1, [2] ++ [3,4,5])
  * = new Cons4(1, new Cons4(2 Empty ++ [3,4,5]))
  * = new Cons4(1, new Cons4(2, new Cons4(3 ,new Cons4(4, new Cons4(5, Empty)))))
  * */
  override def ++[B >: A](list: FunctionalList[B]): FunctionalList[B] = new Cons4[B](h, t ++ list)
}

object ListTest4 extends App {
  val listOfIntegers: FunctionalList[Int] = new Cons4(1, new Cons4(2, new Cons4(3, Empty4)))
  val listOfStrings: FunctionalList[String] = new Cons4("Hello", new Cons4("Scala", new Cons4("World", Empty4)))

  println(listOfIntegers.toString)
  println(listOfStrings.toString)

  println(listOfIntegers.map(new Function1[Int, Int] {
    override def apply(elem: Int): Int = elem * 2
  }))

  println(listOfIntegers.filter(new Function1[Int, Boolean] {
    override def apply(elem: Int): Boolean = elem % 2 == 0
  }))

  println(listOfIntegers.flatMap(new Function1[Int, FunctionalList[Int]] {
    override def apply(elem: Int): FunctionalList[Int] = new Cons4(elem, new Cons4(elem + 1, Empty4))
  }))
}