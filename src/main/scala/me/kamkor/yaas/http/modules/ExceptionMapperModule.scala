package me.kamkor.yaas.http.modules

import com.twitter.finatra.http.exceptions.DefaultExceptionMapper
import com.twitter.finatra.http.internal.exceptions.json.JsonParseExceptionMapper
import com.twitter.finatra.http.internal.exceptions.{ExceptionManager, FinatraDefaultExceptionMapper}
import com.twitter.inject.{Injector, InjectorModule, TwitterModule}
import me.kamkor.yaas.http.exceptions.ValidationExceptionMapper
import me.kamkor.yaas.http.exceptions.json.CaseClassExceptionMapper

object ExceptionMapperModule extends TwitterModule {

  override def modules = Seq(InjectorModule)

  override def configure(): Unit = {
    bindSingleton[DefaultExceptionMapper].to[FinatraDefaultExceptionMapper]
  }

  override def singletonStartup(injector: Injector): Unit = {
    val manager = injector.instance[ExceptionManager]
    manager.add[JsonParseExceptionMapper]
    manager.add[CaseClassExceptionMapper]
    manager.add[ValidationExceptionMapper]
  }

}
