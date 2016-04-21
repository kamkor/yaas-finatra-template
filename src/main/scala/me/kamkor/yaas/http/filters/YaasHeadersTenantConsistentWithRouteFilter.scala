package me.kamkor.yaas.http.filters

import com.twitter.finagle.http.{Request, Response}
import com.twitter.finagle.{Service, SimpleFilter}
import com.twitter.util.Future
import me.kamkor.yaas.http.exceptions.ValidationException
import me.kamkor.yaas.http.filters.YaasHeadersContext._

class YaasHeadersTenantConsistentWithRouteFilter(routeParamName: String) extends SimpleFilter[Request, Response] {

  def this() = this("tenant")

  override def apply(request: Request, service: Service[Request, Response]): Future[Response] = {
    checkIfHeaderTenantConsistentWithRoute(request)
    service(request)
  }

  private def checkIfHeaderTenantConsistentWithRoute(request: Request): Unit =
    Option(request.getParam(routeParamName)) foreach { tenantRouteParam =>
      if (request.yaasHeaders.hybrisTenant != tenantRouteParam) {
        throw new ValidationException(s"'hybris-tenant' header must match route param '$routeParamName'")
      }
    }

}
