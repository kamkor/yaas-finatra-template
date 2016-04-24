package me.kamkor.wishlists.domain.http

import com.twitter.finatra.request.RouteParam
import com.twitter.finatra.validation._
import me.kamkor.wishlists.domain.{WishList, WishListItem}
import org.joda.time.DateTime

// FIXME use http object for WishListItem and convert it with toDomain

case class WishListPutRequest(
  @RouteParam
  tenant: String,
  @RouteParam
  id: String,
  @NotEmpty
  owner: String,
  @NotEmpty
  title: String,
  description: Option[String],
  createdAt: Option[DateTime],
  items: Seq[WishListItem] = Seq.empty
) {

  def toDomain(): WishList =
    WishList(
      id = id,
      owner = owner,
      title = title,
      description = description,
      createdAt = createdAt,
      items = items)

}


