package lectures.part2oop

object Inheritance extends App {

  // single class inheritance
  class Animal {
    val creatureType = "wild"

    private def eatPrivate = println("nomnomnom")

    protected def eatProtected = println("nomnomnom")

    def eatPublic = println("nomnomnom")
  }

  class Cat extends Animal {
    def crunch = {
      // cat.eatProtected  -- can be called protected methods in subclasses
      eatProtected
      println("crunch crunch")
    }
  }

  val cat = new Cat
  cat.eatPublic
  // can not be called
  // println(cat.eatPrivate)   -- can not be called private methods
  // println(cat.eatProtected)   -- can not be called protected methods
  cat.crunch

  class Person(name: String, age: Int) {
    def this(name: String) = this(name, 0)
  }

  class Adult(name: String, age: Int, idCard: String) extends Person(name, age)

  class Child(name: String, age: Int, idCard: String) extends Person(name)

  class Dog(override val creatureType :String) extends Animal {
    override def eatProtected: Unit = println("crunch crunch")
  }

  class Bird extends Animal {
    override val creatureType = "domestic"

    override def eatProtected: Unit = println("crunch crunch crunch")
  }

  val dog = new Dog("K9")
  dog.eatProtected
  println(dog.creatureType)

  // preventing overrides
  // 1- use final on member
  // 2- use final the class
  // 3- use sealed the class => extend classes in THIS FILE, prevent extension in other files
}
