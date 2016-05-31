package me.kamkor.yaas.proxy

import com.github.benmanes.caffeine.cache.{Cache, Caffeine}
import com.twitter.conversions.time._
import com.twitter.finagle.http.{Request, Response}
import com.twitter.finatra.utils.FutureUtils
import com.twitter.util.Future
import me.kamkor.finatra.httpclient.HttpClient
import me.kamkor.yaas.oauth2.OAuthService
import me.kamkor.yaas.oauth2.model.{AccessToken, ClientCredentials}

object YaasProxyClient {
  def apply(oauthService: OAuthService, httpClient: HttpClient) = new YaasProxyClient(oauthService, httpClient)
}

class YaasProxyClient(oauthService: OAuthService, httpClient: HttpClient) {

  val cache: Cache[ClientCredentials, AccessToken] = Caffeine.newBuilder()
    .build()

  def execute(request: Request)(implicit clientCredentials: ClientCredentials): Future[Response] = {
    getToken(clientCredentials) flatMap { token =>
      request.authorization = token.bearerAuthorization
      httpClient.execute(request)
    }
  }

  private def getToken(credentials: ClientCredentials): Future[AccessToken] = {
    Option(cache.getIfPresent(credentials)) match {
      case Some(token) => Future.value(token)
      case None => refreshToken(credentials)
    }
  }

  private def refreshToken(credentials: ClientCredentials): Future[AccessToken] = {
    def invalidateCache(): Future[Unit] = Future(cache.invalidate(credentials))

    oauthService.getToken(credentials) map { token =>
      cache.put(credentials, token)
      val invalidateIn = token.expiresIn - Math.round((token.expiresIn * 0.2))
      FutureUtils.scheduleFuture(invalidateIn.seconds)(invalidateCache)
      token
    }
  }

}
