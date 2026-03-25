package tbank.academy.scala.fractals.drawer.variations

import tbank.academy.scala.fractals.data.{AffineParams, Point}

case class HorseshoeVariationFunction(override val weight: Double) extends VariationFunction {
  private def r(x: Double, y: Double): Double = math.sqrt(x * x + y * y)

  override def apply(x: Double, y: Double, params: AffineParams): Point =
    Point(
      (x - y) * (x + y),
      2.0 * x * y
    ).times(1.0 / r(x, y))
}
