package tbank.academy.scala.fractals.drawer

import org.apache.logging.log4j.{LogManager, Logger}
import tbank.academy.scala.fractals.data.{AffineParams, ImageData, PixelData, Point}
import tbank.academy.scala.fractals.drawer.variations.WeightedVariationFunction
import tbank.academy.scala.fractals.errors.{DomainError, UnexpectedError}

import scala.annotation.tailrec
import scala.util.Random

class ChaosGame(width: Int,
                height: Int,
                zoom: Double,
                seed: Long,
                iterationCount: Int,
                affineParams: List[AffineParams],
                functions: List[WeightedVariationFunction]) {
  private val logger: Logger = LogManager.getLogger(getClass)

  private val random = new Random(seed)

  def render(): Either[DomainError, ImageData] = {
    val aspect = width.toDouble / height
    val initialPoint = Point(random.nextDouble() * 2 * aspect - aspect, random.nextDouble() * 2 - 1)

    logger.info("Calculating point hits")
    pointsHit(initialPoint) match {
      case Left(error) => Left(error)
      case Right(imagePoints) =>
        logger.info(s"Loaded ${imagePoints.length} hits")
        val imagePointsMap = imagePoints.groupBy(imagePoint => (imagePoint.imageX, imagePoint.imageY))

        logger.info(s"Rendering pixels")
        val pixels = List.tabulate(height, width) { (row, col) =>
          makePixel(imagePointsMap.unapply((col, row)).getOrElse(List()))
        }

        Right(ImageData(pixels, width, height))
    }
  }

  @tailrec
  final def makePixel(localHits: List[HitData], acc: PixelData = PixelData.empty): PixelData =
    localHits match {
      case Nil => acc
      case ::(head, next) =>
        if (acc == PixelData.empty)
          makePixel(next, PixelData(red = head.red, green = head.green, blue = head.blue, alpha = 255, hits = 1))
        else
          makePixel(next, acc.merge(head.red, head.green, head.blue))
    }

  @tailrec
  final def pointsHit(point: Point,
                      step: Int = -20,
                      acc: List[HitData] = List()): Either[DomainError, List[HitData]] = {
    val fIndex = random.nextInt(affineParams.length)

    affineParams.unapply(fIndex) match {
      case None => Left(UnexpectedError(s"Cannot get F($fIndex) value"))
      case Some(affineParams) =>
        val nextPoint = functions
          .map(
            weightedFunction =>
              weightedFunction
                .function
                .apply(
                  x = point.x * affineParams.a + point.y * affineParams.b + affineParams.c,
                  y = point.x * affineParams.d + point.y * affineParams.e + affineParams.f,
                  params = affineParams
                )
                .times(weightedFunction.weight)
          )
          .foldLeft(Point.zero)(_.add(_))

        if (step >= iterationCount)
          Right(acc)
        else if (step >= 0) {
          val x = width / 2 + (nextPoint.x * zoom * math.min(width, height) / 2).toInt
          val y = height / 2 + (nextPoint.y * zoom * math.min(width, height) / 2).toInt
          val hitData = HitData(
            imageX = x,
            imageY = y,
            red = affineParams.red,
            green = affineParams.green,
            blue = affineParams.blue
          )

          pointsHit(nextPoint, step + 1, acc :+ hitData)
        } else
          pointsHit(nextPoint, step + 1, acc)
    }
  }

  //  void render (int n, int eqCount, int it, int xRes, int yRes) {
  //    Генерируем eqCount аффинных преобразований со стартовыми цветами;
  //    for (int num = 0; num < n; num ++) {
  //      //Для изображения размером 1920х1080 можно
  //      //взять XMIN=-1.777,XMAX=1.777,YMIN=-1,YMAX=1
  //      //В этом случае в большинстве нелинейных преобразований с боков не будет оставаться черных областей
  //      newX = Rand(XMIN, XMAX);
  //      newY = Rand(YMIN, YMAX);
  //      //Первые 20 итераций точку не рисуем, т.к. сначала надо найти начальную
  //      for (int step =- 20; step < it; step ++
  //      )
  //      {
  //        //Выбираем одно из аффинных преобразований
  //        i = Rand(0, eqCount);
  //        //и применяем его
  //        x = coeff[i].a * newX + coeff[i].b * newY + coeff[i].c;
  //        y = coeff[i].d * newX + coeff[i].e * newY + coeff[i].f;
  //        Применяем нелинейное преобразование;
  //        if (step >= 0 && newX ∈[XMIN, XMAX] && newY ∈[YMIN, YMAX]) {
  //          //Вычисляем координаты точки, а затем задаем цвет
  //          x1 = xRes - Trunc(((XMAX - newX) / (XMAX - XMIN)) * xRes);
  //          y1 = yRes - Trunc(((YMAX - newY) / (YMAX - YMIN)) * yRes);
  //          //Если точка попала в область изображения
  //          if (x1 < xRes && y1 < yRes) {
  //            //то проверяем, первый ли раз попали в нее
  //            if (pixels[x1][y1].counter == 0) {
  //              //Попали в первый раз, берем стартовый цвет у соответствующего аффинного преобразования
  //              pixels[x1][y1].red = coeff[i].red;
  //              pixels[x1][y1].green = coeff[i].green;
  //              pixels[x1][y1].blue = coeffs[i].blue;
  //            } else {
  //              //Попали не в первый раз, считаем так:
  //              pixels[x1][y1].red = (pixels[x1][y1].red + coeff[i].red) / 2;
  //              pixels[x1][y1].green = (pixels[x1][y1].green + coeff[i].green) / 2;
  //              pixels[x1][y1].blue = (pixels[x1][y1].blue + coeff[i].blue) / 2;
  //            }
  //            //Увеличиваем счетчик точки на единицу
  //            pixels[x1][y1].counter ++;
  //          }
  //        }
  //      }
  //    }
  //  }
}


