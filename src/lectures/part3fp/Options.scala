package lectures.part3fp

import scala.util.Random

object Options extends App {

  val myFirstOpt: Option[Int] = Some(3)
  val noOpt: Option[Int] = None

  println(myFirstOpt)
  println(noOpt)

  // to deal with unsafe APIs
  def unSafeApi(): String = null

  // val result = Some(null) !!!!! Wrong!!!!
  val result = Option(unSafeApi())
  println(result)

  // chained methods
  def backUpMethod(): String = "A valid result!"

  val chainedResult = Option(unSafeApi()).orElse(Option(backUpMethod()))

  // DESIGN unsafe APIs
  def betterUnsafeApi(): Option[String] = None

  def betterBackUpMethod(): Option[String] = Some("A valid result!")

  val betterChainedResult = betterUnsafeApi() orElse betterBackUpMethod()
  println(betterChainedResult)

  // functions on Option
  println(myFirstOpt.isEmpty)
  println(noOpt.isEmpty)
  println(myFirstOpt.get) // UNSAFE - DO NOT USE IT IF POSSIBLE

  // map, flatMap, filter
  println(myFirstOpt.map(_ * 2))
  println(myFirstOpt.filter(_ > 2))
  println(myFirstOpt.filter(_ > 20))
  println(myFirstOpt.flatMap(x => Option(x * 3)))

  // for-comprehensions

  /**
   * Exercise:
   *
   * */

  val config: Map[String, String] = Map(
    // fetched from somewhere else
    "host" -> "1234.1234.12",
    "port" -> "80"
  )

  class Connection {
    def connect = "Connected" // connect to some server
  }

  object Connection {
    private val random = new Random(System.nanoTime())

    def apply(host: String, port: String): Option[Connection] = {
      if (random.nextBoolean()) Some(new Connection)
      else None
    }
  }
}
