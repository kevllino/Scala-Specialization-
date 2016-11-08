val lvlVec = Vector(Vector('S', 'T'), Vector('o', 'o'), Vector('o', 'o'))

Vector('S', 'T') indexOf ('T')
List(3)(0)


lvlVec(lvlVec indexWhere (x => x contains ('T'))) indexOf 'T'

val alo = Vector(Vector('S', 'T'), Vector('o', 'o'), Vector('o', 'o'))

List('S', 'T', 'o') contains alo(1)(0)

val level =
  """ooo-------
    |oSoooo----
    |ooooooooo-
    |-ooooooooo
    |-----ooToo
    |------ooo-""".stripMargin

lazy val vector: Vector[Vector[Char]] =
  Vector(level.split("\n").map(str => Vector(str: _*)): _*)


3 :: List(1,2)

List((6, "a"), (7, 'b)) filter
  ({case (d, l) => d % 2 == 0})