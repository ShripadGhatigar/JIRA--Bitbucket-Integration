package com.example.tutorial.plugins;
import cucumber.api.junit.Cucumber;
import cucumber.runtime.Runtime;
import cucumber.runtime.RuntimeOptions;
import cucumber.runtime.io.ResourceLoader;
import java.io.IOException;
import org.junit.runners.model.InitializationError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FeatureFile extends Cucumber  {
    public static Logger log = LoggerFactory.getLogger(XrayApi.class);

     public static  String IMPORTED_SCENARIO_DIRECTORY = System.getProperty("importedScenarioDirectory","https://api.bitbucket.org/2.0/repositories/deshapuraja/testbdd/src");
   //  public static String file="";
    public static String fileContent= "";

    public FeatureFile(Class clazz) throws InitializationError, IOException {
        super(clazz);
       // System.out.println(" Inside the myCucumber Class ");
    }

    @Override
    protected Runtime createRuntime(ResourceLoader resourceLoader, ClassLoader classLoader, RuntimeOptions runtimeOptions) throws InitializationError, IOException {
        log.info("\n\n\t#+#+# FeatureFile STATIC INITIALIZIER\n");
        String issueKey = IssueCreatedResolvedListener.issue.getKey().toString();

       /* System.out.println(" Issue Key is " +issueKey );
        System.out.println(" My Cucumber class is running ");*/

        XrayApi.makeDirectory(IMPORTED_SCENARIO_DIRECTORY);
        fileContent=XrayApi.getIssuekeyfromJIRA(issueKey, IMPORTED_SCENARIO_DIRECTORY + issueKey + ".feature" ).toString();
        System.out.println("************FileReading*************");
        // IssueCreatedResolvedListener.importTestsFromJIRA(IMPORTED_SCENARIO_DIRECTORY + "/tests_imported_from_jira.feature" );
        return super.createRuntime(resourceLoader, classLoader, runtimeOptions);
    }
}
