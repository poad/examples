package org.bitbucket.poad1010.example

import org.bitbucket.poad1010.example.common.Entity
import org.scalatra._

class ScalatraExample extends ScalatraServlet {
  get("/") {
    new Entity()
    "<h1>Hello, world!</h1>"
  }
}
