package tbank.academy.scala.fractals.drawer.variations


import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers
import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper
import tbank.academy.scala.fractals.data.AffineParams

class VariationFunctionTest extends AnyFlatSpec with Matchers {
  private val ZERO_AFFINE_PARAMS = AffineParams(0, 0, 0, 0, 0, 0, 0, 0, 0)
  private val EPS = 1e-6

  it should "LinearVariationFunction test #1" in {
    LinearVariationFunction.apply(1.0, 1.0, ZERO_AFFINE_PARAMS).x shouldBe 1.0 +- EPS
  }

  it should "LinearVariationFunction test #2" in {
    LinearVariationFunction.apply(1.0, 1.0, ZERO_AFFINE_PARAMS).y shouldBe 1.0 +- EPS
  }

  it should "SinusoidalVariationFunction test #1" in {
    SinusoidalVariationFunction.apply(math.Pi / 2, math.Pi, ZERO_AFFINE_PARAMS).x shouldBe 1.0 +- EPS
  }

  it should "SinusoidalVariationFunction test #2" in {
    SinusoidalVariationFunction.apply(math.Pi / 2, math.Pi, ZERO_AFFINE_PARAMS).y shouldBe 0.0 +- EPS
  }

  it should "SphericalVariationFunction test #1" in {
    SphericalVariationFunction.apply(1.0, 1.0, ZERO_AFFINE_PARAMS).x shouldBe 0.5 +- EPS
  }

  it should "SphericalVariationFunction test #2" in {
    SphericalVariationFunction.apply(1.0, 1.0, ZERO_AFFINE_PARAMS).y shouldBe 0.5 +- EPS
  }

  it should "SwirlVariationFunction test #1" in {
    SwirlVariationFunction.apply(math.sqrt(math.Pi), 0.0, ZERO_AFFINE_PARAMS).x shouldBe 0.0 +- EPS
  }

  it should "SwirlVariationFunction test #2" in {
    SwirlVariationFunction.apply(math.sqrt(math.Pi), 0.0, ZERO_AFFINE_PARAMS).y shouldBe -math.sqrt(math.Pi) +- EPS
  }

  it should "SwirlVariationFunction test #3" in {
    SwirlVariationFunction.apply(0.0, math.sqrt(math.Pi), ZERO_AFFINE_PARAMS).x shouldBe math.sqrt(math.Pi) +- EPS
  }

  it should "SwirlVariationFunction test #4" in {
    SwirlVariationFunction.apply(0.0, math.sqrt(math.Pi), ZERO_AFFINE_PARAMS).y shouldBe 0.0 +- EPS
  }

  it should "HorseshoeVariationFunction test #1" in {
    HorseshoeVariationFunction.apply(1.0, 0.0, ZERO_AFFINE_PARAMS).x shouldBe 1.0 +- EPS
  }

  it should "HorseshoeVariationFunction test #2" in {
    HorseshoeVariationFunction.apply(1.0, 0.0, ZERO_AFFINE_PARAMS).y shouldBe 0.0 +- EPS
  }

  it should "HorseshoeVariationFunction test #3" in {
    HorseshoeVariationFunction.apply(1.0, 1.0, ZERO_AFFINE_PARAMS).x shouldBe 0.0 +- EPS
  }

  it should "HorseshoeVariationFunction test #4" in {
    HorseshoeVariationFunction.apply(1.0, 1.0, ZERO_AFFINE_PARAMS).y shouldBe math.sqrt(2) +- EPS
  }
}
