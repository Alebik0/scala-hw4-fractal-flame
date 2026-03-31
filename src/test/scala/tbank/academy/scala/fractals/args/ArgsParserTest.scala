package tbank.academy.scala.fractals.args

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers
import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper
import tbank.academy.scala.fractals.data.{AffineParams, OptionalProgramArguments, ProgramArguments, WeightedFunction}
import tbank.academy.scala.fractals.errors.DomainError

import java.io.File

class ArgsParserTest extends AnyFlatSpec with Matchers {
  it should "args parser test #0" in {
    val parser = new ArgsParser()
    parser.parse(List()) shouldBe Right(ProgramArguments(
      width = 1920,
      height = 1080,
      seed = 5,
      iterationCount = 2500,
      outputPath = "result.png",
      threads = 1,
      affineParams = List(),
      functions = List(),
      symmetryLevel = 1,
      gammaCorrection = false,
      gamma = 2.2
    ))
  }

  it should "args parser test #1" in {
    val parser = new ArgsParser()
    parser.parse(
      ("--seed 1234 " +
        "-i 50000 " +
        "-t 1 " +
        "-ap 1.116,0.081,0.941,-0.880,0.817,0.573/0.856,0.406,-0.444,-0.267,1.463,-0.414/-0.066,0.917,0.803,-1.232,-0.490,-1.376/0.337,1.106,0.601,-0.953,-1.453,-0.750/0.106,0.711,-1.410,-1.431,1.277,-0.904 " +
        "-f linear:0.4,spherical:0.3,sinusoidal:0.3 " +
        "-s 4 " +
        "-g true " +
        "--gamma 2.0").split(" ").toList
    ) shouldBe Right(ProgramArguments(
      1920,
      1080,
      1234,
      50000,
      "result.png",
      1,
      List(
        AffineParams(1.116, 0.081, 0.941, -0.880, 0.817, 0.573),
        AffineParams(0.856, 0.406, -0.444, -0.267, 1.463, -0.414),
        AffineParams(-0.066, 0.917, 0.803, -1.232, -0.490, -1.376),
        AffineParams(0.337, 1.106, 0.601, -0.953, -1.453, -0.750),
        AffineParams(0.106, 0.711, -1.410, -1.431, 1.277, -0.904),
      ),
      List(
        WeightedFunction(0.4, "linear"),
        WeightedFunction(0.3, "spherical"),
        WeightedFunction(0.3, "sinusoidal"),
      ),
      symmetryLevel = 4,
      gammaCorrection = true,
      gamma = 2.0
    ))
  }

  it should "args parser test: invalid seed" in {
    val parser = new ArgsParser()
    parser.parse(
      ("--seed qwerty " +
        "-i 50000 " +
        "-t 1 " +
        "-ap 1.116,0.081,0.941,-0.880,0.817,0.573/0.856,0.406,-0.444,-0.267,1.463,-0.414/-0.066,0.917,0.803,-1.232,-0.490,-1.376/0.337,1.106,0.601,-0.953,-1.453,-0.750/0.106,0.711,-1.410,-1.431,1.277,-0.904 " +
        "-f linear:0.4,spherical:0.3,sinusoidal:0.3").split(" ").toList
    ).isLeft shouldBe true
  }

  it should "args parser test: invalid iteration count, non-number" in {
    val parser = new ArgsParser()
    parser.parse(
      ("-i qwertry " +
        "-t 1 " +
        "-ap 1.116,0.081,0.941,-0.880,0.817,0.573/0.856,0.406,-0.444,-0.267,1.463,-0.414/-0.066,0.917,0.803,-1.232,-0.490,-1.376/0.337,1.106,0.601,-0.953,-1.453,-0.750/0.106,0.711,-1.410,-1.431,1.277,-0.904 " +
        "-f linear:0.4,spherical:0.3,sinusoidal:0.3").split(" ").toList
    ).isLeft shouldBe true
  }

