package me.kamkor.wishlists.repository

import com.twitter.util.Future
import me.kamkor.wishlists.domain.WishList
import me.kamkor.wishlists.domain.http.WishListPutOrPostRequest

trait WishListsRepository {

  def create(postedWishList: WishListPutOrPostRequest): Future[WishList]

  def get(id: String): Future[Option[WishList]]

  def update(puttedWishList: WishListPutOrPostRequest): Future[WishList]

  def delete(id: String): Future[Unit]

}
