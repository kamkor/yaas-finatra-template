package me.kamkor.wishlists.repository.memory

import com.google.inject.{Provides, Singleton}
import com.twitter.inject.TwitterModule
import me.kamkor.wishlists.repository.WishListsRepository

object InMemoryWishListsRepositoryModule extends TwitterModule {

  @Singleton
  @Provides
  def providesWishListsRepository: WishListsRepository = {
    new InMemoryWishListsRepository()
  }

}
