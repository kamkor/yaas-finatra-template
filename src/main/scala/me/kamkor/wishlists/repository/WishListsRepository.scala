package me.kamkor.wishlists.repository

import com.twitter.util.Future
import me.kamkor.wishlists.domain.WishList

trait WishListsRepository {

  def get(tenant: String, id: String): Future[Option[WishList]]

  def update(tenant: String, wishList: WishList): Future[WishList]

  def delete(tenant: String, id: String): Future[Unit]

}
