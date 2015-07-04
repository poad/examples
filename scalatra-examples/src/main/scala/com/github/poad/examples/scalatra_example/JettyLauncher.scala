

package com.github.poad.examples.scalatra_example

import org.eclipse.jetty.webapp.WebAppContext
import org.eclipse.jetty.server.Server
import org.scalatra.servlet.ScalatraListener
import org.eclipse.jetty.servlet.DefaultServlet
import org.eclipse.jetty.servlet.DefaultServlet
import org.eclipse.jetty.servlet.DefaultServlet

/**
 * @author ken-yo
 */
object JettyLauncher {
    def main(args: Array[String]) {
    val port = if(System.getenv("PORT") != null) System.getenv("PORT").toInt else 4567

    val server = new Server(port)
    val context = new WebAppContext()
    context setContextPath "/"
    context.setResourceBase("src/main/webapp")
    context.addEventListener(new ScalatraListener)
    context.addServlet(classOf[DefaultServlet], "/")

    server.setHandler(context)

    server.start
    server.join
  }
}