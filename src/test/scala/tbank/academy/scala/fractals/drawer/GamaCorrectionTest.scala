package tbank.academy.scala.fractals.drawer

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers
import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper
import tbank.academy.scala.fractals.data.{ImageData, PixelData}

class GamaCorrectionTest extends AnyFlatSpec with Matchers {
  private val EPS = 1e-6

  it should "No gamma correction being applied test" in {
    val gamaCorrection = new GamaCorrection(false, 2.2)
    val imageData      = new ImageData(
      pixels = List(
        List(
          PixelData(0.0, 0.5, 1.0, 1.0, 1)
        )
      ),
      width = 1,
      height = 1
    )
    val expected = new ImageData(
      pixels = List(
        List(
          PixelData(0.0, 0.5, 1.0, 1.0, 1)
        )
      ),
      width = 1,
      height = 1
    )

    gamaCorrection.render(imageData) shouldBe Right(expected)
  }

  it should "gamma = 1.0" in {
    val gamaCorrection = new GamaCorrection(true, 1.0)
    val imageData      = new ImageData(
      pixels = List(
        List(
          PixelData(0.0, 0.5, 1.0, 1.0, 1)
        )
      ),
      width = 1,
      height = 1
    )
    val expected = new ImageData(
      pixels = List(
        List(
          PixelData(0.0, 0.5, 1.0, 1.0, 1)
        )
      ),
      width = 1,
      height = 1
    )

    gamaCorrection.render(imageData) shouldBe Right(expected)
  }

  it should "gamma = 2.2" in {
    val gamaCorrection = new GamaCorrection(true, 2.2)
    val imageData      = new ImageData(
      pixels = List(
        List(
          PixelData(0.0, 0.5, 1.0, 1.0, 1)
        )
      ),
      width = 1,
      height = 1
    )
    val expected = new ImageData(
      pixels = List(
        List(
          PixelData(0.0, 0.72974005284, 1.0, 1.0, 1)
        )
      ),
      width = 1,
      height = 1
    )

    gamaCorrection.render(imageData) match {
      case Left(_)       => fail()
      case Right(result) =>
        result
          .pixels
          .zip(expected.pixels)
          .forall(rowItem =>
            rowItem
              ._1
              .zip(rowItem._2)
              .forall(colItem =>
                (colItem._1.red - colItem._2.red < EPS) &&
                  (colItem._1.green - colItem._2.green < EPS) &&
                  (colItem._1.blue - colItem._2.blue < EPS) &&
                  (colItem._1.alpha - colItem._2.alpha < EPS) &&
                  (colItem._1.hits == colItem._2.hits)
              )
          ) shouldBe true
    }
  }
}
