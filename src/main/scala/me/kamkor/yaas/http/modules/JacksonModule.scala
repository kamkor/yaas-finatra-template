package me.kamkor.yaas.http.modules

import com.twitter.finatra.json.modules.FinatraJacksonModule
import com.twitter.finatra.json.utils.CamelCasePropertyNamingStrategy

object JacksonModule extends FinatraJacksonModule {

  override val propertyNamingStrategy = CamelCasePropertyNamingStrategy

}
