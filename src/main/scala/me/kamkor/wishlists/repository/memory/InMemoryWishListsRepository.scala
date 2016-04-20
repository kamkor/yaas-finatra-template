package me.kamkor.wishlists.repository.memory

import com.twitter.util.Future
import me.kamkor.wishlists.domain.WishList
import me.kamkor.wishlists.repository.WishListsRepository

/**
  * Artificial in memory storage for playing around.
  */
class InMemoryWishListsRepository extends WishListsRepository {

  private case class WishListKey(tenant: String, id: String)

  @volatile private var storage: Map[WishListKey, WishList] = Map.empty

  def get(tenant: String, id: String): Future[Option[WishList]] = Future {
    storage.get(WishListKey(tenant, id))
  }

  def delete(tenant: String, id: String): Future[Unit] = Future {
    storage -= WishListKey(tenant, id)
  }

  def update(tenant: String, wishList: WishList): Future[WishList] = Future {
    storage += (WishListKey(tenant, wishList.id) -> wishList)
    wishList
  }

}
