package me.kamkor.yaas.oauth.model

import org.joda.time.DateTime

case class Token(token: String, expiresAt: DateTime)

