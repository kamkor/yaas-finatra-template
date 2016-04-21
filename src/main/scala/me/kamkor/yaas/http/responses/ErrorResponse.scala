package me.kamkor.yaas.http.responses

/**
  *
  * @param status
  * @param `type`
  * @param message
  * @param moreInfo
  * @see https://api.yaas.io/patterns/v1/schema-error-message.json
  */
case class ErrorResponse(
  status: Int,
  `type`: Option[String] = None,
  message: String,
  moreInfo: Option[String] = None
  // details: Seq[ErrorDetail] FIXME
)
