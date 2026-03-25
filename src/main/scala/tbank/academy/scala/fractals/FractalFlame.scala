package tbank.academy.scala.fractals

import org.apache.logging.log4j.{LogManager, Logger}
import tbank.academy.scala.fractals.data.AffineParams
import tbank.academy.scala.fractals.drawer.ChaosGame
import tbank.academy.scala.fractals.drawer.variations.{LinearVariationFunction, SinusoidalVariationFunction, SphericalVariationFunction, WeightedVariationFunction}
import tbank.academy.scala.fractals.errors.JavaError
import tbank.academy.scala.fractals.image.ImageDrawer

import java.io.FileOutputStream
import scala.util.{Failure, Success, Try}

object FractalFlame {
  private val logger: Logger = LogManager.getLogger(getClass)

  def main(args: Array[String]): Unit = {
    val chaosGame = new ChaosGame(
      width = 600,
      height = 400,
      zoom = 0.25,
      seed = 1234,
      iterationCount = 50_000,
      affineParams = List(
        AffineParams(1.116,0.081,0.941,-0.880,0.817,0.573, 255, 0, 0),
        AffineParams(0.856,0.406,-0.444,-0.267,1.463,-0.414, 0, 255, 0),
        AffineParams(-0.066,0.917,0.803,-1.232,-0.490,-1.376, 0, 0, 255),
        AffineParams(0.337,1.106,0.601,-0.953,-1.453,-0.750, 128, 128, 0),
        AffineParams(0.106,0.711,-1.410,-1.431,1.277,-0.904, 0, 128, 128),
      ),
      functions = List(
        WeightedVariationFunction(0.4, LinearVariationFunction),
        WeightedVariationFunction(0.3, SphericalVariationFunction),
          WeightedVariationFunction(0.3, SinusoidalVariationFunction),
      )
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
}
