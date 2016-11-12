import javax.servlet.ServletContext
import org.scalatra.LifeCycle
import com.github.poad.examples.scalatra_example.ExampleServlet

/**
 * @author ken-yo
 */
class ScalatraBootstrap extends LifeCycle {
  override def init(context: ServletContext) {
    context.mount(new ExampleServlet, "/*")
  }
}