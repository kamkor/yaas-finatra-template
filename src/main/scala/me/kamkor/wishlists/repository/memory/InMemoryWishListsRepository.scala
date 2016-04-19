package me.kamkor.wishlists.repository.memory

import java.util.UUID

import com.twitter.util.Future
import me.kamkor.wishlists.domain.WishList
import me.kamkor.wishlists.domain.http.WishListPutOrPostRequest
import me.kamkor.wishlists.repository.WishListsRepository

class InMemoryWishListsRepository extends WishListsRepository {

  @volatile private var storage: Map[String, WishList] = Map.empty

  def create(postedWishList: WishListPutOrPostRequest): Future[WishList] = Future {
    val id = UUID.randomUUID().toString
    val wishList = postedWishList.toDomain(id)
    upsert(wishList)
    wishList
  }

  def get(id: String): Future[Option[WishList]] = Future(storage.get(id))

  def delete(id: String): Future[Unit] = Future {
    storage -= id
    ()
  }

  def update(puttedWishList: WishListPutOrPostRequest): Future[WishList] = Future {
    val wishList = puttedWishList.toDomain()
    upsert(wishList)
    wishList
  }

  private def upsert(wishList: WishList): Unit = storage += (wishList.id -> wishList)

}
