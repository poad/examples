package org.bitbucket.poad.example.ff4j;

import org.ff4j.FF4j;
import org.ff4j.web.FF4jProvider;

/**
 * Created by ken-yo on 2016/11/07.
 */
public class SampleFF4jProvider implements FF4jProvider {
    @Override
    public FF4j getFF4j() {
        return new FF4j();
    }
}
