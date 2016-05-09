package me.kamkor.yaas.oauth2.model

import com.twitter.finagle.http.ProxyCredentials

case class ClientCredentials(clientId: String, clientSecret: String, scope: Seq[String]) {
  lazy val basicAuthorization = new ProxyCredentials(clientId, clientSecret).basicAuthorization
}
