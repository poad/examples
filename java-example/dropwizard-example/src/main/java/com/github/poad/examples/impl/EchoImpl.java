package com.github.poad.examples.impl;

import com.github.poad.examples.resources.EchoResource;

public class EchoImpl implements EchoResource {

    @Override
    public String echo(String message) {
        return message;
    }

}
