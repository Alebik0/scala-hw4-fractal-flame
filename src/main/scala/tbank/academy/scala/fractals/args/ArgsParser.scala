package tbank.academy.scala.fractals.args

import io.circe.generic.auto._
import io.circe.parser._
import org.apache.logging.log4j.{LogManager, Logger}
import tbank.academy.scala.fractals.data._
import tbank.academy.scala.fractals.errors._

import java.io.File
import java.nio.file.Files
import scala.annotation.tailrec
import scala.util.{Failure, Success, Try}

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
      functions = List()
    )

  def parse(args: List[String]): Either[DomainError, ProgramArguments] = {
    parseArgsMap(args) match {
      case Left(error) => Left(error)
      case Right(argsMap) =>
        parseCommandLineArguments(argsMap) match {
          case Left(error) => Left(error)
          case Right(commandLineArguments) =>
            parseConfigArguments(argsMap) match {
              case Left(error) => Left(error)
              case Right(configArguments) => Right(defaultArguments.edit(configArguments).edit(commandLineArguments))
            }
        }
    }
  }

  private def parseConfigArguments(argsMap: Map[String, String]): Either[DomainError, OptionalProgramArguments] = {
    argsMap.get("--config") match {
      case Some(configPath) =>
        val configFile = new File(configPath)

        logger.debug(s"Reading config from ${configFile.getAbsolutePath}")
        Try(Files.readString(configFile.toPath)) match {
          case Failure(exception) => Left(JavaError(exception))
          case Success(value) =>
            decode[JsonProgramArguments](value) match {
              case Left(error) => Left(JsonParseError(error))
              case Right(value) => Right(OptionalProgramArguments(
                width = value.size.map(_.width),
                height = value.size.map(_.height),
                seed = value.seed,
                iterationCount = value.iterationCount,
                outputPath = value.outputPath,
                threads = value.threads,
                affineParams = Some(value.affineParams),
                functions = Some(value.functions)
              ))
            }
        }
      case None => Right(OptionalProgramArguments.empty)
    }
  }

  private def parseCommandLineArguments(argsMap: Map[String, String]): Either[DomainError, OptionalProgramArguments] = {
    val outputPathValue = argsMap.get("--output").orElse(argsMap.get("-o"))
    val eitherWidthValue = parseWidth(argsMap)
    val eitherHeightValue = parseHeight(argsMap)
    val eitherSeedValue: Either[DomainError, Option[Long]] = parseSeedValue(argsMap)
    val eitherIterationCountValue = parseIterationCount(argsMap)
    val eitherThreadsValue = parseThreads(argsMap)
    val eitherAffineParamsValue = parseAffineParams(argsMap)
    val eitherFunctionsValue = parseFunctions(argsMap)

    eitherWidthValue match {
      case Left(error) => Left(error)
      case Right(widthValue) =>
        eitherHeightValue match {
          case Left(error) => Left(error)
          case Right(heightValue) =>
            eitherSeedValue match {
              case Left(error) => Left(error)
              case Right(seedValue) =>
                eitherIterationCountValue match {
                  case Left(error) => Left(error)
                  case Right(iterationCountValue) =>
                    eitherThreadsValue match {
                      case Left(error) => Left(error)
                      case Right(threadsValue) =>
                        eitherAffineParamsValue match {
                          case Left(error) => Left(error)
                          case Right(affineParamsValue) =>
                            eitherFunctionsValue match {
                              case Left(error) => Left(error)
                              case Right(functionsValue) =>
                                Right(
                                  OptionalProgramArguments(
                                    width = widthValue,
                                    height = heightValue,
                                    seed = seedValue,
                                    iterationCount = iterationCountValue,
                                    outputPath = outputPathValue,
                                    threads = threadsValue,
                                    affineParams = affineParamsValue,
                                    functions = functionsValue,
                                  )
                                )
                            }
                        }
                    }
                }
            }
        }
    }
  }

  private def parseWidth(argsMap: Map[String, String]) = {
    argsMap.get("--width").orElse(argsMap.get("-w")) match {
      case Some(it) =>
        it.toIntOption match {
          case Some(value) => Right(Some(value))
          case None => Left(InvalidArgsError("--width or -w"))
        }
      case None => Right(None)
    }
  }

  private def parseHeight(argsMap: Map[String, String]) = {
    argsMap.get("--height").orElse(argsMap.get("-h")) match {
      case Some(it) =>
        it.toIntOption match {
          case Some(value) => Right(Some(value))
          case None => Left(InvalidArgsError("--height or -h"))
        }
      case None => Right(None)
    }
  }

  private def parseSeedValue(argsMap: Map[String, String]) = {
    argsMap.get("--seed") match {
      case Some(it) =>
        it.toLongOption match {
          case Some(value) => Right(Some(value))
          case None => Left(InvalidArgsError("--seed"))
        }
      case None => Right(None)
    }
  }

  private def parseIterationCount(argsMap: Map[String, String]) = {
    argsMap.get("--iteration-count").orElse(argsMap.get("-i")) match {
      case Some(it) =>
        it.toIntOption match {
          case Some(value) => Right(Some(value))
          case None => Left(InvalidArgsError("--iteration-count or -i"))
        }
      case None => Right(None)
    }
  }

  private def parseThreads(argsMap: Map[String, String]) = {
    argsMap.get("--threads").orElse(argsMap.get("-t")) match {
      case Some(it) =>
        it.toIntOption match {
          case Some(value) => Right(Some(value))
          case None => Left(InvalidArgsError("--threads or -t"))
        }
      case None => Right(None)
    }
  }

  private def parseAffineParams(argsMap: Map[String, String]): Either[DomainError, Option[List[AffineParams]]] = {
    argsMap.get("--affine-params").orElse(argsMap.get("-ap")) match {
      case Some(value) =>
        val affineParamRegex = "^(.+),(.+),(.+),(.+),(.+),(.+)$".r
        value
          .split("/")
          .foldLeft[Either[DomainError, List[AffineParams]]](Right(List())) {
            (acc, affineParamString) =>
              acc match {
                case Left(error) => Left(error)
                case Right(accList) =>
                  affineParamRegex.findFirstMatchIn(affineParamString) match {
                    case Some(regexMatch) =>
                      regexMatch.group(1).toDoubleOption match {
                        case Some(a) =>
                          regexMatch.group(2).toDoubleOption match {
                            case Some(b) =>
                              regexMatch.group(3).toDoubleOption match {
                                case Some(c) =>
                                  regexMatch.group(4).toDoubleOption match {
                                    case Some(d) =>
                                      regexMatch.group(5).toDoubleOption match {
                                        case Some(e) =>
                                          regexMatch.group(6).toDoubleOption match {
                                            case Some(f) => Right(accList :+ AffineParams(a, b, c, d, e, f))
                                            case None => Left(InvalidArgsError(s"--affine-params or -ap: \"$affineParamString\" is not affine param"))
                                          }
                                        case None => Left(InvalidArgsError(s"--affine-params or -ap: \"$affineParamString\" is not affine param"))
                                      }
                                    case None => Left(InvalidArgsError(s"--affine-params or -ap: \"$affineParamString\" is not affine param"))
                                  }
                                case None => Left(InvalidArgsError(s"--affine-params or -ap: \"$affineParamString\" is not affine param"))
                              }
                            case None => Left(InvalidArgsError(s"--affine-params or -ap: \"$affineParamString\" is not affine param"))
                          }
                        case None => Left(InvalidArgsError(s"--affine-params or -ap: \"$affineParamString\" is not affine param"))
                      }
                    case None => Left(InvalidArgsError(s"--affine-params or -ap: \"$affineParamString\" is not affine param"))
                  }
              }
          }
          .map(Some(_))
      case None => Right(None)
    }
  }

  private def parseFunctions(argsMap: Map[String, String]) = {
    argsMap.get("--functions").orElse(argsMap.get("-f")) match {
      case Some(value) =>
        val functionRegex = "^(\\w+):(.+)$".r
        value
          .split(",")
          .foldLeft[Either[DomainError, List[WeightedFunction]]](Right(List())) {
            (acc, functionString) =>
              acc match {
                case Left(error) => Left(error)
                case Right(accList) =>
                  functionRegex.findFirstMatchIn(functionString) match {
                    case Some(regexMatch) =>
                      regexMatch.group(2).toDoubleOption match {
                        case Some(weight) => Right(accList :+ WeightedFunction(weight, regexMatch.group(1)))
                        case None => Left(InvalidArgsError(s"--affine-params or -ap: \"$functionString\" is not affine param"))
                      }
                    case None => Left(InvalidArgsError(s"--affine-params or -ap: \"$functionString\" is not affine param"))
                  }
              }
          }
          .map(Some(_))
      case None => Right(None)
    }
  }

  @tailrec
  final def parseArgsMap(args: List[String], acc: Map[String, String] = Map()): Either[DomainError, Map[String, String]] =
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
