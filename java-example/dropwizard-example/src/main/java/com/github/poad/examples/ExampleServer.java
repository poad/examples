package com.github.poad.examples;

import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.setup.Environment;

import com.github.poad.examples.impl.EchoImpl;

public class ExampleServer extends Application<Configuration> {

    public void run(Configuration configuration, Environment environment) {
        environment.jersey().packages("com.github.poad.examples.impl");
        environment.jersey().register(EchoImpl.class);
    }

    public static void main(String... args) throws Exception {
        new ExampleServer().run(args);
    }
}
