package tbank.academy.scala.fractals.args

import io.circe.generic.auto._
import io.circe.parser._
import org.apache.logging.log4j.{LogManager, Logger}
import tbank.academy.scala.fractals.data._
import tbank.academy.scala.fractals.errors._

import java.io.File
import java.nio.file.Files
import scala.util.{Failure, Success, Try}

object JsonConfigArgsParser {
  private val logger: Logger = LogManager.getLogger(getClass)

  def parse(argsMap: Map[String, String]): Either[DomainError, OptionalProgramArguments] =
    argsMap.get("--config") match {
      case Some(configPath) =>
        val configFile = new File(configPath)

        logger.info(s"Parsing JSON arguments from ${configFile.getAbsolutePath}")
        Try(Files.readString(configFile.toPath)) match {
          case Failure(exception) => Left(JavaError(exception))
          case Success(value)     => parseJsonConfig(value)
        }
      case None => Right(OptionalProgramArguments.empty)
    }

  def parseJsonConfig(value: String): Either[DomainError, OptionalProgramArguments] =
    decode[JsonProgramArguments](value) match {
      case Left(error)  => Left(JsonParseError(error))
      case Right(value) => Right(OptionalProgramArguments(
          width = value.size.map(_.width),
          height = value.size.map(_.height),
          seed = value.seed,
          iterationCount = value.iteration_count,
          outputPath = value.output_path,
          threads = value.threads,
          affineParams = value.affine_params,
          functions = value.functions
        ))
    }
}
