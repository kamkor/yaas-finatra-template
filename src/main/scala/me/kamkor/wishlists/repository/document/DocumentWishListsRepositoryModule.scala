package me.kamkor.wishlists.repository.document

import java.net.URL

import com.google.inject.{Provides, Singleton}
import com.twitter.finatra.json.FinatraObjectMapper
import com.twitter.inject.TwitterModule
import me.kamkor.finatra.httpclient.HttpClient
import me.kamkor.wishlists.repository.WishListsRepository
import me.kamkor.yaas.oauth2.OAuthService
import me.kamkor.yaas.oauth2.model.ClientCredentials
import me.kamkor.yaas.proxy.YaasProxyClient

object DocumentWishListsRepositoryModule extends TwitterModule {

  val documentUrl = flag("document.url", "https://api.yaas.io/hybris/document/v1", "destination of the yaas document service")
  val documentClientId = flag("document.clientId", "clientId", "client id for the document service")
  val documentClientSecret = flag("document.clientSecret", "clientSecret", "client secret for the document service")

  @Singleton
  @Provides
  @DocumentClient
  def provideDocumentClient(oauthService: OAuthService): YaasProxyClient = {
    val httpClient = HttpClient(new URL(documentUrl()))
    val proxyClient = new YaasProxyClient(oauthService, httpClient)
    proxyClient
  }

  @Singleton
  @Provides
  def providesWishListsRepository(oauthService: OAuthService, mapper: FinatraObjectMapper): WishListsRepository = {
    val httpClient = HttpClient(new URL(documentUrl()))
    val proxyClient = new YaasProxyClient(oauthService, httpClient)

    val clientCredentials = ClientCredentials(
      documentClientId(), documentClientSecret(), Seq("hybris.document_view", "hybris.document_manage"))
    new DocumentWishListsRepository(proxyClient, mapper, clientCredentials)
  }

}
