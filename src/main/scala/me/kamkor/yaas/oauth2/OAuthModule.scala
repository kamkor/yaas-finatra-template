package me.kamkor.yaas.oauth2

import java.net.URL

import com.google.inject.{Provides, Singleton}
import com.twitter.inject.TwitterModule
import me.kamkor.finatra.httpclient.HttpClient

object OAuthModule extends TwitterModule {

  val oauthUrl = flag("oauth.url", "https://api.yaas.io/hybris/oauth2/v1", "url of the yaas oauth2")

  @Singleton
  @Provides
  @OAuthClient
  def provideOAuthClient(): HttpClient = HttpClient(new URL(oauthUrl()))

}
