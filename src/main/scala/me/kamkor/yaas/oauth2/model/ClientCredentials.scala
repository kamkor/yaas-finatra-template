package me.kamkor.yaas.oauth2.model

case class ClientCredentials(clientId: String, clientSecret: String, scope: Seq[String])
