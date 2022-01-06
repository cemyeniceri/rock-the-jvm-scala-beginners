package lectures.part2oop

object AbstractDataTypes extends App {

  // abstract
  abstract class Animal {
    val creatureType: String = "wild"

    def eat: Unit
  }

  class Dog extends Animal {
    override val creatureType: String = "Canine"

    override def eat: Unit = println("crunch crunch")
  }

  // traits
  trait Carnivore {
    def eat(animal: Animal): Unit

    val preferredMeal: String = "fresh meat"
  }

  trait ColdBlooded

  class Crocodile extends Animal with Carnivore with ColdBlooded {
    override val creatureType: String = "croc"

    def eat: Unit = println("nomnomnom") // override here optional

    override def eat(animal: Animal): Unit = println(s"I'am a croc and I'm eating ${animal.creatureType}")
  }

  val dog = new Dog
  val crocodile = new Crocodile
  crocodile eat dog

  // traits vs abstracts
  // 1- abstract classes can have abstract and non-abstract class members and methods
  // 2- traits can have abstract or non-abstract class members
  // 3- traits do not have constructor parameters (Scala3 can)
  // 4- multiple traits may be inherited by the same class
  // 5- traits <=> "behaviour", abstract class <=> "thing"
}
