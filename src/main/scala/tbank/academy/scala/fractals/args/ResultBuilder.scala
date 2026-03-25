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
                     eitherFunctionsValue: Either[DomainError, Option[List[WeightedFunction]]]
                   ) {
  def build(): Either[DomainError, OptionalProgramArguments] = {
    eitherWidthValue match {
      case Left(error) => Left(error)
      case Right(widthValue) =>
        build2(widthValue)
    }
  }

  private def build2(widthValue: Option[Int]) = {
    eitherHeightValue match {
      case Left(error) => Left(error)
      case Right(heightValue) =>
        build3(widthValue, heightValue)
    }
  }

  private def build3(widthValue: Option[Int], heightValue: Option[Int]) = {
    eitherSeedValue match {
      case Left(error) => Left(error)
      case Right(seedValue) =>
        build4(widthValue, heightValue, seedValue)
    }
  }

  private def build4(widthValue: Option[Int], heightValue: Option[Int], seedValue: Option[Long]) = {
    eitherIterationCountValue match {
      case Left(error) => Left(error)
      case Right(iterationCountValue) =>
        build5(widthValue, heightValue, seedValue, iterationCountValue)
    }
  }

  private def build5(widthValue: Option[Int], heightValue: Option[Int], seedValue: Option[Long], iterationCountValue: Option[Int]) = {
    eitherThreadsValue match {
      case Left(error) => Left(error)
      case Right(threadsValue) =>
        build6(widthValue, heightValue, seedValue, iterationCountValue, threadsValue)
    }
  }

  private def build6(widthValue: Option[Int], heightValue: Option[Int], seedValue: Option[Long], iterationCountValue: Option[Int], threadsValue: Option[Int]) = {
    eitherAffineParamsValue match {
      case Left(error) => Left(error)
      case Right(affineParamsValue) =>
        build7(widthValue, heightValue, seedValue, iterationCountValue, threadsValue, affineParamsValue)
    }
  }

  private def build7(widthValue: Option[Int], heightValue: Option[Int], seedValue: Option[Long], iterationCountValue: Option[Int], threadsValue: Option[Int], affineParamsValue: Option[List[AffineParams]]) = {
    eitherFunctionsValue match {
      case Left(error) => Left(error)
      case Right(functionsValue) =>
        build8(widthValue, heightValue, seedValue, iterationCountValue, threadsValue, affineParamsValue, functionsValue)
    }
  }

  private def build8(widthValue: Option[Int], heightValue: Option[Int], seedValue: Option[Long], iterationCountValue: Option[Int], threadsValue: Option[Int], affineParamsValue: Option[List[AffineParams]], functionsValue: Option[List[WeightedFunction]]) = {
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
      )
    )
  }
}
