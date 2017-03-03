package forcomp

import org.scalatest.FunSuite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

import Anagrams._

@RunWith(classOf[JUnitRunner])
class AnagramsSuite extends FunSuite {

  test("wordOccurrences: basic") {
//    intercept[java.lang.IllegalArgumentException] {
//      assert(wordOccurrences(" ") === List())
//    }
    assert(wordOccurrences("") === List())
    assert(wordOccurrences("a") === List(('a', 1)))
    assert(wordOccurrences("I") === List(('i', 1)))
    assert(wordOccurrences("abba") === List(('a', 2), ('b', 2)))
    assert(wordOccurrences("abcd") === List(('a', 1), ('b', 1), ('c', 1), ('d', 1)))
    assert(wordOccurrences("abcd".reverse) === List(('a', 1), ('b', 1), ('c', 1), ('d', 1)))
    assert(wordOccurrences("Robert") === List(('b', 1), ('e', 1), ('o', 1), ('r', 2), ('t', 1)))
    assert(wordOccurrences("Robert".reverse) === List(('b', 1), ('e', 1), ('o', 1), ('r', 2), ('t', 1)))
    assert(wordOccurrences("antidisestablishmentarianism") === List(('a', 4), ('b', 1), ('d', 1), ('e', 2), ('h', 1), ('i', 5), ('l', 1), ('m', 2), ('n', 3), ('r', 1), ('s', 4), ('t', 3)))
    assert(wordOccurrences("antidisestablishmentarianism".reverse) === List(('a', 4), ('b', 1), ('d', 1), ('e', 2), ('h', 1), ('i', 5), ('l', 1), ('m', 2), ('n', 3), ('r', 1), ('s', 4), ('t', 3)))
  }

  test("sentenceOccurrences: basic") {
    assert(sentenceOccurrences("".split(" ").toList) === List())
    assert(sentenceOccurrences("a a".split(" ").toList) === List(('a', 2)))
    assert(sentenceOccurrences("a a bb".split(" ").toList) === List(('a', 2), ('b', 2)))
    assert(sentenceOccurrences("a a bb".split(" ").toList.reverse) === List(('a', 2), ('b', 2)))
    assert(sentenceOccurrences("aaa bcd eee".split(" ").toList) === List(('a', 3), ('b', 1), ('c', 1), ('d', 1), ('e', 3)))
    assert(sentenceOccurrences("aaa bcd eee".split(" ").toList.reverse) === List(('a', 3), ('b', 1), ('c', 1), ('d', 1), ('e', 3)))
  }

  test("dictionary: histogram") {
    // Build (and print) histogram of word length.
    // Note: Seems (surprise!) there are no one letter words in the dictionary (e.g. no "a", "I", ...)
    val histogram = dictionary.groupBy(e => e.size).mapValues(_.size).toList.sortWith((thiz, thaz) => thiz._1 < thaz._1)
    // println(s"histogram: ${histogram.take(10)}")
    assert(histogram === List((2, 49), (3, 535), (4, 2235), (5, 4172), (6, 6171), (7, 7363), (8, 7073), (9, 6082), (10, 4592), (11, 3069), (12, 1880), (13, 1137), (14, 545), (15, 278), (16, 103), (17, 57), (18, 23), (19, 3), (20, 3), (21, 2), (22, 1), (28, 1)))
  }

  test("dictionary: longest word") {
    assert(dictionary.filter(_.size == 28) === List("antidisestablishmentarianism"))
  }

  test("dictionary: 2-letter words") {
    assert(dictionary.filter(_.size == 2) === List("ad", "ah", "Al", "am", "an", "as", "at", "ax", "be", "by", "De", "do", "Ed", "em", "en", "et", "ex", "Fe", "go", "ha", "he", "hi", "if", "in", "Io", "is", "it", "Jo", "Ku", "me", "mu", "my", "no", "nu", "of", "oh", "on", "or", "ox", "Oz", "pi", "Po", "re", "so", "to", "up", "us", "we", "Wu"))
  }

