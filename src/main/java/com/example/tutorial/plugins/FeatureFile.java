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
    public static String issueKey="";
    public static String File_Name="";
    public static Logger log = LoggerFactory.getLogger(FeatureFile.class);

     public static  String IMPORTED_SCENARIO_DIRECTORY = System.getProperty("importedScenarioDirectory","https://api.bitbucket.org/2.0/repositories/deshapuraja/testbdd/src/master/");


    public FeatureFile(Class clazz) throws InitializationError, IOException {
        super(clazz);
       // System.out.println(" Inside the myCucumber Class ");
    }




    @Override
    protected Runtime createRuntime(ResourceLoader resourceLoader, ClassLoader classLoader, RuntimeOptions runtimeOptions) throws InitializationError, IOException {
        log.info("\n\n\t#+#+# FeatureFile STATIC INITIALIZIER\n");
        issueKey = IssueCreatedResolvedListener.issue.getKey().toString();
        log.info("\n The Issue Key in Feature file method {}", issueKey);
       /* System.out.println(" Issue Key is " +issueKey );
        System.out.println(" My Cucumber class is running ");*/
        log.info("****the Runtime Class has beeen called");
       // File_Name= IMPORTED_SCENARIO_DIRECTORY + issueKey + ".feature";
       // XrayApi.makeDirectory(IMPORTED_SCENARIO_DIRECTORY);
       // XrayApi.getIssuekeyfromJIRA(issueKey, File_Name);
       // BitbucketAPI.createFileBitbucket(File_Name);
        log.info("****Feature file is created******");


         // IssueCreatedResolvedListener.importTestsFromJIRA(IMPORTED_SCENARIO_DIRECTORY + "/tests_imported_from_jira.feature" );
        return super.createRuntime(resourceLoader, classLoader, runtimeOptions);
    }
}
