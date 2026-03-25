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
                           ) {
  def edit(update: OptionalProgramArguments): ProgramArguments =
    ProgramArguments(
      update.width.getOrElse(this.width),
      update.height.getOrElse(this.height),
      update.seed.getOrElse(this.seed),
      update.iterationCount.getOrElse(this.iterationCount),
      update.outputPath.getOrElse(this.outputPath),
      update.threads.getOrElse(this.threads),
      update.affineParams.getOrElse(this.affineParams),
      update.functions.getOrElse(this.functions),
    )
}
