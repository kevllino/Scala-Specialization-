package funsets

import org.scalatest.FunSuite


import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

/**
 * This class is a test suite for the methods in object FunSets. To run
 * the test suite, you can either:
 *  - run the "test" command in the SBT console
 *  - right-click the file in eclipse and chose "Run As" - "JUnit Test"
 */
@RunWith(classOf[JUnitRunner])
class FunSetSuite extends FunSuite {

  /**
   * Link to the scaladoc - very clear and detailed tutorial of FunSuite
   *
   * http://doc.scalatest.org/1.9.1/index.html#org.scalatest.FunSuite
   *
   * Operators
   *  - test
   *  - ignore
   *  - pending
   */

  /**
   * Tests are written using the "test" operator and the "assert" method.
   */
  // test("string take") {
  //   val message = "hello, world"
  //   assert(message.take(5) == "hello")
  // }

  /**
   * For ScalaTest tests, there exists a special equality operator "===" that
   * can be used inside "assert". If the assertion fails, the two values will
   * be printed in the error message. Otherwise, when using "==", the test
   * error message will only say "assertion failed", without showing the values.
   *
   * Try it out! Change the values so that the assertion fails, and look at the
   * error message.
   */
  // test("adding ints") {
  //   assert(1 + 2 === 3)
  // }


  import FunSets._

  test("contains is implemented") {
    assert(contains(x => true, 100))
  }

  /**
   * When writing tests, one would often like to re-use certain values for multiple
   * tests. For instance, we would like to create an Int-set and have multiple test
   * about it.
   *
   * Instead of copy-pasting the code for creating the set into every test, we can
   * store it in the test class using a val:
   *
   *   val s1 = singletonSet(1)
   *
   * However, what happens if the method "singletonSet" has a bug and crashes? Then
   * the test methods are not even executed, because creating an instance of the
   * test class fails!
   *
   * Therefore, we put the shared values into a separate trait (traits are like
   * abstract classes), and create an instance inside each test method.
   *
   */

  trait TestSets {
    val s1 = singletonSet(1)
    val s2 = singletonSet(2)
    val s3 = singletonSet(3)
    val s4: Set = (x: Int) => x> -5 && x < 5
    val s5: Set = (x: Int) => x > -3 && x < 3
    val negativeSet: Set = (x: Int) => x < 0
    val positiveSet: Set = (x: Int) => x > 0
    val otherSet1: Set = (x: Int) => x > -200 && x < -5
    val otherSet2: Set = (x: Int) => x >= -1 && x < 200
    val otherSet3: Set = (x: Int) => x >= 0 && x < 200
    val othar: Set = (x: Int) => x >= 1 && x <= 3
  }

  /**
   * This test is currently disabled (by using "ignore") because the method
   * "singletonSet" is not yet implemented and the test would fail.
   *
   * Once you finish your implementation of "singletonSet", exchange the
   * function "ignore" by "test".
   */
  test("singletonSet(1) contains 1") {

    /**
     * We create a new instance of the "TestSets" trait, this gives us access
     * to the values "s1" to "s3".
     */
    new TestSets {
      /**
       * The string argument of "assert" is a message that is printed in case
       * the test fails. This helps identifying which assertion failed.
       */
      assert(contains(s1, 1), "Singleton")
    }
  }

  test("union contains all elements of each set") {
    new TestSets {
      val u1 = union(s1, s2)
      val u2 = union(u1, s3)
      val s = union(s1, s2)
      val ex2 = union(s3, s4)
      assert(contains(s, 1), "Union 1")
      assert(contains(s, 2), "Union 2")
      assert(!contains(s, 3), "Union 3")
      assert(contains(ex2, -4), "Union 4")
      assert(!contains(ex2, 7), "Union 5")
      assert(contains(u2, 1) && contains(u2, 2) && contains(u2, 3), "Union All")
    }
  }

  test("intersection contains elements common to each set"){
    new TestSets {
      val s = intersect(s1, s2)
      val ex2 = intersect(s4, s5)
      assert(!contains(s, 1), "Intersect 1")
      assert(contains(ex2, -2), "Intersect 2")
      assert(!contains(ex2, 4), "Intersect 3")
    }
  }

  test("diff(s, t) contains elements of set s which are not contained in set t "){
    new TestSets {
      val s = diff(s3, s2)
      val ex2 = diff(s4, s5)

      assert(contains(s, 3), "Diff 1")
      assert(!contains(s, 2), "Diff 2")
      assert(contains(ex2, -4), "Diff 3")
      assert(!contains(ex2, 2), "Diff 4")
    }
  }

  test("filter negativeSet with values comprised between -7 and -3 "){
    new TestSets {
      val s = filter(negativeSet, (x: Int) => x >= -7 && x <= -3)
      assert(contains(s, -3), "Filter 1")
      assert(!contains(s, -98), "Filter 2")

    }
  }

  test("forall should return whether all bounded integers within `s` satisfy `p`."){
    new TestSets {
      assert(forall(otherSet1, (x: Int) => x < 0 ) === true, "Forall hc")
      assert(forall(otherSet1, (x: Int) => x %2 == 0 ) === false, "Forall sc")

    }
  }

  test("exists an element in s that satisfies the predicate") {
    new TestSets {
      val u1 = union(s1, s2)
      val u2 = union(u1, s3)
      assert(exists(u1, e => e >= 1), "Exists 1")
      assert(!exists(u1, e => e > 3), "Exists 2")
    }
  }

  test("exists returns whether there exists a bounded integer within `s`that satisfies `p`."){
    new TestSets {
      assert(exists(otherSet2, (x: Int) => x < 0), "Exists hc")
      assert(!exists(otherSet3, (x: Int) => x < 0), "Exists sc")

    }
    }
  test("assess whether an element belongs to new set generated by the map function"){
    new TestSets {
      assert(contains(map(negativeSet, (x: Int) => -x), 2), "Map hc")
      assert(!contains(map(positiveSet, (x: Int) => x + 10), -11), "Map sc")
      assert(contains(map(othar, (x: Int) => x * 2), 6))
    }
  }

  test("map a set with a function") {
    new TestSets {
      val u1 = union(s1, s2)
      val u2 = union(u1, s3)
      assert(forall(map(u1, e => e + 3), e => e >= 4 && e <= 6), "Map 1")
    }
  }

}
