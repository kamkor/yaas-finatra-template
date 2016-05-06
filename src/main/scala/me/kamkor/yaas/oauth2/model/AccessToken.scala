package me.kamkor.yaas.oauth2.model

import com.fasterxml.jackson.annotation.JsonProperty

case class AccessToken(
  @JsonProperty("access_token") accessToken: String,
  @JsonProperty("expires_in") expiresIn: Int,
  scope: Option[String]) {

}

