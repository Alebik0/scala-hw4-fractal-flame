package tbank.academy.scala.fractals.args

import tbank.academy.scala.fractals.data.WeightedFunction
import tbank.academy.scala.fractals.errors.{DomainError, InvalidArgsError}

import scala.util.matching.Regex

object FunctionsArgsParser {
  private val functionRegex: Regex = "^(\\w+):(.+)$".r

  def parse(value: String): Either[DomainError, WeightedFunction] =
    functionRegex.findFirstMatchIn(value) match {
      case Some(regexMatch) =>
        regexMatch.group(2).toDoubleOption match {
          case Some(weight) => Right(WeightedFunction(weight, regexMatch.group(1)))
          case None         => Left(InvalidArgsError("--functions or -f"))
        }
      case None => Left(InvalidArgsError("--functions or -f"))
    }

}
