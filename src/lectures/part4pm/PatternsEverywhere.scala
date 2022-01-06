package lectures.part4pm

object PatternsEverywhere extends App {

  // big idea #1
  try {
    // code
  } catch {
    case e: RuntimeException => "runtime"
    case npe: NullPointerException => "npe"
    case _ => "sth else"
  }

  // catches are actually MATCHES

  /*  try {
      // code
    } catch(e) {
      e match {
        case e: RuntimeException => "runtime"
        case npe: NullPointerException => "npe"
        case _ => "sth else"
      }
    }*/

  // big idea #2
  val list = List(1, 2, 3, 4)
  val evenOnes = for {
    x <- list if x % 2 == 0
  } yield x * 10

  // generators are also based on PATTERN MATCHING
  val tuples = List((1, 2), (3, 4))
  val filterTuples = for {
    (first, second) <- tuples
  } yield first * second
  // case classes, :: operators, ..

  // big idea #3
  val tuple = (1, 2, 3)
  val (a, b, c) = tuple
  println(b)
  // multiple value definitions baseed on PM
  // ALL THE POWER
  val head :: tail = list
  println(head)
  println(tail)

  // big idea #4 - NEW
  // partial function literal
  val mappedList = list.map {
    case v if v % 2 == 0 => v + "is even"
    case 1 => "the even"
    case _ => "sth else"
  }

  // equivalent to
  val mappedList2 = list.map { x =>
    x match {
      case v if v % 2 == 0 => v + "is even"
      case 1 => "the even"
      case _ => "sth else"
    }
  }
}
