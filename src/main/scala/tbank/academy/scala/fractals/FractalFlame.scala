package tbank.academy.scala.fractals

import org.apache.logging.log4j.{LogManager, Logger}
import tbank.academy.scala.fractals.args.ArgsParser
import tbank.academy.scala.fractals.data.{ColoredAffineParams, ProgramArguments, WeightedFunction}
import tbank.academy.scala.fractals.drawer.ChaosGame
import tbank.academy.scala.fractals.drawer.variations._
import tbank.academy.scala.fractals.errors.JavaError
import tbank.academy.scala.fractals.image.ImageDrawer

import java.io.FileOutputStream
import scala.util.{Failure, Random, Success, Try}

object FractalFlame {
  private val logger: Logger = LogManager.getLogger(getClass)

  def main(args: Array[String]): Unit = {
    val argsParser = new ArgsParser()
    argsParser.parse(args.toList) match {
      case Left(error) =>
        logger.error(error)
      case Right(value) =>
        runWithArgs(value)
    }
  }

  private def runWithArgs(programArguments: ProgramArguments): Unit = {
    logger.debug("Running with arguments: " + programArguments.toString)
    val random = new Random(programArguments.seed)
    val chaosGame = new ChaosGame(
      width = programArguments.width,
      height = programArguments.height,
      zoom = 0.5,
      seed = programArguments.seed,
      iterationCount = programArguments.iterationCount,
      affineParams = programArguments.affineParams.map(ColoredAffineParams(_, random.nextInt(255), random.nextInt(255), random.nextInt(255))),
      functions = programArguments.functions.map(makeVariation)
    )

    logger.info("Rendering image")
    chaosGame.render() match {
      case Left(error) =>
        logger.error(error)
      case Right(result) =>
        logger.info("Rendering image completed")

        val drawer = new ImageDrawer()

        logger.info("Drawing image")
        Try(new FileOutputStream("output.png")) match {
          case Failure(exception) =>
            logger.error(JavaError(exception))
          case Success(outputStream) =>
            drawer.draw(result, outputStream) match {
              case None =>
                outputStream.close()
                logger.info("Drawing image completed")
              case Some(error) =>
                outputStream.close()
                logger.error(error)
            }
        }
    }
  }

  private def makeVariation(fun: WeightedFunction): WeightedVariationFunction =
    WeightedVariationFunction(
      fun.weight,
      fun.name match {
        case "horseshoe" => HorseshoeVariationFunction
        case "linear" => LinearVariationFunction
        case "sinusoidal" => SinusoidalVariationFunction
        case "spherical" => SphericalVariationFunction
        case "swirl" => SwirlVariationFunction
        case _ => LinearVariationFunction
      }
    )
}
