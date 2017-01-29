val mnem = Map('2'-> "ABC", '3'-> "DEF",
  '4'->"GHI", '5'->"JKL", '6'->"MNO", '7'-> "PQRS",
  '8' -> "TUV", '9'->"WXYZ")
val words = List("Aarhus", "Aaron")

val charCode: Map[Char, Char] =
  for {
    (digit, letters) <- mnem
    letter <- letters
  }
    yield (letter, digit)

def wordCode(word: String): String =
 word.toUpperCase map (charCode(_))

val wordsForNum: Map[String, Seq[String]] =
  words groupBy wordCode withDefaultValue Seq()

def encode(number: String): Set[List[String]] =
if(number.isEmpty) Set(List())
else {
  for {
    split <- 1 to number.length
    word <- wordsForNum(number take split)
    rest <- encode(number drop split)
  } yield word :: rest
}.toSet

encode("7225247386")


