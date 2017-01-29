package recfun

object Main {
  def main(args: Array[String]) {
    println("Pascal's Triangle")
    for (row <- 0 to 10) {
      for (col <- 0 to row)
        print(pascal(col, row) + " ")
      println()
    }
  }

  /**
    * Exercise 1
    */
  def pascal(c: Int, r: Int): Int = {
    require(c >= 0 && r >= 0)
    if(c == 0 && r == 0) 1
    else if(c == 0 && r > 0) 1
    else if (c > 0 && r > 0) (pascal(c - 1, r-1) + pascal(c , r -1))
    else 0
  }

  /**
    * Exercise 2
    */
  def balance(chars: List[Char]): Boolean = {

    // extract only paranthesis
    val paranthesis = chars.filter(el => el == '(' || el == ')')
    def loop(paran: List[Char], acc: Int): Boolean = {
      if (paran.isEmpty) acc == 0
      else if (paran.last == '(') false
      else if (acc < 0) false // check order, i.e. ')(' is illegal
      else if (paran.head == '(') loop(paran.tail, acc + 1)
      else if (paran.head == ')') loop(paran.tail, acc - 1)
      else false

    }
    loop(paranthesis, 0) // acc = number of open paranthesis, initialised to 0

  }


  def balance_2(chars: List[Char]): Boolean = {
    def go(paran: List[Char], acc: Int): Boolean = {
      paran match {
        case l if l.isEmpty => acc == 0
        case h :: t if h == '(' => go(t, acc + 1)
        case h :: t if acc > 0  && h == ')' => go(t, acc - 1)
        case h :: t if h == ')' => false
        case _ :: t =>  go(t, acc)
      }
    }
    go(chars, 0)
  }

  /**
    * Exercise 3
    */

  def countChange(money: Int, coins: List[Int]): Int = {
    // require(money >= 0)
    require(coins.forall(_ > 0))
    // if money = 0 and list of coins not empty then 1 way money = 0 * t1 + 0 *2
    if (money == 0 && !coins.isEmpty) 1
    else if (money > 0 && !coins.isEmpty)
      countChange(money - coins.sorted.head, coins) + countChange(money, coins.sorted.tail)
    // list of coins is not empty then 0 ways
    else 0

  }
}
