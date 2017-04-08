package stackoverflow

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{BeforeAndAfterAll, FunSuite}

@RunWith(classOf[JUnitRunner])
class StackOverflowSuite extends FunSuite with BeforeAndAfterAll {

  def initializeStackOverflow(): Boolean =
    try {
      StackOverflow
      true
    } catch {
      case ex: Throwable =>
        println(ex.getMessage)
        ex.printStackTrace()
        false
    }

  override def afterAll(): Unit = {
    assert(initializeStackOverflow(), " -- did you fill in all the values in StackOverflow (conf, sc)?")
    import StackOverflow._
    sc.stop()
  }

    lazy val testObject = new StackOverflow {
      override val langs =
        List(
          "JavaScript", "Java", "PHP", "Python", "C#", "C++", "Ruby", "CSS",
          "Objective-C", "Perl", "Scala", "Haskell", "MATLAB", "Clojure", "Groovy")

      override def langSpread = 50000

      override def kmeansKernels = 45

      override def kmeansEta: Double = 20.0D

      override def kmeansMaxIterations = 120
    }

  val rawPosts = List(
    Posting(1, 345, None, None, 140, Some("Scala")),
    Posting(2, 346, None, Some(345), 67, Some("Scala")),
    Posting(2, 347, None, Some(345), 55, Some("Scala"))
  )

  test("testObject can be instantiated")  {
    val instantiatable = try {
      testObject
      true
    } catch {
      case _: Throwable => false
    }
    assert(instantiatable, "Can't instantiate a StackOverflow object")
  }

  test("test groupedPostings") {
    assert(initializeStackOverflow(), " -- did you fill in all the values in StackOverflow (conf, sc)?")
    import StackOverflow._
    val rdd = sc.parallelize(rawPosts)
    val groupped = groupedPostings(rdd).map(_._2).flatMap(el => el.map(_._2))
    assert(groupped.count() === 2)
  }

  test("test scoredPostings") {
    assert(initializeStackOverflow(), " -- did you fill in all the values in StackOverflow (conf, sc)?")
    import StackOverflow._
    val rdd = sc.parallelize(rawPosts)
    val groupped = groupedPostings(rdd)
    val scored = scoredPostings(groupped)
    assert(scored.map(_._2).collect()(0) === 67)
  }

  test("test vectorPostings") {
    assert(initializeStackOverflow(), " -- did you fill in all the values in StackOverflow (conf, sc)?")
    import StackOverflow._
    val rdd = sc.parallelize(rawPosts)
    val groupped = groupedPostings(rdd)
    val scored = scoredPostings(groupped)
    val vectored = vectorPostings(scored)
    assert(vectored.collect() === Array((500000, 67)))
  }

}
