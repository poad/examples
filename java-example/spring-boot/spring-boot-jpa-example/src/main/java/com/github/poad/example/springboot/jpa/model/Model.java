package com.github.poad.example.springboot.jpa.model;

import com.github.poad.example.springboot.jpa.entity.Entity;
import com.github.poad.example.springboot.jpa.response.Response;

/**
 * Created by ken-yo on 2016/08/06.
 */
public interface Model<E extends Entity, R extends Response> {
    E toEntity();

    R toResponse();
}
