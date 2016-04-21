package me.kamkor.yaas.http.exceptions

import javax.inject.Inject

import com.twitter.finagle.http.{Request, Response, Status}
import com.twitter.finatra.http.exceptions.ExceptionMapper
import com.twitter.finatra.http.response.ResponseBuilder
import me.kamkor.yaas.http.responses.ErrorResponse

class ValidationExceptionMapper @Inject()(
  response: ResponseBuilder
) extends ExceptionMapper[ValidationException] {

  override def toResponse(request: Request, e: ValidationException): Response =
    response.badRequest.json(ErrorResponse(status = Status.BadRequest.code, message = e.getMessage))

}
