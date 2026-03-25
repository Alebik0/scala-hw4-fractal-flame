package tbank.academy.scala.fractals.drawer

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers
import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper
import tbank.academy.scala.fractals.data.{AffineParams, ImageData, PixelData, Point}
import tbank.academy.scala.fractals.drawer.variations.{LinearVariationFunction, WeightedVariationFunction}

class ChaosGameTest extends AnyFlatSpec with Matchers {
  it should "pointsHit test #1" in {
    val affineParams = List(
      AffineParams(0.0, 0.0, 0.1, 0.0, 0.0, 0.2, 0, 0, 0)
    )
    val functions = List(
      WeightedVariationFunction(1.0, LinearVariationFunction)
    )
    val chaosGame = new ChaosGame(2000, 1000, 1.0, 1234, 5, affineParams, functions)
    chaosGame.pointsHit(Point(0.0, 0.0), step = 0, acc = List()) shouldBe Right(List(
      HitData(1050, 600, 0, 0, 0),
      HitData(1050, 600, 0, 0, 0),
      HitData(1050, 600, 0, 0, 0),
      HitData(1050, 600, 0, 0, 0),
      HitData(1050, 600, 0, 0, 0),
    ))
  }

  it should "pointsHit test #2" in {
    val affineParams = List(
      AffineParams(1.0, 0.0, 0.1, 0.0, 0.0, 0.0, 0, 0, 0)
    )
    val functions = List(
      WeightedVariationFunction(1.0, LinearVariationFunction)
    )
    val chaosGame = new ChaosGame(2000, 1000, 1.0, 1234, 5, affineParams, functions)
    chaosGame.pointsHit(Point(0.0, 0.0), step = 0, acc = List()) shouldBe Right(List(
      HitData(1050, 500, 0, 0, 0),
      HitData(1100, 500, 0, 0, 0),
      HitData(1150, 500, 0, 0, 0),
      HitData(1200, 500, 0, 0, 0),
      HitData(1250, 500, 0, 0, 0),
    ))
  }

  it should "makePixel test #1" in {
    val affineParams = List(
      AffineParams(1.0, 0.0, 0.1, 0.0, 0.0, 0.0, 0, 0, 0)
    )
    val functions = List(
      WeightedVariationFunction(1.0, LinearVariationFunction)
    )
    val chaosGame = new ChaosGame(2000, 1000, 1.0, 1234, 5, affineParams, functions)
    val localHits = List(
      HitData(100, 100, 255, 0, 0),
      HitData(100, 100, 0, 255, 0),
      HitData(100, 100, 0, 0, 255),
    )
    chaosGame.makePixel(localHits) shouldBe PixelData(63, 63, 127, 255, 3)
  }

  it should "makePixel test #2" in {
    val affineParams = List(
      AffineParams(1.0, 0.0, 0.1, 0.0, 0.0, 0.0, 0, 0, 0)
    )
    val functions = List(
      WeightedVariationFunction(1.0, LinearVariationFunction)
    )
    val chaosGame = new ChaosGame(2000, 1000, 1.0, 1234, 5, affineParams, functions)
    val localHits = List(
    )
    chaosGame.makePixel(localHits) shouldBe PixelData(0, 0, 0, 255, 0)
  }

  it should "makePixel test #3" in {
    val affineParams = List(
      AffineParams(1.0, 0.0, 0.1, 0.0, 0.0, 0.0, 0, 0, 0)
    )
    val functions = List(
      WeightedVariationFunction(1.0, LinearVariationFunction)
    )
    val chaosGame = new ChaosGame(2000, 1000, 1.0, 1234, 5, affineParams, functions)
    val localHits = List(
      HitData(100, 100, 255, 0, 0),
    )
    chaosGame.makePixel(localHits) shouldBe PixelData(255, 0, 0, 255, 1)
  }

  it should "render test" in {
    val affineParams = List(
      AffineParams(0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 255, 0, 0)
    )
    val functions = List(
      WeightedVariationFunction(1.0, LinearVariationFunction)
    )
    val chaosGame = new ChaosGame(5, 3, 1.0, 1234, 1000, affineParams, functions)

    chaosGame.render() shouldBe Right(ImageData(
      pixels = List(
        List(PixelData(0, 0, 0, 255, 0), PixelData(0, 0, 0, 255, 0), PixelData(0, 0, 0, 255, 0), PixelData(0, 0, 0, 255, 0), PixelData(0, 0, 0, 255, 0)),
        List(PixelData(0, 0, 0, 255, 0), PixelData(0, 0, 0, 255, 0), PixelData(0, 0, 0, 255, 0), PixelData(255, 0, 0, 255, 1000), PixelData(0, 0, 0, 255, 0)),
        List(PixelData(0, 0, 0, 255, 0), PixelData(0, 0, 0, 255, 0), PixelData(0, 0, 0, 255, 0), PixelData(0, 0, 0, 255, 0), PixelData(0, 0, 0, 255, 0)),
      ),
      width = 5,
      height = 3
    ))
  }
}
