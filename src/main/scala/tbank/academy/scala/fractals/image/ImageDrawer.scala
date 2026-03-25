package tbank.academy.scala.fractals.image

import org.apache.logging.log4j.{LogManager, Logger}
import tbank.academy.scala.fractals.data.ImageData
import tbank.academy.scala.fractals.errors.{DomainError, JavaError}

import java.awt.image.BufferedImage
import java.io.OutputStream
import javax.imageio.ImageIO
import scala.util.Try

class ImageDrawer {
  private val logger: Logger = LogManager.getLogger(getClass)

  def draw(imageData: ImageData, outputStream: OutputStream): Option[DomainError] = {
    logger.debug(s"Preparing image to save with size: ${imageData.width}x${imageData.height}")

    val imageOut       = new BufferedImage(imageData.width, imageData.height, BufferedImage.TYPE_4BYTE_ABGR)
    val imageOutPixels = imageData
      .pixels
      .flatten
      .map(_.toInt)
      .toArray
    imageOut.setRGB(0, 0, imageData.width, imageData.height, imageOutPixels, 0, imageData.width)

    logger.info("Saving image")
    Try(ImageIO.write(imageOut, "png", outputStream)).toEither match {
      case Left(javaError) => Some(JavaError(javaError))
      case Right(_)        => None
    }
  }
}
