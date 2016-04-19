package me.kamkor.wishlists.domain.http

import javax.inject.Inject

import com.twitter.finagle.http.Request
import com.twitter.finatra.validation._
import me.kamkor.wishlists.domain.{WishList, WishListItem}
import org.joda.time.DateTime

//I didn't find a better way to handle both Put and Post request in the same DTO class using Finatra mechanism
// FIXME use http object for WishListItem and convert it with toDomain

case class WishListPutOrPostRequest(
  @Inject
  request: Request,
  @NotEmpty
  owner: String,
  @NotEmpty
  title: String,
  url: Option[String],
  description: Option[String],
  createdAt: Option[DateTime],
  items: Seq[WishListItem] = Seq.empty
) {

  def toDomain(): WishList = toDomain(id = request.getParam("id"))

  def toDomain(id: String): WishList =
    WishList(
      id = id,
      owner = owner,
      title = title,
      url = url,
      description = description,
      createdAt = createdAt,
      items = items)
}


