package org.bitbucket.poad1010.examples.cucumber;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * Created by ken-yo on 2016/12/01.
 */
@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"pretty", "html:target/cucumber"}, features = "classpath:features")
public class CucumberTest {
}
