package tbank.academy.scala.fractals.drawer.variations

import tbank.academy.scala.fractals.data.{AffineParams, Point}

case class SwirlVariationFunction(override val weight: Double) extends VariationFunction {
  private def r(x: Double, y: Double): Double = math.sqrt(x * x + y * y)

  override def apply(x: Double, y: Double, params: AffineParams): Point =
    Point(
      x = x * math.sin(r(x, y) * r(x, y)) - y * math.cos(r(x, y) * r(x, y)),
      y = x * math.cos(r(x, y) * r(x, y)) + y * math.sin(r(x, y) * r(x, y)),
    )
}
