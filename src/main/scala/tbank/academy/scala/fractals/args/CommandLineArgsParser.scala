package tbank.academy.scala.fractals.args

import org.apache.logging.log4j.{LogManager, Logger}
import tbank.academy.scala.fractals.data.{AffineParams, OptionalProgramArguments, WeightedFunction}
import tbank.academy.scala.fractals.errors.{DomainError, InvalidArgsError}

object CommandLineArgsParser {
  private val logger: Logger = LogManager.getLogger(getClass)

  def parse(argsMap: Map[String, String]): Either[DomainError, OptionalProgramArguments] = {
    logger.info("Parsing command line arguments")

    val outputPathValue           = parseOutput(argsMap)
    val eitherWidthValue          = parseWidth(argsMap)
    val eitherHeightValue         = parseHeight(argsMap)
    val eitherSeedValue           = parseSeedValue(argsMap)
    val eitherIterationCountValue = parseIterationCount(argsMap)
    val eitherThreadsValue        = parseThreads(argsMap)
    val eitherAffineParamsValue   = parseAffineParams(argsMap)
    val eitherFunctionsValue      = parseFunctions(argsMap)
    val builder                   = new ResultBuilder(
      outputPathValue,
      eitherWidthValue,
      eitherHeightValue,
      eitherSeedValue,
      eitherIterationCountValue,
      eitherThreadsValue,
      eitherAffineParamsValue,
      eitherFunctionsValue
    )

    builder.build()
  }

  private def parseIntArg(
      argsMap: Map[String, String],
      arg1: String,
      arg2: String
  ): Either[DomainError, Option[Int]] = {
    argsMap.get(arg1).orElse(argsMap.get(arg2)) match {
      case Some(it) =>
        it.toIntOption match {
          case Some(value) => Right(Some(value))
          case None        => Left(InvalidArgsError(s"$arg1 or $arg2"))
        }
      case None => Right(None)
    }
  }

  private def parseOutput(argsMap: Map[String, String]): Option[String] =
    argsMap.get("--output").orElse(argsMap.get("-o"))

  private def parseWidth(argsMap: Map[String, String]): Either[DomainError, Option[Int]] =
    parseIntArg(argsMap, "--width", "-w")

  private def parseHeight(argsMap: Map[String, String]): Either[DomainError, Option[Int]] =
    parseIntArg(argsMap, "--height", "-h")

  private def parseSeedValue(argsMap: Map[String, String]): Either[DomainError, Option[Long]] =
    argsMap.get("--seed") match {
      case Some(it) =>
        it.toLongOption match {
          case Some(value) => Right(Some(value))
          case None        => Left(InvalidArgsError("--seed"))
        }
      case None => Right(None)
    }

  private def parseIterationCount(argsMap: Map[String, String]): Either[DomainError, Option[Int]] =
    parseIntArg(argsMap, "--iteration-count", "-i")

  private def parseThreads(argsMap: Map[String, String]): Either[DomainError, Option[Int]] =
    parseIntArg(argsMap, "--threads", "-t")

  private def parseAffineParams(argsMap: Map[String, String]): Either[DomainError, Option[List[AffineParams]]] = {
    argsMap.get("--affine-params").orElse(argsMap.get("-ap")) match {
      case Some(value) =>
        value
          .split("/")
          .foldLeft[Either[DomainError, List[AffineParams]]](Right(List())) {
            (acc, value) =>
              acc match {
                case Left(error)    => Left(error)
                case Right(accList) => AffineParamsArgsParser.parse(value).map(accList :+ _)
              }
          }
          .map(Some(_))
      case None => Right(None)
    }
  }

  private def parseFunctions(argsMap: Map[String, String]): Either[DomainError, Option[List[WeightedFunction]]] = {
    argsMap.get("--functions").orElse(argsMap.get("-f")) match {
      case Some(value) =>
        value
          .split(",")
          .foldLeft[Either[DomainError, List[WeightedFunction]]](Right(List())) {
            (acc, value) =>
              acc match {
                case Left(error)    => Left(error)
                case Right(accList) => FunctionsArgsParser.parse(value).map(accList :+ _)
              }
          }
          .map(Some(_))
      case None => Right(None)
    }
  }

}
