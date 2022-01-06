package lectures.part3fp

object TuplesAndMaps extends App {
  // tuples = finite ordered "lists"
  // val aTuple = new Tuple2(2, "hello, scala")
  // val aTuple = Tuple2(2, "hello, scala")
  val aTuple = (2, "hello-scala")

  println(aTuple._1)
  println(aTuple._2)
  println(aTuple.swap)
  println(aTuple.copy(_2 = "goodbye-java"))

  // maps - keys => values
  val aMap: Map[String, Int] = Map()
  val aPhoneBook = Map(("Cem", 123456), "Tolga" -> 654321).withDefaultValue(-1) // a -> b is syntactic sugar of (a, b)
  println(aPhoneBook)

  // map ops
  println(aPhoneBook.contains("Cem"))
  println(aPhoneBook("Cem")) // apply("Cem") means get it!
  println(aPhoneBook("Non-Exist")) // default Value

  // add a pairing
  val newPairing = "Mary" -> 678
  val newPhoneBook = aPhoneBook + newPairing
  println(newPhoneBook)

  // functionals on maps
  // map, flatMap, filter
  println(newPhoneBook.map(pair => pair._1.toLowerCase -> pair._2))

  // filterKeys
  println(newPhoneBook.view.filterKeys(x => x.startsWith("C")).toMap)

  // mapValues
  println(newPhoneBook.view.mapValues(number => number * 10).toMap)
  println(newPhoneBook.view.mapValues(number => "0245-" + number).toMap)

  // conversion to other collections
  println(newPhoneBook.toList)
  println(List(("Cem", 1234), ("Tolg", 4321)).toMap)
  val names = List("Bob", "James", "Angela", "Mary", "Daniel", "Jim")
  println(names.groupBy(x => x.charAt(0)))

  /**
   * 1- Whot would happen ıf I had two original entries "Jim" -> 555 and "JIM" -> 900 and make keys lowercase ?
   * 2- Overly simplified social network based on maps
   * Person = String
   *  - add a person to the network
   *  - remove
   *  - friend (mutual)
   *  - unfriend
   *
   *  - number of friends of a person
   *  - person with most friends
   *  - how many people have NO friends
   *  - if there is a social connection between two people (direct or not)
   * */

  // 1
  val jimMaps = Map("Jim" -> 555, "JIM" -> 900)
  println(jimMaps.map(t => t._1.toLowerCase -> t._2))

  // 2
  def add(network: Map[String, Set[String]], person: String): Map[String, Set[String]] = {
    network + (person -> Set())
  }

  def remove(network: Map[String, Set[String]], person: String): Map[String, Set[String]] = {
    def removeAux(friends: Set[String], accNetwork: Map[String, Set[String]]): Map[String, Set[String]] = {
      if (friends.isEmpty) accNetwork
      else removeAux(friends.tail, unfriend(accNetwork, friends.head, person))
    }

    val unfriendedNetworks = removeAux(network(person), network)
    unfriendedNetworks - person
  }

  def friend(network: Map[String, Set[String]], person1: String, person2: String): Map[String, Set[String]] = {
    val friendsOfPerson1 = network(person1)
    val friendsOfPerson2 = network(person2)

    network + (person1 -> (friendsOfPerson1 + person2)) + (person2 -> (friendsOfPerson2 + person1))
  }

  def unfriend(network: Map[String, Set[String]], person1: String, person2: String): Map[String, Set[String]] = {
    network + (person1 -> (network(person1) - person2)) + (person2 -> (network(person2) - person1))
  }

  def numberOfFriends(network: Map[String, Set[String]], person: String): Int = {
    if (network.contains(person)) network(person).size else 0
  }

  def mostFriends(network: Map[String, Set[String]]): (String, Int) = {
    val mostFriendPair = network.maxBy(t => t._2.size)
    mostFriendPair._1 -> mostFriendPair._2.size
  }

  def numberOfPeopleHaveNoFriends(network: Map[String, Set[String]]): (Int, String) = {
    val value = network.filter(t => t._2.isEmpty).map(t => t._1)
    value.size -> (value.mkString(","))
  }

  val emptyNetwork: Map[String, Set[String]] = Map()
  val network = add(add(add(emptyNetwork, "Cem"), "Mary"), "Merve")
  println(network)
  val networkOfCem = friend(friend(network, "Cem", "Mary"), "Cem", "Merve")
  println(friend(friend(network, "Cem", "Mary"), "Cem", "Merve"))
  println(unfriend(friend(network, "Cem", "Mary"), "Cem", "Merve"))
  println(unfriend(friend(network, "Cem", "Mary"), "Cem", "Mary"))
  println(remove(friend(network, "Cem", "Mary"), "Mary"))
  println(numberOfFriends(networkOfCem, "Cem"))
  println(mostFriends(networkOfCem))
  println(numberOfPeopleHaveNoFriends(network))
}
