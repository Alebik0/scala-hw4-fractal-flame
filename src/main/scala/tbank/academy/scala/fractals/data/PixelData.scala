package tbank.academy.scala.fractals.data

case class PixelData(red: Int,
                     green: Int,
                     blue: Int,
                     alpha: Int,
                     hits: Int) {
  def toInt: Int = (alpha & 0xFF) << 24 | (red & 0xFF) << 16 | (green & 0xFF) << 8 | (blue & 0xFF)

  def merge(red: Int, green: Int, blue: Int): PixelData =
    PixelData(
      red = (this.red + red) / 2,
      green = (this.green + green) / 2,
      blue = (this.blue + blue) / 2,
      alpha = this.alpha,
      hits = this.hits + 1
    )

  def join(other: PixelData): PixelData = {
    if (hits == 0)
      other
    else if (other.hits == 0)
      this
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
