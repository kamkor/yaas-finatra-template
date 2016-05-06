package me.kamkor.wishlists.domain

import org.joda.time.DateTime

case class WishList(
  id: String,
  owner: String,
  title: String,
  description: Option[String] = None,
  createdAt: Option[DateTime] = None,
  items: Seq[WishListItem] = Seq.empty
)


