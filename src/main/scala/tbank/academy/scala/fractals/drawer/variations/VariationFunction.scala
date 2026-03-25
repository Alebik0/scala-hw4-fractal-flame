package tbank.academy.scala.fractals.drawer.variations

import tbank.academy.scala.fractals.data.{AffineParams, Point}

trait VariationFunction {
  def apply(x: Double, y: Double, params: AffineParams): Point
}
