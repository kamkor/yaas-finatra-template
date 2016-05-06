package me.kamkor.yaas.oauth2

import javax.inject.{Inject, Singleton}

import com.twitter.finagle.http.Status._
import com.twitter.finagle.http.{Message, ProxyCredentials}
import com.twitter.finatra.httpclient.RequestBuilder
import com.twitter.finatra.json.FinatraObjectMapper
import com.twitter.inject.Logging
import com.twitter.util.Future
import me.kamkor.finatra.httpclient.HttpClient
import me.kamkor.yaas.http.exceptions.ValidationException
import me.kamkor.yaas.oauth2.model.{AccessToken, ClientCredentials}

@Singleton
class OAuthService @Inject()(
  @OAuthClient client: HttpClient,
  mapper: FinatraObjectMapper
) extends Logging {

  def getToken(credentials: ClientCredentials): Future[AccessToken] = {
    val request = RequestBuilder.post("token")

    request.authorization = new ProxyCredentials(credentials.clientId, credentials.clientSecret).basicAuthorization

    val scopeParam = if (credentials.scope.isEmpty) "" else s"&scope=${credentials.scope.mkString(" ")}"

    request.body(s"grant_type=client_credentials$scopeParam", Message.ContentTypeWwwFrom)

    client.execute(request) map { response =>
      response.status match {
        case Successful(code) => FinatraObjectMapper.parseResponseBody(response, mapper.reader[AccessToken])
        //case Forbidden => FIXME
        //case NotFound => FIXME
        case _ => {
          throw new ValidationException(response.contentString)
        }
      }
    }
  }

}
