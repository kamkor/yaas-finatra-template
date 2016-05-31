package me.kamkor.yaas.oauth2.model

import com.fasterxml.jackson.annotation.JsonProperty
import org.joda.time.DateTime

import scala.concurrent.duration._

case class AccessToken(
  @JsonProperty("access_token") accessToken: String,
  @JsonProperty("expires_in") expiresIn: Int,
  scope: Option[String]) {

  private[this] val expiresAt = DateTime.now.plus(expiresIn.seconds.minus(30.seconds).toMillis)

  lazy val bearerAuthorization = s"Bearer ${accessToken}"

  def isExpired: Boolean = expiresAt.isBeforeNow

}

