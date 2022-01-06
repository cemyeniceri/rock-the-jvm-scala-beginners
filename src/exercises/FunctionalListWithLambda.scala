package exercises

/** Exercises
 * 1- Replace all FunctionX calls with Lambda
 */

abstract class FunctionalListWithLambda[+A] {
  /*
  * head => first element of the list
  * tail => rest of the list
  * isEmpty => is the list empty
  * add(int) => new list with this element added
  * toString => a string representation of the list
  */

  def head: A

  def tail: FunctionalListWithLambda[A]

  def isEmpty: Boolean

  def add[B >: A](item: B): FunctionalListWithLambda[B]

  def printElements: String

  override def toString: String = "[" + printElements + "]"

  // higher-order functions
  def map[B](transform: A => B): FunctionalListWithLambda[B]

  def flatMap[B](transform: A => FunctionalListWithLambda[B]): FunctionalListWithLambda[B]

  def filter(predicate: A => Boolean): FunctionalListWithLambda[A]

  // concatenation
  def ++[B >: A](list: FunctionalListWithLambda[B]): FunctionalListWithLambda[B]
}

object Empty5 extends FunctionalListWithLambda[Nothing] {
  override def head: Nothing = throw new NoSuchElementException

  override def tail: FunctionalListWithLambda[Nothing] = throw new NoSuchElementException

  override def isEmpty: Boolean = true

  override def add[B >: Nothing](item: B): FunctionalListWithLambda[B] = new Cons5(item, Empty5)

  override def printElements: String = ""

  override def map[B](transformer: Nothing => B): FunctionalListWithLambda[B] = Empty5

  override def flatMap[B](transformer: Nothing => FunctionalListWithLambda[B]): FunctionalListWithLambda[B] = Empty5

  override def filter(predicate: Nothing => Boolean): FunctionalListWithLambda[Nothing] = Empty5

  override def ++[B >: Nothing](list: FunctionalListWithLambda[B]): FunctionalListWithLambda[B] = list
}

class Cons5[+A](h: A, t: FunctionalListWithLambda[A]) extends FunctionalListWithLambda[A] {
  override def head: A = h

  override def tail: FunctionalListWithLambda[A] = t

  override def isEmpty: Boolean = false

  override def add[B >: A](item: B): FunctionalListWithLambda[B] = new Cons5(item, this)

  override def printElements: String = {
    if (t.isEmpty) "" + h
    else "" + h + " " + t.printElements
  }


  /*
  * [1,2,3].map(n * 2)
  * = new Cons5(2, [2,3].map(n * 2))
  * = new Cons5(2, new Cons5(4, [3].map(n * 2)))
  * = new Cons5(2, new Cons5(4, new Cons5(6, Empty.map(n * 2))))
  * = new Cons5(2, new Cons5(4, new Cons5(6, Empty)))
  * */
  override def map[B](transformer: A => B): FunctionalListWithLambda[B] = {
    new Cons5[B](transformer(h), t.map(transformer))
  }

  /*
  * [1,2].flatMap(n => [n, n+1])
  * = [1,2] ++ [2].flatMap(n => [n, n+1])
  * = [1,2] ++ [2,3] ++ Empty.flatMap(n => [n, n+1])
  * = [1,2] ++ [2,3] ++ Empty
  * = [1,2,2,3]
  * */
  override def flatMap[B](transformer: A => FunctionalListWithLambda[B]): FunctionalListWithLambda[B] =
    transformer(h) ++ t.flatMap(transformer)

  /*
  * [1,2,3].filter(n % 2 == 0)
  * [2,3].filter(n % 2 == 0)
  * new Cons5(2, [3].filter(n % 2 == 0))
  * new Cons5(2, Empty.filter(n % 2 == 0))
  * new Cons5(2, Empty)
  * */
  override def filter(predicate: A => Boolean): FunctionalListWithLambda[A] = {
    if (predicate(h)) new Cons5(h, tail.filter(predicate))
    else t.filter(predicate)
  }

  /*
  * [1,2] ++ [3,4,5]
  * = new Cons5(1, [2] ++ [3,4,5])
  * = new Cons5(1, new Cons5(2 Empty ++ [3,4,5]))
  * = new Cons5(1, new Cons5(2, new Cons5(3 ,new Cons5(4, new Cons5(5, Empty)))))
  * */
  override def ++[B >: A](list: FunctionalListWithLambda[B]): FunctionalListWithLambda[B] = new Cons5[B](h, t ++ list)
}

object ListTest5 extends App {
  val listOfIntegers: FunctionalListWithLambda[Int] = new Cons5(1, new Cons5(2, new Cons5(3, Empty5)))
  val listOfStrings: FunctionalListWithLambda[String] = new Cons5("Hello", new Cons5("Scala", new Cons5("World", Empty5)))

  println(listOfIntegers.toString)
  println(listOfStrings.toString)

  println(listOfIntegers.map(_ * 2))

  println(listOfIntegers.filter(_ % 2 == 0))

  println(listOfIntegers.flatMap(x => new Cons5(x, new Cons5(x + 1, Empty5))))
}