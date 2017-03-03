package patmat

import org.scalatest.FunSuite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

import patmat.Huffman._

@RunWith(classOf[JUnitRunner])
class HuffmanSuite extends FunSuite {
	trait TestTrees {
		val t1 = Fork(Leaf('a',2), Leaf('b',3), List('a','b'), 5)
		val t2 = Fork(Fork(Leaf('a',2), Leaf('b',3), List('a','b'), 5), Leaf('d',4), List('a','b','d'), 9)
    val t2bis = makeCodeTree(makeCodeTree(Leaf('a',2), Leaf('b',3)),Leaf('d',4))
    val text1 = "babab"
    val tree1 = Fork(Leaf('a', 2), Leaf('b', 3), List('a', 'b'), 5)
    val text2 = "dbadbadbd"
    val tree2 = Fork(Fork(Leaf('a', 2), Leaf('b', 3), List('a', 'b'), 5), Leaf('d', 4), List('a', 'b', 'd'), 9)
    val text3 = "a"
    val tree3 = Leaf('a', 1)
    val text4 = "aaa"
    val tree4 = Leaf('a', 3)
    val text5 = "roland tritsch ralf christina"
    val times5 = List(Leaf('o',1), Leaf('d',1), Leaf('f',1), Leaf('l',2), Leaf('n',2), Leaf('s',2), Leaf('c',2), Leaf('h',2), Leaf('a',3), Leaf(' ',3), Leaf('t',3), Leaf('i',3), Leaf('r',4))
    val combine5 = List(Leaf('f',1), Leaf('l',2), Leaf('n',2), Leaf('s',2), Leaf('c',2), Leaf('h',2), Fork(Leaf('o',1), Leaf('d',1), List('o','d'), 2), Leaf('a',3), Leaf(' ',3), Leaf('t',3), Leaf('i',3), Leaf('r',4))
    val tree5 =
      Fork(
        Fork(
          Fork(
            Fork(
              Leaf('h',2),
              Fork(
                Leaf('f',1),
                Fork(
                  Leaf('o',1),Leaf('d',1),List('d', 'o'),2),
                List('d', 'f', 'o'),3),
              List('d', 'f', 'h', 'o'),5),
            Fork(
              Leaf('t',3),
              Leaf('i',3),
              List('i', 't'),6),
            List('d', 'f', 'h', 'i', 'o', 't'),11),
          Fork(
            Fork(
              Leaf('a',3),Leaf(' ',3),List(' ', 'a'),6),
            Fork(
              Fork(Leaf('s',2),Leaf('c',2),List('c', 's'),4),
              Fork(Leaf('l',2),Leaf('n',2),List('l', 'n'),4),
              List('c', 'l', 'n', 's'),8),
            List(' ', 'a', 'c', 'l', 'n', 's'),14),
          List(' ', 'a', 'c', 'd', 'f', 'h', 'i', 'l', 'n', 'o', 's', 't'),25),
        Leaf('r',4),
        List(' ', 'a', 'c', 'd', 'f', 'h', 'i', 'l', 'n', 'o', 'r', 's', 't'),29)
    val text5tritsch = "tritsch"
    val bits5tritsch = List(0, 0, 1, 0, 1, 0, 0, 1, 1, 0, 0, 1, 0, 0, 1, 1, 0, 0, 0, 1, 1, 0, 1, 0, 0, 0, 0)

	}

// FIXME add tests later
//  test("until: basic") {
//    new TestTrees {
//      assert(until(singleton, combine)(makeOrderedLeafList(times(string2Chars(text1)))) === List(tree1), "until(singleton, combine)(makeOrderedLeafList(times(string2Chars(text1)))) === List(tree1)")
//      assert(until(singleton, combine)(makeOrderedLeafList(times(string2Chars(text2)))) === List(tree2), "tree 2 failed")
//    }
//  }
//
//  test("makeCodeTree: basic") {
//    new TestTrees {
//      assert(createCodeTree(string2Chars(text1)) === tree1, "createCodeTree(string2Chars(text1)) === tree1")
//      assert(createCodeTree(string2Chars(text2)) === tree2)
//      assert(createCodeTree(string2Chars(text3)) === tree3)
//      assert(createCodeTree(string2Chars(text4)) === tree4)
//      assert(createCodeTree(string2Chars(text5)) === tree5)
//    }
//  }

  test("weight of a larger tree") {
    new TestTrees {
      assert(weight(t1) === 5)
    }
  }


  test("chars of a larger tree") {
    new TestTrees {
      assert(chars(t2) === List('a','b','d'))
    }
  }

  test("makeCodeTree on chars of a larger tree") {
    new TestTrees {
      assert(chars(t2bis) === List('a','b','d'))
    }
  }


  test("string2chars(\"hello, world\")") {
    assert(string2Chars("hello, world") === List('h', 'e', 'l', 'l', 'o', ',', ' ', 'w', 'o', 'r', 'l', 'd'))
  }


  test("makeOrderedLeafList for some frequency table") {
    assert(makeOrderedLeafList(List(('t', 2), ('e', 1), ('x', 3))) === List(Leaf('e',1), Leaf('t',2), Leaf('x',3)))
  }


  test("combine of some leaf list") {
    val leaflist = List(Leaf('e', 1), Leaf('t', 2), Leaf('x', 4))
    assert(combine(leaflist) === List(Fork(Leaf('e',1),Leaf('t',2),List('e', 't'),3), Leaf('x',4)))
  }


/*
  test("decode") {
    assert(decode(frenchCode, secret) === List())
  }
*/

  test("decode and encode a very short text should be identity") {
    new TestTrees {
      assert(decode(t1, encode(t1)("ab".toList)) === "ab".toList)
    }
  }

}
