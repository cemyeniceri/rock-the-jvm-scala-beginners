package exercises

/*
1. Generic trait MyPredicate [-T] with a little method test(T) => Boolean
2. Generic trait MyTransformer[-A, B] with a method transform(A) => B
3. MyList:
  - map(transformer) => ExpandedList
  - filter(predicate) => ExpandedList
  - flatMap(transformer from A to MyList[B]) => ExpandedList[B]

  class EvenPredicate extends MyPredicate[Int]
  class StringToIntTransformer extends MyTransformer[String, Int]

  [1,2,3].map(n * 2) = [2,4,6]
  [1,2,3,4].filter(n % 2) = [2,4]
  [1,2,3].flatMAp(n => [n, n+1]) = [1,2,2,3,3,4]
* */

abstract class ExpandedList[+A] {
  /*
  * head => first element of the list
  * tail => rest of the list
  * isEmpty => is the list empty
  * add(int) => new list with this element added
  * toString => a string representation of the list
  */

  def head: A

  def tail: ExpandedList[A]

  def isEmpty: Boolean

  def add[B >: A](item: B): ExpandedList[B]

  def printElements: String

  override def toString: String = "[" + printElements + "]"

  def map[B](transform: MyTransformer[A, B]): ExpandedList[B]

  def flatMap[B](transform: MyTransformer[A, ExpandedList[B]]): ExpandedList[B]

  def filter(predicate: MyPredicate[A]): ExpandedList[A]

  // concatenation
  def ++[B >: A](list: ExpandedList[B]): ExpandedList[B]
}

object Empty3 extends ExpandedList[Nothing] {
  override def head: Nothing = throw new NoSuchElementException

  override def tail: ExpandedList[Nothing] = throw new NoSuchElementException

  override def isEmpty: Boolean = true

  override def add[B >: Nothing](item: B): ExpandedList[B] = new Cons3(item, Empty3)

  override def printElements: String = ""

  override def map[B](transformer: MyTransformer[Nothing, B]): ExpandedList[B] = Empty3

  override def flatMap[B](transformer: MyTransformer[Nothing, ExpandedList[B]]): ExpandedList[B] = Empty3

  override def filter(predicate: MyPredicate[Nothing]): ExpandedList[Nothing] = Empty3

  override def ++[B >: Nothing](list: ExpandedList[B]): ExpandedList[B] = list
}

class Cons3[+A](h: A, t: ExpandedList[A]) extends ExpandedList[A] {
  override def head: A = h

  override def tail: ExpandedList[A] = t

  override def isEmpty: Boolean = false

  override def add[B >: A](item: B): ExpandedList[B] = new Cons3(item, this)

  override def printElements: String = {
    if (t.isEmpty) "" + h
    else "" + h + " " + t.printElements
  }


  /*
  * [1,2,3].map(n * 2)
  * = new Cons3(2, [2,3].map(n * 2))
  * = new Cons3(2, new Cons3(4, [3].map(n * 2)))
  * = new Cons3(2, new Cons3(4, new Cons3(6, Empty.map(n * 2))))
  * = new Cons3(2, new Cons3(4, new Cons3(6, Empty)))
  * */
  override def map[B](transformer: MyTransformer[A, B]): ExpandedList[B] = {
    new Cons3[B](transformer.transform(h), t.map(transformer))
  }

  /*
  * [1,2].flatMap(n => [n, n+1])
  * = [1,2] ++ [2].flatMap(n => [n, n+1])
  * = [1,2] ++ [2,3] ++ Empty.flatMap(n => [n, n+1])
  * = [1,2] ++ [2,3] ++ Empty
  * = [1,2,2,3]
  * */
  override def flatMap[B](transformer: MyTransformer[A, ExpandedList[B]]): ExpandedList[B] =
    transformer.transform(h) ++ t.flatMap(transformer)

  /*
  * [1,2,3].filter(n % 2 == 0)
  * [2,3].filter(n % 2 == 0)
  * new Cons3(2, [3].filter(n % 2 == 0))
  * new Cons3(2, Empty.filter(n % 2 == 0))
  * new Cons3(2, Empty)
  * */
  override def filter(predicate: MyPredicate[A]): ExpandedList[A] = {
    if (predicate.test(h)) new Cons3(h, tail.filter(predicate))
    else t.filter(predicate)
  }

  /*
  * [1,2] ++ [3,4,5]
  * = new Cons3(1, [2] ++ [3,4,5])
  * = new Cons3(1, new Cons3(2 Empty ++ [3,4,5]))
  * = new Cons3(1, new Cons3(2, new Cons3(3 ,new Cons3(4, new Cons3(5, Empty)))))
  * */
  override def ++[B >: A](list: ExpandedList[B]): ExpandedList[B] = new Cons3[B](h, t ++ list)
}

trait MyPredicate[-T] {
  def test(elem: T): Boolean
}

trait MyTransformer[-A, B] {
  def transform(elem: A): B
}

object ListTest3 extends App {
  val listOfIntegers: ExpandedList[Int] = new Cons3(1, new Cons3(2, new Cons3(3, Empty3)))
  val listOfStrings: ExpandedList[String] = new Cons3("Hello", new Cons3("Scala", new Cons3("World", Empty3)))

  println(listOfIntegers.toString)
  println(listOfStrings.toString)

  println(listOfIntegers.map(new MyTransformer[Int, Int] {
    override def transform(elem: Int): Int = elem * 2
  }))

  println(listOfIntegers.filter(new MyPredicate[Int] {
    override def test(elem: Int): Boolean = elem % 2 == 0
  }))

  println(listOfIntegers.flatMap(new MyTransformer[Int, ExpandedList[Int]] {
    override def transform(elem: Int): ExpandedList[Int] = new Cons3(elem, new Cons3(elem+1, Empty3))
  }))
}