package me.kamkor.yaas.oauth2

import javax.inject.{Inject, Singleton}

import com.twitter.finagle.http.Message
import com.twitter.finagle.http.Status._
import com.twitter.finatra.http.exceptions.InternalServerErrorException
import com.twitter.finatra.httpclient.RequestBuilder
import com.twitter.finatra.json.FinatraObjectMapper
import com.twitter.inject.Logging
import com.twitter.util.Future
import me.kamkor.finatra.httpclient.HttpClient
import me.kamkor.yaas.oauth2.model.{AccessToken, ClientCredentials}

@Singleton
class OAuthService @Inject()(
  @OAuthClient client: HttpClient,
  mapper: FinatraObjectMapper
) extends Logging {

  def getToken(credentials: ClientCredentials): Future[AccessToken] = {
    val postRequest = RequestBuilder
      .post("token")
      .body(createTokenForm(credentials), Message.ContentTypeWwwFrom)

    postRequest.authorization = credentials.basicAuthorization

    client.execute(postRequest) map { response =>
      response.status match {
        case Successful(code) => mapper.parse[AccessToken](response.contentString)
        case _ => {
          error(s"OAuth2 service returned status'${response.status}' and body '${response.contentString}.")
          throw InternalServerErrorException("Backing service error")
        }
      }
    }
  }

  private def createTokenForm(credentials: ClientCredentials): String = {
    val scopeParam = if (credentials.scope.isEmpty) "" else s"&scope=${credentials.scope.mkString(" ")}"
    s"grant_type=client_credentials$scopeParam"
  }

}
