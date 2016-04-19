package me.kamkor.wishlists.repository.memory

import java.util.UUID

import com.twitter.util.Future
import me.kamkor.wishlists.domain.WishList
import me.kamkor.wishlists.domain.http.WishListPutOrPostRequest
import me.kamkor.wishlists.repository.WishListsRepository

class InMemoryWishListsRepository extends WishListsRepository {

  //Map

  def create(newWishList: WishListPutOrPostRequest): Future[WishList] = {
    Future(newWishList.toDomain(UUID.randomUUID().toString))
  }

  def get(id: String): Future[Option[WishList]] = ???

  def delete(id: String): Future[Unit] = ???

  def update(updatedWishList: WishListPutOrPostRequest): Future[WishList] = {
    Future(updatedWishList.toDomain())
  }
}
