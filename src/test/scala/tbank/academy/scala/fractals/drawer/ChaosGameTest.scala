package tbank.academy.scala.fractals.drawer

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers
import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper
import tbank.academy.scala.fractals.data.{AffineParams, Point}
import tbank.academy.scala.fractals.drawer.variations.{LinearVariationFunction, WeightedVariationFunction}

class ChaosGameTest  extends AnyFlatSpec with Matchers {
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
}
