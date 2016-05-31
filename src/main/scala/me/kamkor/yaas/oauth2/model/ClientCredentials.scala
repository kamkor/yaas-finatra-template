package me.kamkor.yaas.oauth2.model

import com.twitter.finagle.http.ProxyCredentials

case class ClientCredentials(clientId: String, clientSecret: String, scope: Seq[String]) {

  lazy val basicAuthorization = new ProxyCredentials(clientId, clientSecret).basicAuthorization

  def addScopes(scope: Seq[String]): ClientCredentials = {
    this.copy(scope = this.scope ++ scope)
  }

  def addTenantScope(tenant: String): ClientCredentials = {
    addScopes(Seq(s"hybris.tenant=$tenant"))
  }

}
