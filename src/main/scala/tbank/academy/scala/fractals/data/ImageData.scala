package tbank.academy.scala.fractals.data

case class ImageData(pixels: List[List[PixelData]],
                     width: Int,
                     height: Int)

object ImageData {
  def empty(width: Int, height: Int): ImageData = ImageData(
    pixels = (0 until width)
      .map(
        _ =>
          (0 until height)
            .map(_ => PixelData.empty)
            .toList
      )
      .toList,
    width = width,
    height = height
  )
}