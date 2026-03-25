package tbank.academy.scala.fractals.image

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers
import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper
import tbank.academy.scala.fractals.data.{ImageData, PixelData}

import java.io.ByteArrayOutputStream

class ImageDrawerTest extends AnyFlatSpec with Matchers {
  it should "draw test" in {
    val drawer = new ImageDrawer()
    val outputStream = new ByteArrayOutputStream()
    val imageData = ImageData(
      pixels = List(
        List(PixelData(0, 0, 0, 255, 0), PixelData(0, 0, 0, 255, 0), PixelData(0, 0, 0, 255, 0), PixelData(0, 0, 0, 255, 0), PixelData(0, 0, 0, 255, 0)),
        List(PixelData(0, 0, 0, 255, 0), PixelData(0, 0, 0, 255, 0), PixelData(0, 0, 0, 255, 0), PixelData(255, 0, 0, 255, 1000), PixelData(0, 0, 0, 255, 0)),
        List(PixelData(0, 0, 0, 255, 0), PixelData(0, 0, 0, 255, 0), PixelData(0, 0, 0, 255, 0), PixelData(0, 0, 0, 255, 0), PixelData(0, 0, 0, 255, 0)),
      ),
      width = 5,
      height = 3
    )
    drawer.draw(imageData, outputStream) match {
      case Some(_) =>
        outputStream.close()
        fail()
      case None =>
        outputStream.close()
        outputStream.toByteArray.toList shouldBe List(-119, 80, 78, 71, 13, 10, 26, 10, 0, 0, 0, 13, 73, 72, 68, 82, 0, 0, 0, 5, 0, 0, 0, 3, 8, 6, 0, 0, 0, 91, 54, -59, -8, 0, 0, 0, 21, 73, 68, 65, 84, 120, 94, 99, 96, 96, 96, -8, -113, 5, 35, 56, -1, -79, 9, -62, 48, 0, -35, 111, 15, -15, 7, -79, -55, -40, 0, 0, 0, 0, 73, 69, 78, 68, -82, 66, 96, -126)
    }
  }
}
