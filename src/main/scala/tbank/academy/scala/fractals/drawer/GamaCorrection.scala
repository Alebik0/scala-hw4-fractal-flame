package tbank.academy.scala.fractals.drawer

import org.apache.logging.log4j.{LogManager, Logger}
import tbank.academy.scala.fractals.data.{ImageData, PixelData}
import tbank.academy.scala.fractals.errors.DomainError

class GamaCorrection(val tunedOn: Boolean, val gamma: Double) {
  private val logger: Logger = LogManager.getLogger(getClass)

  def render(imageData: ImageData): Either[DomainError, ImageData] = {
    if (tunedOn) {
      logger.info("Applying gamma correction")

      Right(ImageData(
        width = imageData.width,
        height = imageData.height,
        pixels = imageData
          .pixels
          .map(row =>
            row.map((pixelData: PixelData) =>
              PixelData(
                red = math.pow(pixelData.red, 1.0 / gamma),
                green = math.pow(pixelData.green, 1.0 / gamma),
                blue = math.pow(pixelData.blue, 1.0 / gamma),
                alpha = pixelData.alpha,
                hits = pixelData.hits
              )
            )
          )
      ))
    } else {
      logger.info("Skipping gamma correction")
      Right(imageData)
    }
  }
}
