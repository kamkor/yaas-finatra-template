package me.kamkor.finatra.httpclient

import java.net.URL

import com.twitter.finagle.http.{Request, Response}
import com.twitter.finagle.{Http, Service}
import com.twitter.util.Future

object HttpClient {

  val DefaultFinagleClient: Http.Client =
    Http
      .client
      .withSessionQualifier.noFailFast

  def apply(
    url: URL,
    defaultHeaders: Map[String, String] = Map.empty,
    finagleHttpClient: Http.Client = DefaultFinagleClient) = {

    val hostname = url.getHost
    val port = if (url.getPort != -1) url.getPort else url.getDefaultPort
    val ssl = "https".equalsIgnoreCase(url.getProtocol) || port == 443

    val service =
      if (ssl) {
        // FIXME also variant for tls with hostname
        finagleHttpClient.withTlsWithoutValidation.newService(s"$hostname:$port")
      } else {
        finagleHttpClient.newService(s"$hostname:$port")
      }

    new HttpClient(hostname, Some(url.getPath), defaultHeaders, service)
  }

}

class HttpClient(
  hostname: String,
  basePath: Option[String] = None,
  defaultHeaders: Map[String, String] = Map.empty,
  service: Service[Request, Response]) {

  def execute(request: Request): Future[Response] = {
    setHeaders(request)
    request.uri = basePath.getOrElse("") + "/" + request.uri
    setHost(request)
    service(request)
  }

  private[this] def setHost(request: Request) =
    if (hostname.nonEmpty) {
      request.headerMap.add("Host", hostname)
    }

  private[this] def setHeaders(request: Request): Unit = {
    if (defaultHeaders.nonEmpty) {
      for ((key, value) <- defaultHeaders) {
        request.headerMap.add(key, value)
      }
    }
  }

}

