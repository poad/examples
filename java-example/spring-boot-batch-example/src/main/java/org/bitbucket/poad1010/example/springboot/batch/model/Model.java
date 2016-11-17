package org.bitbucket.poad1010.example.springboot.batch.model;

import org.bitbucket.poad1010.example.springboot.batch.entity.Entity;

/**
 * Created by ken-yo on 2016/08/06.
 */
public interface Model<E extends Entity> {
    E toEntity();
}
