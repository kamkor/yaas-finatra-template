package me.kamkor.yaas.http.exceptions

import javax.inject.Inject

import com.twitter.finagle.http.{Request, Response}
import com.twitter.finatra.http.exceptions.{ExceptionMapper, HttpException}
import com.twitter.finatra.http.response.ResponseBuilder
import me.kamkor.yaas.http.responses.ErrorResponse

class HttpExceptionMapper @Inject()(
  response: ResponseBuilder
) extends ExceptionMapper[HttpException] {

  override def toResponse(request: Request, e: HttpException): Response = {
    val errorMessage = e.errors.mkString("\n")
    response.status(e.statusCode).json(ErrorResponse(status = e.statusCode.code, message = errorMessage))
  }

}