  it should "args parser test: invalid iteration count, negative number" in {
    val parser = new ArgsParser()
    parser.parse(
      ("-i -1000 " +
        "-t 1 " +
        "-ap 1.116,0.081,0.941,-0.880,0.817,0.573/0.856,0.406,-0.444,-0.267,1.463,-0.414/-0.066,0.917,0.803,-1.232,-0.490,-1.376/0.337,1.106,0.601,-0.953,-1.453,-0.750/0.106,0.711,-1.410,-1.431,1.277,-0.904 " +
        "-f linear:0.4,spherical:0.3,sinusoidal:0.3").split(" ").toList
    ).isLeft shouldBe true
  }

  it should "args parser test: invalid iteration count, zero number" in {
    val parser = new ArgsParser()
    parser.parse(
      ("-i 0 " +
        "-t 1 " +
        "-ap 1.116,0.081,0.941,-0.880,0.817,0.573/0.856,0.406,-0.444,-0.267,1.463,-0.414/-0.066,0.917,0.803,-1.232,-0.490,-1.376/0.337,1.106,0.601,-0.953,-1.453,-0.750/0.106,0.711,-1.410,-1.431,1.277,-0.904 " +
        "-f linear:0.4,spherical:0.3,sinusoidal:0.3").split(" ").toList
    ).isLeft shouldBe true
  }

  it should "args parser test: invalid threads count, non-number" in {
    val parser = new ArgsParser()
    parser.parse(
      ("-t qwerty " +
        "-ap 1.116,0.081,0.941,-0.880,0.817,0.573/0.856,0.406,-0.444,-0.267,1.463,-0.414/-0.066,0.917,0.803,-1.232,-0.490,-1.376/0.337,1.106,0.601,-0.953,-1.453,-0.750/0.106,0.711,-1.410,-1.431,1.277,-0.904 " +
        "-f linear:0.4,spherical:0.3,sinusoidal:0.3").split(" ").toList
    ).isLeft shouldBe true
  }

  it should "args parser test: invalid threads count, negative number" in {
    val parser = new ArgsParser()
    parser.parse(
      ("-t -1 " +
        "-ap 1.116,0.081,0.941,-0.880,0.817,0.573/0.856,0.406,-0.444,-0.267,1.463,-0.414/-0.066,0.917,0.803,-1.232,-0.490,-1.376/0.337,1.106,0.601,-0.953,-1.453,-0.750/0.106,0.711,-1.410,-1.431,1.277,-0.904 " +
        "-f linear:0.4,spherical:0.3,sinusoidal:0.3").split(" ").toList
    ).isLeft shouldBe true
  }

  it should "args parser test: invalid threads count, zero number" in {
    val parser = new ArgsParser()
    parser.parse(
      ("-t 0 " +
        "-ap 1.116,0.081,0.941,-0.880,0.817,0.573/0.856,0.406,-0.444,-0.267,1.463,-0.414/-0.066,0.917,0.803,-1.232,-0.490,-1.376/0.337,1.106,0.601,-0.953,-1.453,-0.750/0.106,0.711,-1.410,-1.431,1.277,-0.904 " +
        "-f linear:0.4,spherical:0.3,sinusoidal:0.3").split(" ").toList
    ).isLeft shouldBe true
  }

  it should "args parser test: invalid affine params" in {
    val parser = new ArgsParser()
    parser.parse(
      ("-ap qwerty " +
        "-f linear:0.4,spherical:0.3,sinusoidal:0.3").split(" ").toList
    ).isLeft shouldBe true
  }

  it should "args parser test: invalid functions" in {
    val parser = new ArgsParser()
    parser.parse("-f qwerrtyy".split(" ").toList).isLeft shouldBe true
  }

  it should "args parser test: invalid symmetry level, non-number" in {
    val parser = new ArgsParser()
    parser.parse("-s qwerty".split(" ").toList).isLeft shouldBe true
  }

