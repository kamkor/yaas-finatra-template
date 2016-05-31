package me.kamkor.wishlists.repository.document

import com.twitter.finagle.http.Status.{NotFound, Successful}
import com.twitter.finagle.http._
import com.twitter.finatra.http.exceptions.InternalServerErrorException
import com.twitter.finatra.httpclient.RequestBuilder
import com.twitter.finatra.json.FinatraObjectMapper
import com.twitter.inject.Logging
import com.twitter.util.Future
import me.kamkor.finatra.httpclient.HttpClient
import me.kamkor.wishlists.domain.WishList
import me.kamkor.wishlists.repository.WishListsRepository
import me.kamkor.yaas.oauth2.model.ClientCredentials
import me.kamkor.yaas.proxy.YaasProxyClient

/**
  * Uses multi-tenant YaaS.io document service to store WishLists.
  *
  * https://devportal.yaas.io/services/document/latest/index.html
  */
class DocumentWishListsRepository(client: YaasProxyClient, mapper: FinatraObjectMapper, clientCredentials: ClientCredentials)
  extends WishListsRepository with Logging {

  implicit val defaultClientCredentials = clientCredentials

  override def get(tenant: String, id: String): Future[Option[WishList]] = {
    val getRequest = RequestBuilder.get(path(tenant, id))

    client.execute(getRequest) map { response =>
      response.status match {
        case Successful(code) => Some(mapper.parse[WishList](response.contentString))
        case NotFound => None
        case _ => {
          error(s"Could not get wishlist for tenant '$tenant' and id: '$id', ${getErrorMessage(response)}")
          throw InternalServerErrorException("Backing service error")
        }
      }
    }
  }

  override def update(tenant: String, wishList: WishList): Future[WishList] = {
    val uri = Request.queryString(path(tenant, wishList.id), "upsert" -> "true")
    val putRequest = RequestBuilder
      .put(uri)
      .body(mapper.writeValueAsString(wishList))

    client.execute(putRequest) map { response =>
      response.status match {
        case Successful(code) => wishList
        case _ => {
          error(s"Could not update wishlist '$wishList' for tenant '$tenant', ${getErrorMessage(response)}")
          throw InternalServerErrorException("Backing service error")
        }
      }
    }
  }

  override def delete(tenant: String, id: String): Future[Unit] = {
    val deleteRequest = RequestBuilder.delete(path(tenant, id))

    client.execute(deleteRequest) map { response =>
      response.status match {
        case Successful(code) => ()
        case _ => {
          error(s"Could not delete wishlist for tenant '$tenant' and id: '$id', ${getErrorMessage(response)}")
          throw InternalServerErrorException("Backing service error")
        }
      }
    }
  }

  private def getErrorMessage(response: Response) =
    s"Document service returned status'${response.status}' and body '${response.contentString}."

  private val hybrisClient = "stork.yaas-finatra-template"

  private def path(tenant: String, id: String) = s"/$tenant/$hybrisClient/data/wishlists/$id"

}
