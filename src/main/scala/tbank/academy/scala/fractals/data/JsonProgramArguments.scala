package tbank.academy.scala.fractals.data

case class JsonProgramArguments(
    size: Option[JsonSizeArguments],
    seed: Option[Long],
    iteration_count: Option[Int],
    output_path: Option[String],
    threads: Option[Int],
    affine_params: Option[List[AffineParams]],
    functions: Option[List[WeightedFunction]],
    symmetry_level: Option[Int],
    gamma_correction: Option[Boolean],
    gamma: Option[Double]
)
