package tbank.academy.scala.fractals.data

case class PixelData(
    red: Int,
    green: Int,
    blue: Int,
    alpha: Int,
    hits: Int
) {
  def toInt: Int = (alpha & 0xff) << 24 | (red & 0xff) << 16 | (green & 0xff) << 8 | (blue & 0xff)

  def merge(r: Int, g: Int, b: Int): PixelData =
    PixelData(
      red = (red + r) / 2,
      green = (green + g) / 2,
      blue = (blue + b) / 2,
      alpha = alpha,
      hits = hits + 1
    )

  def join(other: PixelData): PixelData = {
    if (hits == 0)
      PixelData(other.red, other.green, other.blue, other.alpha, other.hits)
    else if (other.hits == 0)
      PixelData(red, green, blue, alpha, hits)
    else
      PixelData(
        (red + other.red) / 2,
        (green + other.green) / 2,
        (blue + other.blue) / 2,
        (alpha + other.alpha) / 2,
        hits + other.hits,
      )
  }
}

object PixelData {
  def empty: PixelData = PixelData(0, 0, 0, 255, 0)
}
