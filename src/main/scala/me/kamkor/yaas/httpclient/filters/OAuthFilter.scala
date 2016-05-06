package me.kamkor.yaas.httpclient.filters

import com.twitter.finagle.http.{Request, Response}
import com.twitter.finagle.{Service, SimpleFilter}
import com.twitter.inject.Logging
import com.twitter.util.Future
import me.kamkor.yaas.oauth2.OAuthService
import me.kamkor.yaas.oauth2.model.ClientCredentials

private[httpclient] class OAuthFilter(oauthService: OAuthService, credentials: ClientCredentials) extends SimpleFilter[Request, Response] with Logging {
  override def apply(request: Request, service: Service[Request, Response]): Future[Response] =
    oauthService.getToken(credentials) flatMap { token =>
      request.authorization = s"Bearer ${token.accessToken}"
      service(request)
    }
}
