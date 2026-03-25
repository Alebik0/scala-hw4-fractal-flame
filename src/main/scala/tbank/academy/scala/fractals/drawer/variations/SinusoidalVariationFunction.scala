package tbank.academy.scala.fractals.drawer.variations

import tbank.academy.scala.fractals.data.{AffineParams, Point}

case class SinusoidalVariationFunction(override val weight: Double) extends VariationFunction {
  override def apply(x: Double, y: Double, params: AffineParams): Point =
    Point(math.sin(x), math.sin(y))
}
