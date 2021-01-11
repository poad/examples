package org.bitbucket.poad1010.example.springboot.jpa.model;

import org.bitbucket.poad1010.example.springboot.jpa.entity.Entity;
import org.bitbucket.poad1010.example.springboot.jpa.response.Response;

/**
 * Created by ken-yo on 2016/08/06.
 */
public interface Model<E extends Entity, R extends Response> {
    E toEntity();

    R toResponse();
}
