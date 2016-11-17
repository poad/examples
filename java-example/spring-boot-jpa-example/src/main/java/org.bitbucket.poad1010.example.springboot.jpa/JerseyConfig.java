package org.bitbucket.poad1010.example.springboot.jpa;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

/**
 * Created by ken-yo on 2016/08/06.
 */
@Component
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        packages(true, getClass().getPackage().getName());
    }
}
