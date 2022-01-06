package exercises

abstract class MyGenericCovariantList[+A] {
  /*
  * head => first element of the list
  * tail => rest of the list
  * isEmpty => is the list empty
  * add(int) => new list with this element added
  * toString => a string representation of the list
  */

  def head: A

  def tail: MyGenericCovariantList[A]

  def isEmpty: Boolean

  def add[B >: A](item: B): MyGenericCovariantList[B]

  def printElements: String

  override def toString: String = "[" + printElements + "]"
}

object Empty2 extends MyGenericCovariantList[Nothing] {
  override def head: Nothing = throw new NoSuchElementException

  override def tail: MyGenericCovariantList[Nothing] = throw new NoSuchElementException

  override def isEmpty: Boolean = true

  override def add[B >: Nothing](item: B): MyGenericCovariantList[B] = new Cons2(item, Empty2)

  override def printElements: String = ""
}

class Cons2[+A](h: A, t: MyGenericCovariantList[A]) extends MyGenericCovariantList[A] {
  override def head: A = h

  override def tail: MyGenericCovariantList[A] = t

  override def isEmpty: Boolean = false

  override def add[B >: A](item: B): MyGenericCovariantList[B] = new Cons2(item, this)

  override def printElements: String = {
    if (t.isEmpty) "" + h
    else "" + h + " " + t.printElements
  }
}

object ListTest2 extends App {
  val listOfIntegers: MyGenericCovariantList[Int] = new Cons2(1, new Cons2(2, new Cons2(3, Empty2)))
  val listOfStrings: MyGenericCovariantList[String] = new Cons2("1", new Cons2("2", new Cons2("3", Empty2)))
  println(listOfIntegers.head)
  println(listOfIntegers.tail.head)
  println(listOfIntegers.tail.tail.head)
  println(listOfIntegers.add(4).head)
  println(listOfIntegers.isEmpty)
  println(listOfIntegers.toString)
}