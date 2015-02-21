/**
 * 
 */
package com.github.poad.java_examples.akka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import akka.actor.UntypedActor;

/**
 * 
 *
 */
public class SimpleActor extends UntypedActor {

    private static final Logger logger = LoggerFactory.getLogger(SimpleActor.class.getPackage().getName());
    
    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof String) {
            logger.info("message:" + message);
        } else {
            logger.info("unhandled message.");

            // 想定しない型のメッセージはスルーする
            unhandled(message);
        }
    }

    @Override
    public void preStart() {
        logger.info("preStart");
    }

    @Override
    public void postStop() {
        logger.info("postStop");
    }
}
