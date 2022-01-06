package lectures.part4pm

import exercises.{Cons6, Empty6, HOFFunctionalList}

object AllThePatterns extends App {

  // 1- constants
  val x: Any = "Scala"
  val constants = x match {
    case 1 => "a number"
    case "Scala" => "The Scala"
    case true => "The Truth"
    case AllThePatterns => "A Singleton Object"
  }

  // 2- match anything
  // 2.1 wildcard
  val matchAnything = x match {
    case _ =>
  }

  // 2.2 variable
  val matchAVariable = x match {
    case something => s"I've found $something"
  }

  // 3- Tuples
  val aTuple = (1, 2)
  val matchATuple = aTuple match {
    case (1, 1) =>
    case (something, 2) => s"I've found $something"
  }

  val nestedTuple = (1, (2, 3))
  val matchANestedTuple = nestedTuple match {
    case (_, (2, v)) =>
  }
  // PMs can be nested!!!


  // 4- case classes - constructor pattern
  // PMs can be nested!!!
  val aList: HOFFunctionalList[Int] = Cons6(1, Cons6(2, Empty6))
  val matchAList = aList match {
    case Empty6 =>
    case Cons6(head, Cons6(subhead, subtail)) =>
  }

  // 5- list patterns
  val aStandardList = List(1, 2, 3, 4, 5)
  val matchAStandardList = aStandardList match {
    case List(1, _, _, _, _) => // extractor - advanced
    case List(1, _*) => // list of arbitrary length - advanced
    case 1 :: List(1) => // infix pattern
    case List(1, 2, 3, 4) :+ 42 => // infix pattern
  }

  // 6- type specifiers
  val unknown: Any = 2
  val unknownMatch = unknown match {
    case list: List[Int] => // explicit type specifiers
    case _ =>
  }

  // 7- name binding
  val nameBindingMatch = aList match {
    case nonEmptyList @ Cons6(_, _) => // name binding => use the name letter(here)
    case Cons6(1, rest @ Cons6(2, _)) => // name binding inside nested patterns
  }

  // 8- multi-patterns
  val mutliPattern = aList match {
    case Empty6 | Cons6(0, _) => // compound pattern (multi-pattern)
  }

  // 9- if guards
  val secondElementSpecial = aList match {
    case Cons6(_, Cons6(specialElement, _)) if specialElement % 2 == 0 =>
  }

  // ALL!
}