  it should "args parser test: invalid symmetry level, negative number" in {
    val parser = new ArgsParser()
    parser.parse("-s -1".split(" ").toList).isLeft shouldBe true
  }

  it should "args parser test: invalid symmetry level, zero number" in {
    val parser = new ArgsParser()
    parser.parse("-s 0".split(" ").toList).isLeft shouldBe true
  }

  it should "args parser test: invalid gamma correction" in {
    val parser = new ArgsParser()
    parser.parse("-g qwerty".split(" ").toList).isLeft shouldBe true
  }

  it should "args parser test: invalid gamma, non-number" in {
    val parser = new ArgsParser()
    parser.parse("--gamma qwerty".split(" ").toList).isLeft shouldBe true
  }

  it should "args parser test: invalid gamma, negative number" in {
    val parser = new ArgsParser()
    parser.parse("--gamma -1.0".split(" ").toList).isLeft shouldBe true
  }

  it should "args parser test: invalid gamma, zero number" in {
    val parser = new ArgsParser()
    parser.parse("--gamma 0.0".split(" ").toList).isLeft shouldBe true
  }

  it should "args parser test: invalid command line format, no value provided" in {
    val parser = new ArgsParser()
    parser.parse("-f".split(" ").toList).isLeft shouldBe true
  }

  it should "args parser test: invalid command line format, no key provided" in {
    val parser = new ArgsParser()
    parser.parse("amogus".split(" ").toList).isLeft shouldBe true
  }

  it should "json config args parser test #1" in {
    val jsonConfig =
      """{
        |  "size": {
        |    "width": 1920,
        |    "height": 1080
        |  },
        |  "iteration_count": 2500,
        |  "output_path": "result.png",
        |  "threads": 4,
        |  "functions": [
        |    {
        |      "name": "swirl",
        |      "weight": 1.0
        |    },
        |    {
        |      "name": "horseshoe",
        |      "weight": 0.7
        |    }
        |  ],
        |  "affine_params": [
        |    {
        |      "a": 1.0,
        |      "b": 1.0,
        |      "c": 1.0,
        |      "d": 1.0,
        |      "e": 1.0,
        |      "f": 1.0
        |    },
        |    {
        |      "a": 0.3,
        |      "b": 1.0,
        |      "c": -0.2,
        |      "d": 0.4,
        |      "e": 1.0,
        |      "f": 1.0
        |    }
        |  ],
        |  "symmetry_level": 4,
        |  "gamma_correction": true,
        |  "gamma": 2.0
        |}"""
    JsonConfigArgsParser.parseJsonConfig(jsonConfig.stripMargin) shouldBe Right(OptionalProgramArguments(
      Some(1920),
      Some(1080),
      None,
      Some(2500),
      Some("result.png"),
      Some(4),
      Some(List(AffineParams(1.0, 1.0, 1.0, 1.0, 1.0, 1.0), AffineParams(0.3, 1.0, -0.2, 0.4, 1.0, 1.0))),
      Some(List(WeightedFunction(1.0, "swirl"), WeightedFunction(0.7, "horseshoe"))),
      Some(4),
      Some(true),
      Some(2.0),
    ))
  }

  it should "json config args parser test #2" in {
    val configSource = getClass.getResource("/config.json")
    val file         = new File(configSource.getPath).getAbsolutePath

    JsonConfigArgsParser.parse(Map("--config" -> file)) shouldBe Right(OptionalProgramArguments(
      Some(1920),
      Some(1080),
      None,
      Some(2500),
      Some("result.png"),
      Some(4),
      Some(List(AffineParams(1.0, 1.0, 1.0, 1.0, 1.0, 1.0), AffineParams(0.3, 1.0, -0.2, 0.4, 1.0, 1.0))),
      Some(List(WeightedFunction(1.0, "swirl"), WeightedFunction(0.7, "horseshoe"))),
      None,
      None,
      None,
    ))
  }

