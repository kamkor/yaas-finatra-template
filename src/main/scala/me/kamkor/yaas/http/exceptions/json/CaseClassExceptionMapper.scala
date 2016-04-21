package me.kamkor.yaas.http.exceptions.json

import javax.inject.Inject

import com.twitter.finagle.http.{Request, Response, Status}
import com.twitter.finatra.http.exceptions.ExceptionMapper
import com.twitter.finatra.http.response.ResponseBuilder
import com.twitter.finatra.json.internal.caseclass.exceptions.CaseClassMappingException
import me.kamkor.yaas.http.responses.ErrorResponse

class CaseClassExceptionMapper @Inject()(
  response: ResponseBuilder
) extends ExceptionMapper[CaseClassMappingException] {

  override def toResponse(request: Request, e: CaseClassMappingException): Response = {
    // FIXME, do it properly
    val errorMessage = e.errors map (_.getMessage) mkString ("\n")

    val errorResponse =
      ErrorResponse(
        status = Status.BadRequest.code,
        message = errorMessage
      )

    response.badRequest.json(errorResponse)
  }


}
