package me.kamkor.common.filters

import com.twitter.finagle.http.{Request, Response}
import com.twitter.finagle.{Service, SimpleFilter}
import com.twitter.util.Future

case class YaasAware(hybrisTenant: String)

class YaasAwareFilter extends SimpleFilter[Request, Response] {
  override def apply(request: Request, service: Service[Request, Response]): Future[Response] = {
    YaasAwareContext.setYaasAware(request)
    service(request)
  }
}

// create a context, see https://twitter.github.io/finatra/user-guide/build-new-http-server/filter.html
object YaasAwareContext {

  private val YaasAwareField = Request.Schema.newField[YaasAware]

  implicit class YaasAwareContextSyntax(val request: Request) extends AnyVal {
    def yaasAware: YaasAware = request.ctx(YaasAwareField)
  }

  private[filters] def setYaasAware(request: Request): Unit = {
    val hybrisTenant = request.headerMap("hybris-tenant")
    val yaasAware = YaasAware(hybrisTenant)
    request.ctx.update(YaasAwareField, yaasAware)
  }

}
