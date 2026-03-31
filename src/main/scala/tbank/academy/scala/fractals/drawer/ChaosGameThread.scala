package tbank.academy.scala.fractals.drawer

import org.apache.logging.log4j.{LogManager, Logger}
import tbank.academy.scala.fractals.data.{ColoredAffineParams, ImageData, PixelData, Point}
import tbank.academy.scala.fractals.drawer.variations.WeightedVariationFunction
import tbank.academy.scala.fractals.errors.{DomainError, UnexpectedError}

import scala.annotation.tailrec
import scala.util.Random

class ChaosGameThread(
    width: Int,
    height: Int,
    zoom: Double,
    seed: Long,
    iterationCount: Int,
    affineParams: List[ColoredAffineParams],
    functions: List[WeightedVariationFunction],
    symmetryLevel: Int
) {
  private val logger: Logger = LogManager.getLogger(getClass)

  private val random = new Random(seed)

  def render(): Either[DomainError, ImageData] = {
    val aspect       = width.toDouble / height
    val initialPoint = Point(random.nextDouble() * 2 * aspect - aspect, random.nextDouble() * 2 - 1)

    logger.info("Calculating point hits")
    pointsHit(initialPoint) match {
      case Left(error)        => Left(error)
      case Right(imagePoints) =>
        logger.info(s"Loaded ${imagePoints.length} hits")
        val imagePointsMap = imagePoints.groupBy(imagePoint => (imagePoint.imageX, imagePoint.imageY))

        logger.info("Rendering pixels")
        val pixels = List.tabulate(height, width) { (row, col) =>
          makePixel(imagePointsMap.unapply((col, row)).getOrElse(List()))
        }

        Right(ImageData(pixels, width, height))
    }
  }

  @tailrec
  final def makePixel(localHits: List[HitData], acc: PixelData = PixelData.empty): PixelData =
    localHits match {
      case Nil            => acc
      case ::(head, next) =>
        if (acc == PixelData.empty)
          makePixel(next, PixelData(head.red, head.green, head.blue, 1.0, hits = 1))
        else
          makePixel(next, acc.merge(head.red, head.green, head.blue))
    }

  @tailrec
  final def pointsHit(
      point: Point,
      step: Int = -20,
      acc: List[HitData] = List()
  ): Either[DomainError, List[HitData]] = {
    val fIndex = random.nextInt(affineParams.length)

    affineParams.unapply(fIndex) match {
      case None               => Left(UnexpectedError(s"Cannot get F($fIndex) value"))
      case Some(affineParams) =>
        val nextPoint = functions
          .map(weightedFunction =>
            weightedFunction
              .function
              .apply(
                x = point.x * affineParams.params.a + point.y * affineParams.params.b + affineParams.params.c,
                y = point.x * affineParams.params.d + point.y * affineParams.params.e + affineParams.params.f,
                params = affineParams.params
              )
              .times(weightedFunction.weight)
          )
          .foldLeft(Point.zero)(_.add(_))

        if (step >= iterationCount)
          Right(acc)
        else if (step >= 0) {
          val hits = symmetryPoints(nextPoint)
            .map(p => {
              val (x, y) = pointToImage(p)
              HitData(
                imageX = x,
                imageY = y,
                red = affineParams.red,
                green = affineParams.green,
                blue = affineParams.blue
              )
            })

          pointsHit(nextPoint, step + 1, acc :++ hits)
        } else
          pointsHit(nextPoint, step + 1, acc)
    }
  }

  private def symmetryPoints(point: Point): List[Point] = {
    val angle = 2.0 * math.Pi / symmetryLevel
    (0 until symmetryLevel)
      .map(alpha =>
        Point(
          point.x * math.cos(angle * alpha) - point.y * math.sin(angle * alpha),
          point.x * math.sin(angle * alpha) + point.y * math.cos(angle * alpha)
        )
      )
      .toList
  }

  private def pointToImage(p: Point): (Int, Int) = {
    val x = width / 2 + (p.x * zoom * math.min(width, height) / 2).toInt
    val y = height / 2 + (p.y * zoom * math.min(width, height) / 2).toInt

    (x, y)
  }
}
