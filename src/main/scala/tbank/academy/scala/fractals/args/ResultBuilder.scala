package tbank.academy.scala.fractals.args

import tbank.academy.scala.fractals.data.{AffineParams, OptionalProgramArguments, WeightedFunction}
import tbank.academy.scala.fractals.errors.DomainError

class ResultBuilder(
    outputPathValue: Option[String],
    eitherWidthValue: Either[DomainError, Option[Int]],
    eitherHeightValue: Either[DomainError, Option[Int]],
    eitherSeedValue: Either[DomainError, Option[Long]],
    eitherIterationCountValue: Either[DomainError, Option[Int]],
    eitherThreadsValue: Either[DomainError, Option[Int]],
    eitherAffineParamsValue: Either[DomainError, Option[List[AffineParams]]],
    eitherFunctionsValue: Either[DomainError, Option[List[WeightedFunction]]],
    eitherSymmetryLevel: Either[DomainError, Option[Int]],
    eitherGammaCorrection: Either[DomainError, Option[Boolean]],
    eitherGamma: Either[DomainError, Option[Double]],
) {
  def build(): Either[DomainError, OptionalProgramArguments] = {
    eitherWidthValue match {
      case Left(error)       => Left(error)
      case Right(widthValue) =>
        build2(widthValue)
    }
  }

  private def build2(widthValue: Option[Int]): Either[DomainError, OptionalProgramArguments] = {
    eitherHeightValue match {
      case Left(error)        => Left(error)
      case Right(heightValue) =>
        build3(widthValue, heightValue)
    }
  }

  private def build3(
      widthValue: Option[Int],
      heightValue: Option[Int]
  ): Either[DomainError, OptionalProgramArguments] = {
    eitherSeedValue match {
      case Left(error)      => Left(error)
      case Right(seedValue) =>
        build4(widthValue, heightValue, seedValue)
    }
  }

  private def build4(
      widthValue: Option[Int],
      heightValue: Option[Int],
      seedValue: Option[Long]
  ): Either[DomainError, OptionalProgramArguments] = {
    eitherIterationCountValue match {
      case Left(error)                => Left(error)
      case Right(iterationCountValue) =>
        build5(widthValue, heightValue, seedValue, iterationCountValue)
    }
  }

  private def build5(
      widthValue: Option[Int],
      heightValue: Option[Int],
      seedValue: Option[Long],
      iterationCountValue: Option[Int]
  ): Either[DomainError, OptionalProgramArguments] = {
    eitherThreadsValue match {
      case Left(error)         => Left(error)
      case Right(threadsValue) =>
        build6(widthValue, heightValue, seedValue, iterationCountValue, threadsValue)
    }
  }

  private def build6(
      widthValue: Option[Int],
      heightValue: Option[Int],
      seedValue: Option[Long],
      iterationCountValue: Option[Int],
      threadsValue: Option[Int]
  ): Either[DomainError, OptionalProgramArguments] = {
    eitherAffineParamsValue match {
      case Left(error)              => Left(error)
      case Right(affineParamsValue) =>
        build7(widthValue, heightValue, seedValue, iterationCountValue, threadsValue, affineParamsValue)
    }
  }

  private def build7(
      widthValue: Option[Int],
      heightValue: Option[Int],
      seedValue: Option[Long],
      iterationCountValue: Option[Int],
      threadsValue: Option[Int],
      affineParamsValue: Option[List[AffineParams]]
  ): Either[DomainError, OptionalProgramArguments] = {
    eitherFunctionsValue match {
      case Left(error)           => Left(error)
      case Right(functionsValue) =>
        build8(widthValue, heightValue, seedValue, iterationCountValue, threadsValue, affineParamsValue, functionsValue)
    }
  }

  private def build8(
      widthValue: Option[Int],
      heightValue: Option[Int],
      seedValue: Option[Long],
      iterationCountValue: Option[Int],
      threadsValue: Option[Int],
      affineParamsValue: Option[List[AffineParams]],
      functionsValue: Option[List[WeightedFunction]]
  ): Either[DomainError, OptionalProgramArguments] = {
    eitherSymmetryLevel match {
      case Left(error)               => Left(error)
      case Right(symmetryLevelValue) =>
        build9(
          widthValue,
          heightValue,
          seedValue,
          iterationCountValue,
          threadsValue,
          affineParamsValue,
          functionsValue,
          symmetryLevelValue
        )
    }
  }

  private def build9(
      widthValue: Option[Int],
      heightValue: Option[Int],
      seedValue: Option[Long],
      iterationCountValue: Option[Int],
      threadsValue: Option[Int],
      affineParamsValue: Option[List[AffineParams]],
      functionsValue: Option[List[WeightedFunction]],
      symmetryLevelValue: Option[Int],
  ): Either[DomainError, OptionalProgramArguments] = {
    eitherGammaCorrection match {
      case Left(error)                 => Left(error)
      case Right(gammaCorrectionValue) =>
        build10(
          widthValue,
          heightValue,
          seedValue,
          iterationCountValue,
          threadsValue,
          affineParamsValue,
          functionsValue,
          symmetryLevelValue,
          gammaCorrectionValue
        )
    }
  }

  private def build10(
      widthValue: Option[Int],
      heightValue: Option[Int],
      seedValue: Option[Long],
      iterationCountValue: Option[Int],
      threadsValue: Option[Int],
      affineParamsValue: Option[List[AffineParams]],
      functionsValue: Option[List[WeightedFunction]],
      symmetryLevelValue: Option[Int],
      gammaCorrectionValue: Option[Boolean],
  ): Either[DomainError, OptionalProgramArguments] = {
    eitherGamma match {
      case Left(error)       => Left(error)
      case Right(gammaValue) =>
        buildFinal(
          widthValue,
          heightValue,
          seedValue,
          iterationCountValue,
          threadsValue,
          affineParamsValue,
          functionsValue,
          symmetryLevelValue,
          gammaCorrectionValue,
          gammaValue
        )
    }
  }

  private def buildFinal(
      widthValue: Option[Int],
      heightValue: Option[Int],
      seedValue: Option[Long],
      iterationCountValue: Option[Int],
      threadsValue: Option[Int],
      affineParamsValue: Option[List[AffineParams]],
      functionsValue: Option[List[WeightedFunction]],
      symmetryLevelValue: Option[Int],
      gammaCorrectionValue: Option[Boolean],
      gammaValue: Option[Double],
  ): Either[DomainError, OptionalProgramArguments] = {
    Right(
      OptionalProgramArguments(
        width = widthValue,
        height = heightValue,
        seed = seedValue,
        iterationCount = iterationCountValue,
        outputPath = outputPathValue,
        threads = threadsValue,
        affineParams = affineParamsValue,
        functions = functionsValue,
        symmetryLevel = symmetryLevelValue,
        gammaCorrection = gammaCorrectionValue,
        gamma = gammaValue
      )
    )
  }
}
