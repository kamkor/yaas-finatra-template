package me.kamkor.wishlists.repository.document

import com.twitter.finagle.http.Status.{NotFound, Successful}
import com.twitter.finagle.http._
import com.twitter.finatra.httpclient.RequestBuilder
import com.twitter.finatra.json.FinatraObjectMapper
import com.twitter.inject.Logging
import com.twitter.util.Future
import me.kamkor.finatra.httpclient.HttpClient
import me.kamkor.wishlists.domain.WishList
import me.kamkor.wishlists.repository.WishListsRepository
import me.kamkor.yaas.http.exceptions.ValidationException

/**
  * Uses multi-tenant YaaS.io document service to store WishLists.
  *
  * https://devportal.yaas.io/services/document/latest/index.html
  */
class DocumentWishListsRepository(client: HttpClient, mapper: FinatraObjectMapper)
  extends WishListsRepository with Logging {

  override def get(tenant: String, id: String): Future[Option[WishList]] = {
    val request = RequestBuilder.get(path(tenant, id))

    client.execute(request) map { response =>
      response.status match {
        case Successful(code) => Some(FinatraObjectMapper.parseResponseBody(response, mapper.reader[WishList]))
        case NotFound => None
        case _ => throw new ValidationException(response.contentString) // FIXME
      }
    }
  }

  override def update(tenant: String, wishList: WishList): Future[WishList] = {
    val uri = Request.queryString(path(tenant, wishList.id), "upsert" -> "true")
    val request = RequestBuilder.put(uri)
    request.body(mapper.writeValueAsString(wishList))

    client.execute(request) map { response =>
      response.status match {
        case Successful(code) => wishList
        case _ => throw new ValidationException(response.contentString) // FIXME
      }
    }
  }

  override def delete(tenant: String, id: String): Future[Unit] = {
    val request = RequestBuilder.delete(path(tenant, id))

    client.execute(request) map { response =>
      response.status match {
        case Successful(code) => ()
        case _ => throw new ValidationException(response.contentString) // FIXME
      }
    }
  }

  private val hybrisClient = "stork.yaas-finatra-template"

  private def path(tenant: String, id: String) = s"/$tenant/$hybrisClient/data/wishlists/$id"

}
