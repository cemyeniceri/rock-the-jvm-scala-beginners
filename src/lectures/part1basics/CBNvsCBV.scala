package lectures.part1basics

object CBNvsCBV extends App {
  def calledByValue(x: Long): Unit = {
    println("by Value : " + x)
    println("by Value : " + x)
  }

  def calledByName(x: => Long): Unit = {
    println("by Name : " + x)
    println("by Name : " + x)
  }

  calledByValue(System.nanoTime())
  calledByName(System.nanoTime())
}
