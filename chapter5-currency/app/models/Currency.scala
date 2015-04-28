package models

import play.api.mvc._

sealed trait Currency {

}
final case object USD extends Currency
final case object GBP extends Currency
final case object EUR extends Currency

object Currency {
  private val numberFormat = new java.text.DecimalFormat("#.##")

  def format(amount: Double, currency: Currency) =
    s"""${numberFormat.format(amount)} $currency"""

  implicit val currencyPathBindable = new PathBindable[Currency] {
    def bind(key: String, value: String) =
      value.toUpperCase match {
         case "USD" => Right(USD)
         case "GBP" => Right(GBP)
         case "EUR" => Right(EUR)
         case _     => Left("bad.currency")
      }

    def unbind(key: String, value: Currency) =
      value.toString
  }
}
