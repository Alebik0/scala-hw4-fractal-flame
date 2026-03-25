package tbank.academy.scala.fractals.errors

sealed trait DomainError

case class JavaError(javaError: Throwable) extends DomainError

case class UnexpectedError(description: String) extends DomainError