  it should "json config args parser test #3" in {
    val configSource = getClass.getResource("/config_invalid.json")
    val file         = new File(configSource.getPath).getAbsolutePath

    JsonConfigArgsParser.parse(Map("--config" -> file)).isLeft shouldBe true
  }

  it should "json config args parser test #4" in {
    val configSource = getClass.getResource("/config_invalid.json")
    val file         = new File(configSource.getPath).getAbsolutePath
    val parser       = new ArgsParser()
    parser.parse(
      ("--seed 1234 " +
        "-i 50000 " +
        "-t 1 " +
        "-ap 1.116,0.081,0.941,-0.880,0.817,0.573/0.856,0.406,-0.444,-0.267,1.463,-0.414/-0.066,0.917,0.803,-1.232,-0.490,-1.376/0.337,1.106,0.601,-0.953,-1.453,-0.750/0.106,0.711,-1.410,-1.431,1.277,-0.904 " +
        "-f linear:0.4,spherical:0.3,sinusoidal:0.3 " +
        "-s 4 " +
        "-g true " +
        "--gamma 2.0 " +
        "--config " + file).split(" ").toList
    ).isLeft shouldBe true
  }

  it should "affine params parser test #1" in {
    AffineParamsArgsParser.parse("1.0,1.0,1.0,1.0,1.0,1.0") shouldBe Right(AffineParams(1.0, 1.0, 1.0, 1.0, 1.0, 1.0))
  }

  it should "affine params parser test #2" in {
    AffineParamsArgsParser.parse("qwerty").isLeft shouldBe true
  }

  it should "affine params parser test #3" in {
    AffineParamsArgsParser.parse("1.0,1.0,1.0,1.0,1.0,qwerty").isLeft shouldBe true
  }

  it should "affine params parser test #4" in {
    AffineParamsArgsParser.parse("1.0,1.0,1.0,1.0,qwerty,qwerty").isLeft shouldBe true
  }

  it should "affine params parser test #5" in {
    AffineParamsArgsParser.parse("1.0,1.0,1.0,qwerty,qwerty,qwerty").isLeft shouldBe true
  }

  it should "affine params parser test #6" in {
    AffineParamsArgsParser.parse("1.0,1.0,qwerty,qwerty,qwerty,qwerty").isLeft shouldBe true
  }

  it should "affine params parser test #7" in {
    AffineParamsArgsParser.parse("1.0,qwerty,qwerty,qwerty,qwerty,qwerty").isLeft shouldBe true
  }

  it should "affine params parser test #8" in {
    AffineParamsArgsParser.parse("qwerty,qwerty,qwerty,qwerty,qwerty,qwerty").isLeft shouldBe true
  }

  it should "functions params parser test #1" in {
    FunctionsArgsParser.parse("sinusoidal:0.3") shouldBe Right(WeightedFunction(0.3, "sinusoidal"))
  }

  it should "functions params parser test #2" in {
    FunctionsArgsParser.parse("qwerty").isLeft shouldBe true
  }

  it should "functions params parser test #3" in {
    FunctionsArgsParser.parse("sinusoidal:qwerty").isLeft shouldBe true
  }

  it should "result builder throws first error #1" in {
    new ResultBuilder(
      None,
      Left(TestDomainError(1)),
      Left(TestDomainError(2)),
      Left(TestDomainError(3)),
      Left(TestDomainError(4)),
      Left(TestDomainError(5)),
      Left(TestDomainError(6)),
      Left(TestDomainError(7)),
      Left(TestDomainError(8)),
      Left(TestDomainError(9)),
      Left(TestDomainError(10)),
    ).build() shouldBe Left(TestDomainError(1))
  }

  it should "result builder throws first error #2" in {
    new ResultBuilder(
      None,
      Right(None),
      Left(TestDomainError(2)),
      Left(TestDomainError(3)),
      Left(TestDomainError(4)),
      Left(TestDomainError(5)),
      Left(TestDomainError(6)),
      Left(TestDomainError(7)),
      Left(TestDomainError(8)),
      Left(TestDomainError(9)),
      Left(TestDomainError(10)),
    ).build() shouldBe Left(TestDomainError(2))
  }

