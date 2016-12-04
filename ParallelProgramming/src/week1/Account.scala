package week1

/**
  * Created by keid on 03/12/2016.
  */


class Account(private var amount: Double = 0) {

  def transfer(target: Account, n: Double) =
    this.synchronized {
      target.synchronized {
        this.amount -= n
        target.amount += n

      }
    }
}
