package controllers

import javax.inject._
import models._
import play.api.mvc._
import scala.concurrent._

@Singleton class CurrencyController @Inject() (cc: ControllerComponents)(implicit ec: ExecutionContext) extends AbstractController(cc) {
  def convertOne(fromAmount: Double, fromCurrency: Currency, toCurrency: Currency) =
    Action.async { request =>
      exchange(fromAmount, fromCurrency, toCurrency).
        map(Ok(_))
    }

  def convertAll(fromAmount: Double, fromCurrency: Currency) =
    Action.async { request =>
      Future.
        sequence(currencies.map(exchange(fromAmount, fromCurrency, _))).
        map(lines => Ok(lines mkString "\n"))
    }

  def exchange(fromAmount: Double, fromCurrency: Currency, toCurrency: Currency): Future[String] =
    for {
      usdAmount <- toUSD(fromAmount, fromCurrency)
      toAmount  <- fromUSD(usdAmount, toCurrency)
    } yield formatConversion(fromAmount, fromCurrency, toAmount, toCurrency)

  // Helpers

  val currencies: Seq[Currency] =
    Seq(USD, GBP, EUR)

  def toUSD(amount: Double, from: Currency): Future[Double] =
    from match {
      case USD => Future.successful(amount * 1.0)
      case GBP => Future.successful(amount * 1.5)
      case EUR => Future.successful(amount * 1.1)
    }

  def fromUSD(amount: Double, to: Currency): Future[Double] =
    toUSD(1.0, to).map(amount / _)

  def formatConversion(fromAmount: Double, fromCurrency: Currency, toAmount: Double, toCurrency: Currency): String =
    Currency.format(fromAmount, fromCurrency) + " = " + Currency.format(toAmount, toCurrency)
}
