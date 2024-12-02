import com.sun.javafx.css.Combinator

import java.io.File
import scala.io.Source

/**
 * Count number of elements
 * Get the first element
 * Get the last element
 * Get the first 5 elements
 * Get the last 5 elements
 *
 * hint: use the following methods
 * head
 * last
 * size
 * take
 * tails
 */
val ls1 = List.range(0,10)
ls1.size
ls1.head
ls1.last
ls1.take(5)
ls1.takeRight(5)

/**
 * Double each number from the numList and return a flatten list
 * e.g.res4: List[Int] = List(2, 3, 4)
 *
 * Compare flatMap VS ls.map().flatten
 */
val numList = List(List(1,2), List(3));
numList.flatMap(_.map(x => x * 2))

/**
 * Sum List.range(1,11) in three ways
 * hint: sum, reduce, foldLeft
 *
 * Compare reduce and foldLeft
 * https://stackoverflow.com/questions/7764197/difference-between-foldleft-and-reduceleft-in-scala
 */
val ls2 = List.range(1,11)
ls2.sum
ls2.reduce((x,y) => x + y)
ls2.foldLeft(0)((x,y) => x + y)

/**
 * Practice Map and Optional
 *
 * Map question1:
 *
 * Compare get vs getOrElse (Scala Optional)
 * countryMap.get("Amy");
 * countryMap.getOrElse("Frank", "n/a");
 */
val countryMap = Map("Amy" -> "Canada", "Sam" -> "US", "Bob" -> "Canada")
countryMap.get("Amy")
countryMap.get("edward")
countryMap.getOrElse("edward", "n/a")

/**
 * Map question2:
 *
 * create a list of (name, country) tuples using `countryMap` and `names`
 * e.g. res2: List[(String, String)] = List((Amy,Canada), (Sam,US), (Eric,n/a), (Amy,Canada))
 */
val names = List("Amy", "Sam", "Eric", "Amy")
names.map(x => (x, countryMap.getOrElse(x, "n/a")))

/**
 * Map question3:
 *
 * count number of people by country. Use `n/a` if the name is not in the countryMap  using `countryMap` and `names`
 * e.g. res0: scala.collection.immutable.Map[String,Int] = Map(Canada -> 2, n/a -> 1, US -> 1)
 * hint: map(get_value_from_map) ; groupBy country; map to (country,count)
 */
names
  .map(x => (countryMap.getOrElse(x, "n/a")))
  .groupBy(identity)
  .mapValues(_.size)

/**
 * number each name in the list from 1 to n
 * e.g. res3: List[(Int, String)] = List((1,Amy), (2,Bob), (3,Chris))
 */
val names2 = List("Amy", "Bob", "Chris", "Dann")
List.range(1, names2.size+1)
  .map(x => (x,names2(x-1)))

/**
 * SQL questions1:
 *
 * read file lines into a list
 * lines: List[String] = List(id,name,city, 1,amy,toronto, 2,bob,calgary, 3,chris,toronto, 4,dann,montreal)
 */
val stream = getClass.getResourceAsStream("/employees.csv")
Source.fromInputStream(stream).getLines
  .map(_.split(","))
  .flatMap(_.dropRight(1))
  .toList

/**
 * SQL questions2:
 *
 * Convert lines to a list of employees
 * e.g. employees: List[Employee] = List(Employee(1,amy,toronto), Employee(2,bob,calgary), Employee(3,chris,toronto), Employee(4,dann,montreal))
 */
class Employee(val id: String, val name: String, val city: String, val age: String = null){
  override def toString: String = {
    if (age == null) {
      s"Employee($id, $name, $city)"
    } else {
      s"Employee($id, $name, $city, $age)"
    }
  }
}
val stream = getClass.getResourceAsStream("/employees.csv")
Source.fromInputStream(stream).getLines
  .drop(1)
  .map(_.split(","))
  .map(e => new Employee(e(0), e(1), e(2)))
  .toList

/**
 * SQL questions3:
 *
 * Implement the following SQL logic using functional programming
 * SELECT uppercase(city)
 * FROM employees
 *
 * result:
 * upperCity: List[Employee] = List(Employee(1,amy,TORONTO,20), Employee(2,bob,CALGARY,19), Employee(3,chris,TORONTO,20), Employee(4,dann,MONTREAL,21), Employee(5,eric,TORONTO,22))
 */
val stream = getClass.getResourceAsStream("/employees.csv")
Source.fromInputStream(stream).getLines
  .drop(1)
  .map(_.split(","))
  .map(e => new Employee(e(0), e(1).toUpperCase(), e(2), e(3)))
  .toList

/**
 * SQL questions4:
 *
 * Implement the following SQL logic using functional programming
 * SELECT uppercase(city)
 * FROM employees
 * WHERE city = 'toronto'
 *
 * result:
 * res5: List[Employee] = List(Employee(1,amy,TORONTO,20), Employee(3,chris,TORONTO,20), Employee(5,eric,TORONTO,22))
 */
val stream = getClass.getResourceAsStream("/employees.csv")
Source.fromInputStream(stream).getLines
  .drop(1)
  .map(_.split(","))
  .filter(_(2).equals("toronto"))
  .map(e => new Employee(e(0), e(1).toUpperCase(), e(2), e(3)))
  .toList


/**
 * SQL questions5:
 *
 * Implement the following SQL logic using functional programming
 *
 * SELECT uppercase(city), count(*)
 * FROM employees
 * GROUP BY city
 *
 * result:
 * cityNum: scala.collection.immutable.Map[String,Int] = Map(CALGARY -> 1, TORONTO -> 3, MONTREAL -> 1)
 */
val stream = getClass.getResourceAsStream("/employees.csv")
Source.fromInputStream(stream).getLines
  .drop(1)
  .map(_.split(","))
  .map(e => new Employee(e(0), e(1), e(2).toUpperCase(), e(3)))
  .toList
  .groupBy(_.city)
  .mapValues(_.size)

/**
 * SQL questions6:
 *
 * Implement the following SQL logic using functional programming
 *
 * SELECT uppercase(city), count(*)
 * FROM employees
 * GROUP BY city,age
 *
 * result:
 * res6: scala.collection.immutable.Map[(String, Int),Int] = Map((MONTREAL,21) -> 1, (CALGARY,19) -> 1, (TORONTO,20) -> 2, (TORONTO,22) -> 1)
 */
val stream = getClass.getResourceAsStream("/employees.csv")
Source.fromInputStream(stream).getLines
  .drop(1)
  .map(_.split(","))
  .map(e => new Employee(e(0), e(1), e(2).toUpperCase(), e(3)))
  .toList
  .groupBy(e => (e.city, e.age))
  .mapValues(_.size)