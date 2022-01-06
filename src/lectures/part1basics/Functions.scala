package lectures.part1basics

object Functions extends App {

  def aFunction(a: String, b: Int): String = {
    a + " " + b
  }

  println(aFunction("hello", 3))

  def aParameterlessFunction(): Int = 43

  println(aParameterlessFunction())


  def aRepeatedFunction(aString: String, n: Int): String = {
    if (n == 1) aString
    else aString + aRepeatedFunction(aString, n - 1)
  }

  println(aRepeatedFunction("test", 4))

  // WHEN YOU NEED LOOPS, USE RECURSION!!

  def aFunctionWithSideEffects(aString: String): Unit = println(aString)

  def aBigFunction(n: Int): Int = {
    def aSmallerFunction(a: Int, b: Int): Int = a + b

    aSmallerFunction(n, n - 1)
  }

  /**
   * 1-) A greeting function (name, age) => "Hi, my name is $name and I am $age years old."
   * 2-) Factorial function 1 * 2 * 3 * 4.. * n
   * 3-) A Fibonacci Function
   * f(1) = 1
   * f(2) = 1
   * f(n) = f(n-1) + f(n-2)
   *
   * 4-) Test if a number is a prime.
   *
   * */

  def aGreetingFunction(name: String, age: Int): String = {
    s"Hi, my name is $name and I am $age years old."
  }

  println(aGreetingFunction("cem", 31))

  def factorialFunction(n: Int): Int = {
    if (n <= 0) 1
    else n * factorialFunction(n - 1)
  }

  println(factorialFunction(5))

  def fibonacciFunction(n: Int): Int = {
    if (n < 3) 1
    else fibonacciFunction(n - 2) + fibonacciFunction(n - 1)
  }

  println(fibonacciFunction(8))


  def isPrime(n: Int): Boolean = {

    def isPrimeUtil(t: Int): Boolean = {
      if (t <= 1) true
      else n % t != 0 && isPrimeUtil(t - 1)
    }

    isPrimeUtil(n / 2)
  }
}
