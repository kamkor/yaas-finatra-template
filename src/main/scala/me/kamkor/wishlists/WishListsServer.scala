package me.kamkor.wishlists

import com.twitter.finatra.http.HttpServer
import com.twitter.finatra.http.filters.CommonFilters
import com.twitter.finatra.http.routing.HttpRouter
import me.kamkor.wishlists.controllers.WishListsController
import me.kamkor.wishlists.repository.memory.InMemoryWishListsRepositoryModule
import me.kamkor.yaas.http.filters.{YaasHeadersFilter, YaasHeadersTenantConsistentWithRouteFilter}
import me.kamkor.yaas.http.modules.{ExceptionMapperModule, JacksonModule}
import me.kamkor.yaas.oauth.OAuthModule

object WishListsServerMain extends WishListsServer

/*
FIXME TODO
FIXME circuit breaker to document repository
FIXME endpoint tests
FIXME MDC logging with propagation
FIXME configurability with ENV variables
FIXME deployment
FIXME error handling (yaas error message)
FIXME YaaS Aware object (hybris-tenant and other parameters send by YaaS api proxy)
FIXME api console and raml

 */

class WishListsServer extends HttpServer {

  override val modules = Seq(oauthModule, wishListsRepositoryModule)

  def oauthModule = OAuthModule

  def wishListsRepositoryModule = InMemoryWishListsRepositoryModule

  override def jacksonModule = JacksonModule

  override def exceptionMapperModule = ExceptionMapperModule

  override def configureHttp(router: HttpRouter): Unit = {
    router
      .filter[CommonFilters]
      .filter[YaasHeadersFilter]
      .add[YaasHeadersTenantConsistentWithRouteFilter, WishListsController]
  }

}
