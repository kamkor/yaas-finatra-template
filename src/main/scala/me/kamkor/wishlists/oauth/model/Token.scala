package me.kamkor.wishlists.oauth.model

import org.joda.time.DateTime

case class Token(token: String, expiresAt: DateTime)

