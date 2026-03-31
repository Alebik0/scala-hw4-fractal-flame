package tbank.academy.scala.fractals.data

case class PixelData(
    red: Double,
    green: Double,
    blue: Double,
    alpha: Double,
    hits: Int
) {
  def toInt: Int = {
    val alphaInt = math.min((256 * alpha).toInt, 255)
    val redInt   = math.min((256 * red).toInt, 255)
    val greenInt = math.min((256 * green).toInt, 255)
    val blueInt  = math.min((256 * blue).toInt, 255)

    (alphaInt & 0xff) << 24 | (redInt & 0xff) << 16 | (greenInt & 0xff) << 8 | (blueInt & 0xff)
  }

  def merge(r: Double, g: Double, b: Double): PixelData =
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
  def empty: PixelData = PixelData(0, 0, 0, 1, 0)
}
