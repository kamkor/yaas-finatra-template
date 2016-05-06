package me.kamkor.yaas.http.filters

import com.twitter.finagle.http.{Request, Response}
import com.twitter.finagle.{Service, SimpleFilter}
import com.twitter.util.Future
import org.slf4j.MDC

class YaasHeadersMDCFilter extends SimpleFilter[Request, Response] {

  import YaasHeadersContext._

  override def apply(request: Request, service: Service[Request, Response]): Future[Response] = {
    MDC.put(YaasHeaders.HybrisTenant, request.yaasHeaders.hybrisTenant)
    service(request)
  }

}

