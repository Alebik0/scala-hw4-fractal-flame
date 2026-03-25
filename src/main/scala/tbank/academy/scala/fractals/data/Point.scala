package tbank.academy.scala.fractals.data

case class Point(x: Double, y: Double) {

  def times(weight: Double): Point = Point(x * weight, y * weight)

  def add(point: Point): Point = Point(x + point.x, y + point.y)
}

object Point {
  def zero: Point = Point(0, 0)
}
