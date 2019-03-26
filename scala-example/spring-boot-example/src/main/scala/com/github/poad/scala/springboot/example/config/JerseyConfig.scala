package com.github.poad.scala.springboot.example.config

import com.github.poad.scala.springboot.example.mapper.RawExceptionMapper
import org.glassfish.jersey.server.ResourceConfig
import org.springframework.stereotype.Component

@Component
class JerseyConfig extends ResourceConfig {
  packages(true, "com.github.poad.scala.springboot.example.resource")
  register(new RawExceptionMapper(), 1)
}
