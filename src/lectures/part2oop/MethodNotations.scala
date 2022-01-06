package lectures.part2oop

import scala.language.postfixOps

object MethodNotations extends App {
  class Person(val name: String, favoriteMovie: String, val age: Int = 0) {

    def likes(movie: String): Boolean = movie == favoriteMovie

    def +(person: Person): String = s"${this.name} is hanging out with ${person.name}"

    def unary_! : String = s"$name, what the heck?!"

    def isAlive: Boolean = true

    def apply(): String = s"Hi, my name is $name and I like $favoriteMovie"

    def apply(n: Int): String = s"$name, watched the $favoriteMovie $n times"

    def +(nickName: String): Person = new Person(s"$name ($nickName)", favoriteMovie)

    def unary_+ : Person = new Person(name, favoriteMovie, age + 1)

    def learns(course: String): String = s"$name learns $course"

    def learnScala: String = this learns "Scala"
  }

  val mary = new Person("Mary", "Inception")
  print(mary.likes("Inception"))
  print(mary likes "Inception") // equivalent
  // infix notation = operator notation (syntactic sugar)

  // "operators" in Scala
  val tom = new Person("Tom", "Fight Club")
  println(mary + tom)
  println(mary.+(tom))

  println(1 + 2)
  println(1.+(2))

  // ALL OPERATORS ARE METHODS
  // Akka Actors have ? !

  // prefix notation

  val x = -1 // equivalent to 1.unary_-
  val y = 1.unary_-
  // unary_ prefix only works with - + ~ !

  println(!mary)
  println(mary.unary_!)

  // postfix notation
  println(mary.isAlive)
  println(mary isAlive)

  // apply
  println(mary.apply())
  println(mary()) // equivalent

  println(mary learns "Java")
  println(mary learnScala)

  println(mary.apply(2))
  println(mary(4))

  /**
   * 1- Overload the + operator
   * mary + "the rockstar" => new Person "Mary (the rockstar)"
   *
   * 2- Add an age to the Person class
   * Add a unary + operator => new Person with the age + 1
   * +mary => mary with the age incremented
   *
   * 3- Add a "learns" method in the Person class => "Mary learns Scala"
   * Add a learnScala method, calls learns method with "Scala"
   * Use it in postfix notation
   *
   * 4- Overload the apply method
   * mary.apply(2) => "Mary watched the Inception 2 times"
   * */

  println(mary + "the Rockstar")

  println((+(+mary)).age)

  println(mary learns "Java")
  println(mary learnScala)

  println(mary.apply(2))
  println(mary(4))
}
