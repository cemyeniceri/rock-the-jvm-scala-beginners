package lectures.part2oop

object OOBasics extends App {
  val person = new Person("John", 26)
  println(person.age)
  println(person.x)
  person.greet("CEM")
  person.greet()

  val author = new Writer("Charles", "Dickens", 1812)
  val novel = new Novel("Great Expectations", 1861, author)

  val counter = new Counter
  counter.inc.print

  counter.inc.inc.inc.print

  counter.inc(10).print
}

// constructor
class Person(name: String, val age: Int = 0) {
  // class parameters are NOT FIELDS

  //body

  val x = 2
  println(1 + 3) // will be executed first while object creation

  // method
  def greet(name: String): Unit = println(s"${this.name} says: Hi, $name")

  // overloading
  def greet(): Unit = println(s"Hi, I am $name")

  // multiple constructors
  def this(name: String) = this(name, 0)

  def this() = this("John Doe")
}

/*
* Novel and a writer
*
* Writer : first name, surname, year
* - method fullname
*
* Novel: name, year of release, author
* - authorAge
* - isWrittenBy(author)
* - copy(new year of release) = new Instance of Novel
*
* */
class Writer(firstName: String, surname: String, val year: Int) {
  def fullName: String = firstName + " " + surname
}

class Novel(name: String, yearOfRelease: Int, author: Writer) {
  def authorAge(): Int = yearOfRelease - author.year

  def isWrittenBy(author: Writer): Boolean = author.equals(author)

  def copy(newYear: Int): Novel = new Novel(name, newYear, author)
}

/*
* Counter Class
* - receives an int value
* - method current count
* - method to increment/decrement => new Counter
* - overload inc/dec to receive an amount
* */

class Counter(val count: Int = 0) {
  def inc = {
    println("incrementing")
    new Counter(count + 1)
  }

  def dec = {
    println("decrementing")
    new Counter(count - 1)
  }

  def inc(n: Int): Counter = {
    if (n <= 0) this
    else inc.inc(n - 1)
  }

  def dec(n: Int): Counter = {
    if (n <= 0) this
    else dec.dec(n - 1)
  }

  def print = println(count)
}