package me.kamkor.wishlists.modules

import com.twitter.finatra.json.modules.FinatraJacksonModule
import com.twitter.finatra.json.utils.CamelCasePropertyNamingStrategy

object WishListsJacksonModule extends FinatraJacksonModule {

  override val propertyNamingStrategy = CamelCasePropertyNamingStrategy

}
