package tbank.academy.scala.fractals.data

case class JsonProgramArguments(
                                 size: Option[JsonSizeArguments],
                                 seed: Option[Long],
                                 iterationCount: Option[Int],
                                 outputPath: Option[String],
                                 threads: Option[Int],
                                 affineParams: List[AffineParams],
                                 functions: List[WeightedFunction],
                               )
