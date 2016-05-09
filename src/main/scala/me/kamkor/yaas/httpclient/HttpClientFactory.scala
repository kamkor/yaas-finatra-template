package me.kamkor.yaas.httpclient

import java.net.URL
import javax.inject.{Inject, Singleton}

import com.twitter.finagle.Http
import com.twitter.finagle.http.ProxyCredentials
import me.kamkor.finatra.httpclient.HttpClient
import me.kamkor.yaas.httpclient.filters.AccessTokenFilter
import me.kamkor.yaas.oauth2.OAuthService
import me.kamkor.yaas.oauth2.model.ClientCredentials

@Singleton
class HttpClientFactory @Inject()(oauthService: OAuthService) {

  def newYaasProxyClient(
    defaultClientCredentials: ClientCredentials,
    url: URL,
    defaultHeaders: Map[String, String] = Map.empty,
    finagleHttpClient: Option[Http.Client] = None): HttpClient = {

    val filteredClient =
      finagleHttpClient
        .getOrElse(HttpClient.DefaultFinagleClient)
        .filtered(new AccessTokenFilter(oauthService, defaultClientCredentials))

    HttpClient(url = url, defaultHeaders = defaultHeaders, finagleHttpClient = filteredClient)
  }

  def newBasicAuthClient(
    proxyCredentials: ProxyCredentials,
    url: URL,
    defaultHeaders: Map[String, String] = Map.empty,
    finagleHttpClient: Option[Http.Client] = None
  ): HttpClient = ???

}
