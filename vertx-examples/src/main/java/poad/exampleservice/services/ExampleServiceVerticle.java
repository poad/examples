/*
 * 
 */
package poad.exampleservice.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.AbstractVerticle;

/**
 * 
 *
 */
public class ExampleServiceVerticle extends AbstractVerticle {
	private final static Logger log = LoggerFactory.getLogger(ExampleServiceVerticle.class);

	@Override
	public void start() {
		log.info("hogehoge");
		vertx.createHttpServer().requestHandler(req -> req.response().end("Hello World!")).listen(8080);
	}
}
