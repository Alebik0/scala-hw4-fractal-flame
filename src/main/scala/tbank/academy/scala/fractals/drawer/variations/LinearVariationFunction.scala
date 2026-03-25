package tbank.academy.scala.fractals.drawer.variations

import tbank.academy.scala.fractals.data.{AffineParams, Point}

case class LinearVariationFunction(override val weight: Double) extends VariationFunction {
  override def apply(x: Double, y: Double, params: AffineParams): Point =
    Point(x, y)
}
