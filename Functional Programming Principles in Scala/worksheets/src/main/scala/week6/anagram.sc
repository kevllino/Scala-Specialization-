type Word = String

type Sentence = List[Word]

type Occurrences = List[(Char, Int)]

def wordOccurrences(w: Word): Occurrences =
  w.toLowerCase.groupBy(c => c).map(el => (el._1, el._2.length)).toList.sorted

val word1: Word = "bonjour"
val word2: Word = "gobaga"
wordOccurrences(word1)

def sentenceOccurrences(s: Sentence): Occurrences = wordOccurrences(s mkString)

val sentence1: Sentence = List(word1, word2)
sentenceOccurrences(sentence1)

val dico: List[Word] = List("en", "as", "my", "man", "yes", "men", "say", "sane", "Sean")


lazy val dictionaryByOccurrences: Map[Occurrences, List[Word]] =
  dico.groupBy(wordOccurrences(_)) withDefaultValue (List())

def wordAnagrams(word: Word): List[Word] =
    dictionaryByOccurrences(wordOccurrences(word))

for (i <- 1 to 2) yield for (j <- 1 to 2) yield (i, j)

def combinations(occurrences: Occurrences): List[Occurrences] =
  (occurrences foldRight List[Occurrences](List())){
    case ((char, freq), acc) =>
      acc ++ {
        for {
          it <- 1 to freq
          combi <- acc
        }
          yield  (char, it) :: combi
      }
  }

( 1 to 9 foldRight (0))((x, y) => {println(x + " "+ y); x + y})
( 1 to 9 foldLeft (0))((x, y) => {println(x + " "+ y); x + y})

/*def combinations(occurrences: Occurrences): Listfe[Occurrences] =
  occurrences match {
    case e if e.isEmpty => List(List())
    case (char, freq) :: Nil => {for (it <- 1 to freq) yield (char, it) :: List() }.toList
    case (char, freq) :: rest =>  {
      for {
        it <- 1 to freq
        occu <- combinations(rest)
      } yield  (char, it) :: occu
    }.toList
  }*/


/*  if(occurrences.isEmpty) List(List())
  else  {
      for {
        freq <- 1 to occurrences.head._2
        rest <- List() :: combinations(occurrences.tail)
      } yield (occurrences.head._1, freq) :: rest
  }.toList*/

val occ1: Occurrences = List(('a', 2), ('b', 2))

combinations(occ1)



/*def encode(number: String): Set[List[String]] =
  if(number.isEmpty) Set(List())
  else {
    for {
      split <- 1 to number.length
      word <- wordsForNum(number take split)
      rest <- encode(number drop split)
    } yield word :: rest
  }.toSe*/

val x = List(('a', 1), ('d', 1), ('l', 1), ('r', 1))
val y = List(('r', 1))

def subtract(x: Occurrences, y: Occurrences): List[(Char, Int)] =
  (x foldLeft (List[(Char, Int)]())) {
    case (acc, (char, freq)) =>
      val ymap = y.toMap withDefaultValue 0
      (char, freq - ymap(char))  :: acc filter(_._2 > 0)
  }.sortBy(_._1)

subtract(x, y)

def combinations2(words: List[Word]): List[Sentence] =
  (words foldRight List[Sentence](List())){
    case (word, acc) =>
      acc ++ {
        for {
          w <- acc
          if (word + (w mkString "")).length < 7
        }
          yield  word :: w
      }
  }



def sentenceAnagrams(sentence: Sentence): List[Sentence] = {
  def computeAnagram(occurrences: Occurrences): List[Sentence] = {
    if (occurrences.isEmpty) List[Sentence](List())
    else {
      for {
        combination <- combinations(occurrences)
        word <- dictionaryByOccurrences(combination)
        rest <- computeAnagram(subtract(occurrences, wordOccurrences(word)))

      } yield word :: rest
    }
  }
  computeAnagram(sentenceOccurrences(sentence))
}








for {
  el <- {
    combinations2(List("en", "as", "my", "man", "yes", "men", "say", "sane", "Sean")).filter(el => sentenceOccurrences(el) == sentenceOccurrences(List("Yes", "man")))}
} yield el.permutations.toList



List("yes", "man").permutations.toList
subtract(List(('x',2)), List(('x', 2)))


sentenceOccurrences(List("Yes", "man"))
val comba = combinations(sentenceOccurrences(List("Yes", "man")))


for {
  el <- comba
  if dictionaryByOccurrences contains el
} yield el

sentenceAnagrams(List("Yes", "man")).length