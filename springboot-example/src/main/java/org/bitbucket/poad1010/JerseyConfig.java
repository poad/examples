package org.bitbucket.poad1010;

import org.bitbucket.poad1010.resources.Resource;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

/**
 * Created by ken-yo on 2016/08/06.
 */
@Component
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        packages(true, Resource.class.getPackage().getName());
    }
}
