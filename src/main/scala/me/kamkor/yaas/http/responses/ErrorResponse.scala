package me.kamkor.yaas.http.responses

/**
  *
  * @param status
  * @param `type`
  * @param message
  * @see https://api.yaas.io/patterns/v1/schema-error-message.json
  */
case class ErrorResponse(
  status: Int,
  `type`: Option[String] = None,
  message: String
)
