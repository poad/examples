package com.github.poad.examples.scalatra_example

import org.scalatra._
import org.scalatra.scalate.ScalateSupport

/**
 * @author ken-yo
 */
class ExampleServlet extends ScalatraServlet with ScalateSupport {

  get("/") {
    <html>
      <body>
        <h1>Hello, world!</h1>
        Say <a href="hello-scalate">hello to Scalate</a>.
      </body>
    </html>
  }
}