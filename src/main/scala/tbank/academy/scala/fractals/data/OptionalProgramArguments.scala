package tbank.academy.scala.fractals.data

case class OptionalProgramArguments(
    width: Option[Int],
    height: Option[Int],
    seed: Option[Long],
    iterationCount: Option[Int],
    outputPath: Option[String],
    threads: Option[Int],
    affineParams: Option[List[AffineParams]],
    functions: Option[List[WeightedFunction]],
    symmetryLevel: Option[Int],
)

object OptionalProgramArguments {
  def empty: OptionalProgramArguments =
    OptionalProgramArguments(None, None, None, None, None, None, None, None, None)
}
