package me.kamkor.wishlists.controllers

import javax.inject.{Inject, Singleton}

import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller
import com.twitter.inject.Logging
import me.kamkor.wishlists.domain.http.WishListPutRequest
import me.kamkor.wishlists.repository.WishListsRepository


@Singleton
class WishListsController @Inject()(
  wishListsRepository: WishListsRepository
) extends Controller with Logging {

  get("/:tenant/wishlists/:id") { request: Request =>
    wishListsRepository.get(request.getParam("tenant"), request.getParam("id"))
  }

  put("/:tenant/wishlists/:id") { request: WishListPutRequest =>
    wishListsRepository.update(request.tenant, request.toDomain()) map (_ => response.noContent)
  }

  delete("/:tenant/wishlists/:id") { request: Request =>
    wishListsRepository.delete(request.getParam("tenant"), request.getParam("id")) map (_ => response.noContent)
  }

}
