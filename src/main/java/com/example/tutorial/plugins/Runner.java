package com.example.tutorial.plugins;
import org.junit.runner.RunWith;
import cucumber.api.CucumberOptions;

@RunWith(FeatureFile.class)
@CucumberOptions(
        features = {"target/TestsImportedFromJira"}
)
public class Runner {
}
