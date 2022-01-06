package lectures.part1basics

import scala.annotation.tailrec

object Recursion extends App {

  def factorial(n: Int): Int =
    if (n <= 1) 1
    else {
      println("Computing factorial of " + n + " - I first need factorial of " + (n - 1))
      val result = n * factorial(n - 1)
      println("Computed factorial of " + n)
      result
    }

  println(factorial(10))

  def anotherFactorial(n: Int): BigInt = {
    @tailrec
    def factHelper(x: Int, acc: BigInt): BigInt = {
      if (x <= 1) acc
      else factHelper(x - 1, x * acc) // TAIL_RECURSION = use recursive call as the LAST expression
    }

    factHelper(n, 1)
  }

  println(anotherFactorial(5000))

  /**
   * anotherFactorial(10) = factHelper(10, 1)
   * = factHelper(9, 10 * 1)
   * = factHelper(8, 9 * 10 * 1)
   * = factHelper(7, 8 * 9 * 10 * 1)
   * ...
   * = factHelper(1, 2 * 3 * 4 * 5 * 6 * 7 * 8 * 9 * 10 * 1)
   * */


  /**
   * 1-) Concatenate a string n times
   * 2-) isPrime Function tail_recursive
   * 3-) fibonacci Function tail_recursive
   * */

  def concatenateTailRec(str: String, n: Int): String = {
    @tailrec
    def concatenateHelper(x: Int, accStr: String): String = {
      if (x < 1) accStr
      else concatenateHelper(x - 1, accStr + str)
    }

    concatenateHelper(n, "")
  }

  println(concatenateTailRec("cem", 3))

  def isPrimeTailRec(n: Int): Boolean = {
    def isPrimeHelper(x: Int, tempResult: Boolean): Boolean = {
      if (!tempResult || x <= 1) tempResult
      else isPrimeHelper(x - 1, (n % x) != 0)
    }

    isPrimeHelper(n / 2, true)
  }

  println(isPrimeTailRec(2003))
  println(isPrimeTailRec(629))

  def fibonacciTailRec(n: Int): Int = {
    def fibonacciHelper(x: Int, acc1: Int, acc2: Int): Int = {
      if (x <= 1) acc1 + acc2
      else fibonacciHelper(x - 1, acc2, acc1 + acc2)
    }

    fibonacciHelper(n - 2, 1, 1)
  }

  println(fibonacciTailRec(3))
  println(fibonacciTailRec(4))
  println(fibonacciTailRec(5))
  println(fibonacciTailRec(6))
  println(fibonacciTailRec(7))
}
