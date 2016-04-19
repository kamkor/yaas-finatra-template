package me.kamkor.wishlists.controllers

import javax.inject.{Inject, Singleton}

import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller
import com.twitter.util.Future
import me.kamkor.wishlists.domain.http.{WishListPutOrPostRequest}
import me.kamkor.wishlists.repository.WishListsRepository

@Singleton
class WishListsController @Inject()(wishListsRepository: WishListsRepository) extends Controller {

  post("/wishlists") { request: WishListPutOrPostRequest =>
    wishListsRepository.create(request) map { wishList =>
      response
        .created(wishList)
        .location(wishList.id)
    }
  }

  get("/wishlists") { request: Request =>
    Future(response.internalServerError("FIXME"))
  }

  get("/wishlists/:id") { request: Request =>
    wishListsRepository.get(request.getParam("id"))
  }

  put("/wishlists/:id") { request: WishListPutOrPostRequest =>
    wishListsRepository.update(request) map (_ => response.noContent)
  }

  delete("/wishlists/:id") { request: Request =>
    Future(response.internalServerError("FIXME"))
  }

}
