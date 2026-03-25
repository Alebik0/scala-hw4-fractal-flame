package tbank.academy.scala.fractals.data

case class ImageData(
    pixels: List[List[PixelData]],
    width: Int,
    height: Int
) {

  def join(value: ImageData): ImageData =
    ImageData(
      pixels
        .zip(value.pixels)
        .map {
          rowZipped =>
            rowZipped
              ._1
              .zip(rowZipped._2)
              .map {
                cellZipped =>
                  cellZipped
                    ._1
                    .join(cellZipped._2)
              }
        },
      width,
      height
    )
}

object ImageData {
  def empty(width: Int, height: Int): ImageData =
    ImageData(List.fill(height, width)(PixelData.empty), width, height)
}
