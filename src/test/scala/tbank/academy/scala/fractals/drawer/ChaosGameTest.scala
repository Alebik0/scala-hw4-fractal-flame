package tbank.academy.scala.fractals.drawer

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers
import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper
import tbank.academy.scala.fractals.data.{AffineParams, ColoredAffineParams, ImageData, PixelData, Point}
import tbank.academy.scala.fractals.drawer.variations.{LinearVariationFunction, WeightedVariationFunction}

class ChaosGameTest extends AnyFlatSpec with Matchers {
  it should "pointsHit test #1" in {
    val affineParams = List(
      ColoredAffineParams(AffineParams(0.0, 0.0, 0.1, 0.0, 0.0, 0.2), 0, 0, 0)
    )
    val functions = List(
      WeightedVariationFunction(1.0, LinearVariationFunction)
    )
    val chaosGame = new ChaosGameThread(2000, 1000, 1.0, 1234, 5, affineParams, functions, symmetryLevel = 1)
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
      ColoredAffineParams(AffineParams(1.0, 0.0, 0.1, 0.0, 0.0, 0.0), 0, 0, 0)
    )
    val functions = List(
      WeightedVariationFunction(1.0, LinearVariationFunction)
    )
    val chaosGame = new ChaosGameThread(2000, 1000, 1.0, 1234, 5, affineParams, functions, symmetryLevel = 1)
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
      ColoredAffineParams(AffineParams(1.0, 0.0, 0.1, 0.0, 0.0, 0.0), 0, 0, 0)
    )
    val functions = List(
      WeightedVariationFunction(1.0, LinearVariationFunction)
    )
    val chaosGame = new ChaosGameThread(2000, 1000, 1.0, 1234, 5, affineParams, functions, symmetryLevel = 1)
    val localHits = List(
      HitData(100, 100, 1.0, 0, 0),
      HitData(100, 100, 0, 1.0, 0),
      HitData(100, 100, 0, 0, 1.0),
    )
    chaosGame.makePixel(localHits) shouldBe PixelData(0.25, 0.25, 0.5, 1.0, 3)
  }

  it should "makePixel test #2" in {
    val affineParams = List(
      ColoredAffineParams(AffineParams(1.0, 0.0, 0.1, 0.0, 0.0, 0.0), 0, 0, 0)
    )
    val functions = List(
      WeightedVariationFunction(1.0, LinearVariationFunction)
    )
    val chaosGame = new ChaosGameThread(2000, 1000, 1.0, 1234, 5, affineParams, functions, symmetryLevel = 1)
    val localHits =
      List(
      )
    chaosGame.makePixel(localHits) shouldBe PixelData(0, 0, 0, 1.0, 0)
  }

  it should "makePixel test #3" in {
    val affineParams = List(
      ColoredAffineParams(AffineParams(1.0, 0.0, 0.1, 0.0, 0.0, 0.0), 0, 0, 0)
    )
    val functions = List(
      WeightedVariationFunction(1.0, LinearVariationFunction)
    )
    val chaosGame = new ChaosGameThread(2000, 1000, 1.0, 1234, 5, affineParams, functions, symmetryLevel = 1)
    val localHits = List(
      HitData(100, 100, 1.0, 0, 0),
    )
    chaosGame.makePixel(localHits) shouldBe PixelData(1.0, 0, 0, 1.0, 1)
  }

  it should "render test" in {
    val affineParams = List(
      ColoredAffineParams(AffineParams(0.0, 0.0, 1.0, 0.0, 0.0, 0.0), 1.0, 0, 0)
    )
    val functions = List(
      WeightedVariationFunction(1.0, LinearVariationFunction)
    )
    val chaosGame = new ChaosGameThread(5, 3, 1.0, 1234, 1000, affineParams, functions, symmetryLevel = 1)

    chaosGame.render() shouldBe Right(ImageData(
      pixels = List(
        List(
          PixelData(0, 0, 0, 1.0, 0),
          PixelData(0, 0, 0, 1.0, 0),
          PixelData(0, 0, 0, 1.0, 0),
          PixelData(0, 0, 0, 1.0, 0),
          PixelData(0, 0, 0, 1.0, 0)
        ),
        List(
          PixelData(0, 0, 0, 1.0, 0),
          PixelData(0, 0, 0, 1.0, 0),
          PixelData(0, 0, 0, 1.0, 0),
          PixelData(1.0, 0, 0, 1.0, 1000),
          PixelData(0, 0, 0, 1.0, 0)
        ),
        List(
          PixelData(0, 0, 0, 1.0, 0),
          PixelData(0, 0, 0, 1.0, 0),
          PixelData(0, 0, 0, 1.0, 0),
          PixelData(0, 0, 0, 1.0, 0),
          PixelData(0, 0, 0, 1.0, 0)
        ),
      ),
      width = 5,
      height = 3
    ))
  }

  it should "render test multithreaded" in {
    val affineParams = List(
      ColoredAffineParams(AffineParams(0.0, 0.0, 1.0, 0.0, 0.0, 0.0), 1.0, 0, 0)
    )
    val functions = List(
      WeightedVariationFunction(1.0, LinearVariationFunction)
    )
    val chaosGame = new ChaosGame(5, 3, 1.0, 1234, 4, 1000, affineParams, functions, 1)

    chaosGame.render() shouldBe Right(ImageData(
      pixels = List(
        List(
          PixelData(0, 0, 0, 1.0, 0),
          PixelData(0, 0, 0, 1.0, 0),
          PixelData(0, 0, 0, 1.0, 0),
          PixelData(0, 0, 0, 1.0, 0),
          PixelData(0, 0, 0, 1.0, 0)
        ),
        List(
          PixelData(0, 0, 0, 1.0, 0),
          PixelData(0, 0, 0, 1.0, 0),
          PixelData(0, 0, 0, 1.0, 0),
          PixelData(1.0, 0, 0, 1.0, 1000),
          PixelData(0, 0, 0, 1.0, 0)
        ),
        List(
          PixelData(0, 0, 0, 1.0, 0),
          PixelData(0, 0, 0, 1.0, 0),
          PixelData(0, 0, 0, 1.0, 0),
          PixelData(0, 0, 0, 1.0, 0),
          PixelData(0, 0, 0, 1.0, 0)
        ),
      ),
      width = 5,
      height = 3
    ))
  }
}
