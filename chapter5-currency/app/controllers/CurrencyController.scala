package controllers

import play.api.mvc._
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import scala.concurrent.Future
import models._

object CurrencyController extends Controller with ExchangeRateHelpers {
  def convertOne(fromAmount: Double, fromCurrency: Currency, toCurrency: Currency) =
    Action.async { request =>
      exchange(fromAmount, fromCurrency, toCurrency) map { toAmount =>
        Ok(formatOutput(fromAmount, fromCurrency, toAmount, toCurrency))
      }
    }

  def convertAll(fromAmount: Double, fromCurrency: Currency) =
    Action.async { request =>
      val conversionFutures: Seq[Future[(Double, Currency)]] =
        currencies map { toCurrency =>
          exchange(fromAmount, fromCurrency, toCurrency) map { toAmount =>
            (toAmount, toCurrency)
          }
        }

      val futureConversions: Future[Seq[(Double, Currency)]] =
        Future.sequence(conversionFutures)

      val futureResult: Future[Result] =
        futureConversions map { conversions =>
          conversions map {
            case (toAmount, toCurrency) =>
              formatOutput(fromAmount, fromCurrency, toAmount, toCurrency)
          } mkString ("\n")
        } map (Ok(_))

      futureResult
    }

  def exchange(fromAmount: Double, fromCurrency: Currency, toCurrency: Currency): Future[Double] =
    for {
      usdAmount <- toUSD(fromAmount, fromCurrency)
      toAmount  <- fromUSD(usdAmount, toCurrency)
    } yield toAmount
}

trait ExchangeRateHelpers {
  val currencies: Seq[Currency] =
    Seq(USD, GBP, EUR)

  def toUSD(amount: Double, from: Currency): Future[Double] =
    from match {
      case USD => Future.successful(amount * 1.0)
      case GBP => Future.successful(amount * 1.5)
      case EUR => Future.successful(amount * 1.1)
    }

  def fromUSD(amount: Double, to: Currency): Future[Double] =
    toUSD(1.0, to) map (amount / _)

  def formatOutput(fromAmount: Double, fromCurrency: Currency, toAmount: Double, toCurrency: Currency): String =
    Currency.format(fromAmount, fromCurrency) + " = " + Currency.format(toAmount, toCurrency)
}
