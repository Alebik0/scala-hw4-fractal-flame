package tbank.academy.scala.fractals.args

import tbank.academy.scala.fractals.data.AffineParams
import tbank.academy.scala.fractals.errors.{DomainError, InvalidArgsError}

import scala.util.matching.Regex

object AffineParamsArgsParser {
  private val affineParamRegex: Regex = "^(.+),(.+),(.+),(.+),(.+),(.+)$".r

  def parse(affineParamString: String): Either[DomainError, AffineParams] =
    affineParamRegex.findFirstMatchIn(affineParamString) match {
      case Some(regexMatch) => AffineParamsArgsParser.parseInternal(regexMatch)
      case None => Left(InvalidArgsError("--affine-params or -ap"))
    }

  private def parseInternal(regexMatch: Regex.Match): Either[DomainError, AffineParams] =
    regexMatch.group(1).toDoubleOption match {
      case Some(a) => parseInternal(regexMatch, a)
      case None => Left(InvalidArgsError("--affine-params or -ap"))
    }

  private def parseInternal(regexMatch: Regex.Match, a: Double): Either[DomainError, AffineParams] =
    regexMatch.group(2).toDoubleOption match {
      case Some(b) => parseInternal(regexMatch, a, b)
      case None => Left(InvalidArgsError("--affine-params or -ap"))
    }

  private def parseInternal(regexMatch: Regex.Match, a: Double, b: Double): Either[DomainError, AffineParams] =
    regexMatch.group(3).toDoubleOption match {
      case Some(c) => parseInternal(regexMatch, a, b, c)
      case None => Left(InvalidArgsError("--affine-params or -ap"))
    }

  private def parseInternal(regexMatch: Regex.Match, a: Double, b: Double, c: Double): Either[DomainError, AffineParams] =
    regexMatch.group(4).toDoubleOption match {
      case Some(d) => parseInternal(regexMatch, a, b, c, d)
      case None => Left(InvalidArgsError("--affine-params or -ap"))
    }

  private def parseInternal(regexMatch: Regex.Match, a: Double, b: Double, c: Double, d: Double): Either[DomainError, AffineParams] =
    regexMatch.group(5).toDoubleOption match {
      case Some(e) => parseInternal(regexMatch, a, b, c, d, e)
      case None => Left(InvalidArgsError("--affine-params or -ap"))
    }

  private def parseInternal(regexMatch: Regex.Match, a: Double, b: Double, c: Double, d: Double, e: Double): Either[DomainError, AffineParams] =
    regexMatch.group(6).toDoubleOption match {
      case Some(f) => Right(AffineParams(a, b, c, d, e, f))
      case None => Left(InvalidArgsError("--affine-params or -ap"))
    }
}
