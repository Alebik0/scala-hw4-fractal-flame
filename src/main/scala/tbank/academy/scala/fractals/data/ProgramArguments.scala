package tbank.academy.scala.fractals.data

case class ProgramArguments(
    width: Int,
    height: Int,
    seed: Long,
    iterationCount: Int,
    outputPath: String,
    threads: Int,
    affineParams: List[AffineParams],
    functions: List[WeightedFunction],
    symmetryLevel: Int
) {
  def edit(update: OptionalProgramArguments): ProgramArguments =
    ProgramArguments(
      update.width.getOrElse(width),
      update.height.getOrElse(height),
      update.seed.getOrElse(seed),
      update.iterationCount.getOrElse(iterationCount),
      update.outputPath.getOrElse(outputPath),
      update.threads.getOrElse(threads),
      update.affineParams.getOrElse(affineParams),
      update.functions.getOrElse(functions),
      update.symmetryLevel.getOrElse(symmetryLevel),
    )
}
