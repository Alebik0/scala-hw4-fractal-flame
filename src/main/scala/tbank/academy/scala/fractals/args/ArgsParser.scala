package tbank.academy.scala.fractals.args

import org.apache.logging.log4j.{LogManager, Logger}
import tbank.academy.scala.fractals.data._
import tbank.academy.scala.fractals.errors._

import scala.annotation.tailrec

class ArgsParser {
  private val logger: Logger = LogManager.getLogger(getClass)

  private val defaultArguments: ProgramArguments =
    ProgramArguments(
      width = 1920,
      height = 1080,
      seed = 5,
      iterationCount = 2500,
      outputPath = "result.png",
      threads = 1,
      affineParams = List(),
      functions = List(),
      symmetryLevel = 1
    )

  def parse(args: List[String]): Either[DomainError, ProgramArguments] = {
    logger.info("Parsing arguments")

    parseArgsMap(args) match {
      case Left(error)    => Left(error)
      case Right(argsMap) =>
        CommandLineArgsParser.parse(argsMap) match {
          case Left(error)                 => Left(error)
          case Right(commandLineArguments) =>
            JsonConfigArgsParser.parse(argsMap) match {
              case Left(error)            => Left(error)
              case Right(configArguments) =>
                Right(defaultArguments.edit(configArguments).edit(commandLineArguments))
            }
        }
    }
  }

  @tailrec
  private def parseArgsMap(
      args: List[String],
      acc: Map[String, String] = Map()
  ): Either[DomainError, Map[String, String]] =
    args match {
      case ::(key, next) =>
        if (key.startsWith("--") || key.startsWith("-"))
          next match {
            case ::(value, next) =>
              parseArgsMap(next, acc + (key -> value))
            case Nil =>
              Left(NoArgValueFoundError(key))
          }
        else
          Left(NoArgKeyFoundError(key))
      case Nil => Right(acc)
    }
}
