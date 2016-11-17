package org.bitbucket.poad1010.examples.cucumber;

import cucumber.api.java8.En;

/**
 * Created by ken-yo on 2016/12/01.
 */
public class Stepdefs implements En {
    public Stepdefs() {
        Given("I have (\\d+) cukes in my belly", (Integer cukes) -> {
            System.out.format("Cukes: %d\n", cukes);
        });
    }
}
