package com.github.poad.java_examples.akka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class AkkaExample {
    private static final Logger logger = LoggerFactory.getLogger(AkkaExample.class.getPackage().getName());
    
    public static ActorSystem system;
    
    @BeforeTest
    public static void setUp() {
        system = ActorSystem.create("system");
    }
    
    @Test
    public static void test() {
        ActorRef ref = system.actorOf(Props.create(SimpleActor.class), "simpleActor");

        String message = "hello.";
        // Actorへメッセージを送る（応答を待たない）
        ref.tell(message, null);

        try {
            Thread.sleep(3000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
    
    @AfterTest
    public static void tearDown() {
        system.shutdown();
        
        logger.info("end.");
    }
}
