package me.kamkor.yaas.http.filters

import com.twitter.finagle.http.{Request, Response}
import com.twitter.finagle.{Service, SimpleFilter}
import com.twitter.util.Future

case class YaasHeaders(hybrisTenant: String)

class YaasHeadersFilter extends SimpleFilter[Request, Response] {
  override def apply(request: Request, service: Service[Request, Response]): Future[Response] = {
    YaasHeadersContext.setYaasHeaders(request)
    service(request)
  }
}

// create a context, see https://twitter.github.io/finatra/user-guide/build-new-http-server/filter.html
object YaasHeadersContext {

  private val YaasHeadersField = Request.Schema.newField[YaasHeaders]

  implicit class YaasAwareContextSyntax(val request: Request) extends AnyVal {
    def yaasHeaders: YaasHeaders = request.ctx(YaasHeadersField)
  }

  private[filters] def setYaasHeaders(request: Request): Unit = {
    val hybrisTenant = request.headerMap("hybris-tenant")
    val yaasAware = YaasHeaders(hybrisTenant)
    request.ctx.update(YaasHeadersField, yaasAware)
  }

}
