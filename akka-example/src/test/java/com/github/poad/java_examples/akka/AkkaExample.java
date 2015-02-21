package com.github.poad.java_examples.akka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class AkkaExample {
    private static final Logger logger = LoggerFactory.getLogger(AkkaExample.class.getPackage().getName());
    
    @Test
    public static void test() {
        ActorSystem system = ActorSystem.create("system");
        ActorRef ref = system.actorOf(Props.create(SimpleActor.class), "simpleActor");

        String message = "hello.";
        ref.tell(message, null);

        try {
            Thread.sleep(3000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        system.shutdown();
        
        logger.info("end.");
    }
}
