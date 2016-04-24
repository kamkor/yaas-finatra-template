package me.kamkor.wishlists

import com.twitter.finagle.http.Status
import com.twitter.finatra.http.test.{EmbeddedHttpServer, HttpTest}
import com.twitter.inject.Mockito
import com.twitter.inject.server.FeatureTest

object WishListsFeatureTest {

  val TestTenant = "TestTenant"
  val Headers = Map("hybris-tenant" -> TestTenant)

}

class WishListsFeatureTest extends FeatureTest with Mockito with HttpTest {

  import WishListsFeatureTest._

  override val server = new EmbeddedHttpServer(new WishListsServer)

  "A WishLists endpoint" should {

    "GET wishlist" in {
      //mock document repository client
      //server.httpGet(path = s"/$TestTenant/wishlists/1", headers = Headers, andExpect = Status.Ok)
    }

    "GET NotFound" in {

    }

    "PUT wishlist" in {
      server.httpPut(
        path = s"/$TestTenant/wishlists/1",
        headers = Headers,
        putBody =
          """
            |{
            |  "owner":"kamil",
            |  "title":"food list",
            |  "description":"Food for the weekend"
            |}
          """.stripMargin,
        andExpect = Status.NoContent
      )
    }

    "DELETE wishlist" in {
      server.httpDelete(path = s"/$TestTenant/wishlists/1", headers = Headers, andExpect = Status.NoContent)
    }
  }


}
