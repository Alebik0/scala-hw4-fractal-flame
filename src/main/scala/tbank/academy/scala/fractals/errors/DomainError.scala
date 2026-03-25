package tbank.academy.scala.fractals.errors

import io.circe

trait DomainError

case class JavaError(javaError: Throwable) extends DomainError

case class UnexpectedError(description: String) extends DomainError

case class NoArgValueFoundError(description: String) extends DomainError
case class NoArgKeyFoundError(description: String) extends DomainError
case class InvalidArgsError(description: String) extends DomainError

case class JsonParseError(error: circe.Error) extends DomainError