  it should "result builder throws first error #3" in {
    new ResultBuilder(
      None,
      Right(None),
      Right(None),
      Left(TestDomainError(3)),
      Left(TestDomainError(4)),
      Left(TestDomainError(5)),
      Left(TestDomainError(6)),
      Left(TestDomainError(7)),
      Left(TestDomainError(8)),
      Left(TestDomainError(9)),
      Left(TestDomainError(10)),
    ).build() shouldBe Left(TestDomainError(3))
  }

  it should "result builder throws first error #4" in {
    new ResultBuilder(
      None,
      Right(None),
      Right(None),
      Right(None),
      Left(TestDomainError(4)),
      Left(TestDomainError(5)),
      Left(TestDomainError(6)),
      Left(TestDomainError(7)),
      Left(TestDomainError(8)),
      Left(TestDomainError(9)),
      Left(TestDomainError(10)),
    ).build() shouldBe Left(TestDomainError(4))
  }

  it should "result builder throws first error #5" in {
    new ResultBuilder(
      None,
      Right(None),
      Right(None),
      Right(None),
      Right(None),
      Left(TestDomainError(5)),
      Left(TestDomainError(6)),
      Left(TestDomainError(7)),
      Left(TestDomainError(8)),
      Left(TestDomainError(9)),
      Left(TestDomainError(10)),
    ).build() shouldBe Left(TestDomainError(5))
  }

  it should "result builder throws first error #6" in {
    new ResultBuilder(
      None,
      Right(None),
      Right(None),
      Right(None),
      Right(None),
      Right(None),
      Left(TestDomainError(6)),
      Left(TestDomainError(7)),
      Left(TestDomainError(8)),
      Left(TestDomainError(9)),
      Left(TestDomainError(10)),
    ).build() shouldBe Left(TestDomainError(6))
  }

  it should "result builder throws first error #7" in {
    new ResultBuilder(
      None,
      Right(None),
      Right(None),
      Right(None),
      Right(None),
      Right(None),
      Right(None),
      Left(TestDomainError(7)),
      Left(TestDomainError(8)),
      Left(TestDomainError(9)),
      Left(TestDomainError(10)),
    ).build() shouldBe Left(TestDomainError(7))
  }

  it should "result builder throws first error #8" in {
    new ResultBuilder(
      None,
      Right(None),
      Right(None),
      Right(None),
      Right(None),
      Right(None),
      Right(None),
      Right(None),
      Left(TestDomainError(8)),
      Left(TestDomainError(9)),
      Left(TestDomainError(10)),
    ).build() shouldBe Left(TestDomainError(8))
  }

  it should "result builder throws first error #9" in {
    new ResultBuilder(
      None,
      Right(None),
      Right(None),
      Right(None),
      Right(None),
      Right(None),
      Right(None),
      Right(None),
      Right(None),
      Left(TestDomainError(9)),
      Left(TestDomainError(10)),
    ).build() shouldBe Left(TestDomainError(9))
  }

  it should "result builder throws first error #10" in {
    new ResultBuilder(
      None,
      Right(None),
      Right(None),
      Right(None),
      Right(None),
      Right(None),
      Right(None),
      Right(None),
      Right(None),
      Right(None),
      Left(TestDomainError(10)),
    ).build() shouldBe Left(TestDomainError(10))
  }

  it should "result builder throws first error #11" in {
    new ResultBuilder(
      None,
      Right(None),
      Right(None),
      Right(None),
      Right(None),
      Right(None),
      Right(None),
      Right(None),
      Right(None),
      Right(None),
      Right(None),
    ).build() shouldBe Right(OptionalProgramArguments(None, None, None, None, None, None, None, None, None, None, None))
  }
}

private case class TestDomainError(id: Int) extends DomainError