  test("dictionaryByOccurrences.get: basic") {
    assert(dictionaryByOccurrences.get(List()).map(_.toSet) === None)
    assert(dictionaryByOccurrences.get(List(('a', 1))).map(_.toSet) === None)
    assert(dictionaryByOccurrences.get(List(('i', 1))).map(_.toSet) === None)
    assert(dictionaryByOccurrences.get(List(('z', 3))).map(_.toSet) === None)
    assert(dictionaryByOccurrences.get(sentenceOccurrences("".split(" ").toList)).map(_.toSet) === None)
    assert(dictionaryByOccurrences.get(sentenceOccurrences(" ".split(" ").toList)).map(_.toSet) === None)
    assert(dictionaryByOccurrences.get(sentenceOccurrences("ied rr ma ".split(" ").toList)).map(_.toSet) === Some(Set("married", "admirer")))
    assert(dictionaryByOccurrences.get(sentenceOccurrences("aa bb".split(" ").toList)).map(_.toSet) === Some(Set("Abba")))
    assert(dictionaryByOccurrences.get(sentenceOccurrences("t a e".split(" ").toList)).map(_.toSet) === Some(Set("ate", "eat", "tea")))
  }

  test("word anagrams: basic") {
//    intercept[java.util.NoSuchElementException] {
//      assert(wordAnagrams("").toSet === Set())
//    }
//    intercept[java.util.NoSuchElementException] {
//      assert(wordAnagrams("xyz").toSet === Set())
//    }
//    intercept[java.util.NoSuchElementException] {
//      assert(wordAnagrams("I").toSet === Set("I"))
//    }
//    intercept[java.util.NoSuchElementException] {
//      assert(wordAnagrams("a").toSet === Set("a"))
//    }
    assert(wordAnagrams("eat").toSet === Set("ate", "eat", "tea"))
    assert(wordAnagrams("married").toSet === Set("married", "admirer"))
    assert(wordAnagrams("player").toSet === Set("parley", "pearly", "player", "replay"))
    assert(wordAnagrams("Heather").toSet === Set("heather"))
  }

  test("combinations: basic") {
    assert(combinations(List()) === List(List()))
    assert(combinations(sentenceOccurrences("uw".split(" ").toList)).toSet === List(
      List(),
      List(('w', 1)),
      List(('u', 1)),
      List(('u', 1), ('w', 1))
    ).toSet)
    assert(combinations(sentenceOccurrences("xxx".split(" ").toList)).toSet === List(
      List(),
      List(('x', 3)),
      List(('x', 2)),
      List(('x', 1))
    ).toSet)
    /*
    assert(combinations(sentenceOccurrences("333 22 1".split(" ").toList)).toSet === List(
      List((1, 1)),
      List((1, 1), (2, 1)),
      List((1, 1), (2, 1), (3, 1)),
      List((1, 1), (2, 1), (3, 2)),
      List((1, 1), (2, 1), (3, 3)),
      List((1, 1), (2, 2)),
      List((1, 1), (2, 2), (3, 1)),
      List((1, 1), (2, 2), (3, 2)),
      List((1, 1), (2, 2), (3, 3)),
      List((1, 1), (3, 1)),
      List((1, 1), (3, 2)),
      List((1, 1), (3, 3)),
      List((2, 1)),
      List((2, 1), (3, 1)),
      List((2, 1), (3, 2)),
      List((2, 1), (3, 3)),
      List((2, 2)),
      List((2, 2), (3, 1)),
      List((2, 2), (3, 2)),
      List((2, 2), (3, 3)),
      List((3, 1)),
      List((3, 2)),
      List((3, 3)),
      List()
    ).toSet)
 */
    assert(combinations(sentenceOccurrences("bb aa".split(" ").toList)).toSet === List(
      List(),
      List(('a', 1)),
      List(('a', 2)),
      List(('b', 1)),
      List(('a', 1), ('b', 1)),
      List(('a', 2), ('b', 1)),
      List(('b', 2)),
      List(('a', 1), ('b', 2)),
      List(('a', 2), ('b', 2))
    ).toSet)
  }

  test("subtract: basic") {
//    assert(subtract(List(('a', 1)), List(('a', 1))) === List())
//    intercept[java.lang.IllegalArgumentException] {
//      assert(subtract(List(('a', 1)), List(('x', 1))) === List())
//    }
//    intercept[java.lang.IllegalArgumentException] {
//      assert(subtract(List(('a', 1)), List(('a', 2))) === List())
//    }
    assert(subtract(List(('x', 3), ('y', 1), ('z', 3)), List(('x', 1), ('y', 1), ('z', 2))) === List(('x', 2), ('z', 1)))
    assert(subtract(List(('a', 1), ('d', 1), ('l', 1), ('r', 1)), List(('r', 1))) === List(('a', 1), ('d', 1), ('l', 1)))
  }
}
