package tbank.academy.scala.fractals.drawer.variations

import tbank.academy.scala.fractals.data.{AffineParams, Point}

object LinearVariationFunction extends VariationFunction {
  override def apply(x: Double, y: Double, params: AffineParams): Point =
    Point(x, y)
}
