package lectures.part1basics

object Exceptions extends App {


  val x: String = null
  // println(x.length)
  // this ^^will crash with a NPE

  // 1. throwing exceptions

  //val aWeirdValue: String = throw new NullPointerException

  // throwable classes extend the Thwrowable class
  // Exception and Error are the major Throwable subtypes

  // 2. how to catch exceptions

  def getInt(withExceptions: Boolean): Int = {
    if (withExceptions) throw new RuntimeException("No int for you!")
    else 42
  }

  val potentialFail = try {
    getInt(true)
  } catch {
    case e: RuntimeException => println("caught a Runtime Exception")
  } finally {
    // code that will executed no matter what
    // optional
    // does not influence the return type of this expression
    // use finally only for side-effects
    println("finally")
  }
  println(potentialFail)

  // 3. how to define your own exception
  class MyException extends Exception

  val myException = new MyException
  //throw myException

  /*
  * 1. Crash your program with an OutOfMemoryError
  * 2. Crash with StackOverFlow Error
  * 3. PocketCalculator
  *    - add(x,y)
  *    - subtract(x,y)
  *    - multiply(x,y)
  *    - divide(x,y)
  *
  *     Throw:
  *     - OverflowException if add(x,y) exceeds Int.MAX_VALUE
  *     - UnderflowException if subtract(x,y) exceeds Int.MIN_VALUE
  *     - MathCalculationException for division by 0
  *     */


  // 1- Crash your program with an OutOfMemoryError
  // val array = Array.ofDim[Int](Int.MaxValue)

  // 2- Crash with StackOverFlow Error
  // def infinite: Int = 1 + infinite
  // val noLimite = infinite
  class OverflowException extends RuntimeException

  class UnderFlowException extends RuntimeException

  class MathCalculationException extends RuntimeException("Division By 0")

  object PocketCalculator {
    def add(x: Int, y: Int): Int = {
      val result = x + y
      if (x > 0 && y > 0 && result < 0) throw new OverflowException
      else if (x < 0 && y < 0 && result > 0) throw new UnderFlowException
      else result
    }

    def subtract(x: Int, y: Int): Int = {
      val result = x - y
      if (x > 0 && y < 0 && result < 0) throw new OverflowException
      else if (x < 0 && y > 0 && result > 0) throw new UnderFlowException
      else result
    }

    def divide(x: Int, y: Int): Int =
      if (y == 0) throw new MathCalculationException else x / y

    def multiply(x: Int, y: Int): Int =
      val result = x * y
      if (x > 0 && y > 0 && result < 0) throw new OverflowException
      else if (x < 0 && y < 0 && result < 0) throw new OverflowException
      else if (x > 0 && y < 0 && result > 0) throw new UnderFlowException
      else if (x < 0 && y > 0 && result > 0) throw new UnderFlowException
      else result
  }

  println(PocketCalculator.add(Int.MaxValue, 10))
  println(PocketCalculator.divide(2, 0))
}
