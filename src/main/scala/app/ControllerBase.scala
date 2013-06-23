package app

import model.Account
import util.Validations
import org.scalatra._
import org.scalatra.json._
import org.json4s._
import jp.sf.amateras.scalatra.forms._

/**
 * Provides generic features for ScalatraServlet implementations.
 */
abstract class ControllerBase extends ScalatraFilter
  with ClientSideValidationFormSupport with JacksonJsonSupport with Validations {

  implicit val jsonFormats = DefaultFormats

  /**
   * Returns the context object for the request.
   */
  implicit def context: Context = Context(servletContext.getContextPath, LoginAccount)
  
  private def LoginAccount: Option[Account] = {
    session.get("LOGIN_ACCOUNT") match {
      case Some(x: Account) => Some(x)
      case _ => None
    }
  }

  protected def NotFound()     = html.error("Not Found")
  protected def Unauthorized() = redirect("/")

  protected def baseUrl = {
    val url = request.getRequestURL.toString
    url.substring(0, url.length - request.getRequestURI.length)
  }

}

case class Context(path: String, loginAccount: Option[Account])