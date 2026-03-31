package tbank.academy.scala.fractals.drawer

import org.apache.logging.log4j.{LogManager, Logger}
import tbank.academy.scala.fractals.data.{ColoredAffineParams, ImageData}
import tbank.academy.scala.fractals.drawer.variations.WeightedVariationFunction
import tbank.academy.scala.fractals.errors.DomainError

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}

class ChaosGame(
    width: Int,
    height: Int,
    zoom: Double,
    seed: Long,
    threads: Int,
    iterationCount: Int,
    affineParams: List[ColoredAffineParams],
    functions: List[WeightedVariationFunction],
    symmetryLevel: Int
) {
  private val logger: Logger = LogManager.getLogger(getClass)

  implicit val ec: ExecutionContext = scala.concurrent.ExecutionContext.global

  def render(): Either[DomainError, ImageData] = {
    logger.info("Creating threads")
    val tasks           = (1 to threads).map(task)
    val resultsSequence = Future.sequence(tasks)

    logger.info("Awaiting threads")
    val result = Await.result(resultsSequence, Duration.Inf)
    logger.info("Got results")

    result.foldLeft[Either[DomainError, ImageData]](Right(ImageData.empty(width, height))) { (acc, elem) =>
      acc match {
        case Left(error)      => Left(error)
        case Right(imageData) =>
          elem match {
            case Left(error)  => Left(error)
            case Right(value) => Right(imageData.join(value))
          }
      }
    }
  }

  private def task(threadIndex: Int): Future[Either[DomainError, ImageData]] = Future {
    val thread =
      new ChaosGameThread(width, height, zoom, seed + threadIndex, iterationCount / threads, affineParams, functions, symmetryLevel)
    thread.render()
  }
}
