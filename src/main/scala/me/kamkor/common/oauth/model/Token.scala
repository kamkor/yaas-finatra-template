package me.kamkor.common.oauth.model

import org.joda.time.DateTime

case class Token(token: String, expiresAt: DateTime)

