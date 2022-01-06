package lectures.part2oop

object Objects extends App {

  // SCALA DOES NOT HAVE CLASS-LEVEL FUNCTIONALITY ("static")
  object Person { // type + its only instance
    // "static" / "class" - level functionality
    val N_EYES = 2

    def canFly: Boolean = true

    // factory method
    def apply(mother: Person, father: Person): Person = new Person("Bobbie")
  }

  class Person(val name: String) {
    // instance-level functionality
  }

  // class Person + object Person => COMPANIONS
  println(Person.N_EYES)
  println(Person.canFly)

  // Scala Object = SINGLETON INSTANCE

  val person1 = Person
  val person2 = Person
  println(person1 == person2)

  val mary = new Person("mary")
  val john = new Person("john")

  print(mary == john)

  val bobbieWithApply = Person.apply(mary, john)
  val bobbieWithoutApply = Person(mary, john)

  // SCALA Applications = Scala object with
  // final def main(args: Array[String]): Unit
}
