package com.github.poad.scala.springboot.example

import java.util.TimeZone

import javax.annotation.PostConstruct
import org.springframework.boot.SpringApplication
import org.springframework.boot.actuate.health.{Health, HealthIndicator}
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer
import org.springframework.scheduling.annotation.EnableScheduling

object SpringScalaBootApplication extends SpringBootServletInitializer {
  def main(args: Array[String]): Unit = {
    SpringApplication
      .run(classOf[SpringScalaBootApplication], args: _*)
  }
}

@SpringBootApplication(scanBasePackages=Array("com.github.poad.scala.springboot.example"))
@EnableScheduling
class SpringScalaBootApplication extends SpringBootServletInitializer with HealthIndicator {

  override def health(): Health = Health.up().withDetail("health", true).build()

  @PostConstruct
  def postConstruct(): Unit = TimeZone.setDefault(TimeZone.getTimeZone("UTC"))

}
