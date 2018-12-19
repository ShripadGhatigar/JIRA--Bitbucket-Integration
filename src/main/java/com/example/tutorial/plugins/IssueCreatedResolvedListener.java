package com.example.tutorial.plugins;

import com.atlassian.event.api.EventListener;
import com.atlassian.event.api.EventPublisher;
import com.atlassian.jira.event.issue.IssueEvent;
import com.atlassian.jira.event.type.EventType;
import com.atlassian.jira.issue.Issue;
import com.atlassian.jira.issue.context.IssueContext;
import com.atlassian.jira.issue.issuetype.IssueType;
//import com.atlassian.plugin.spring.scanner.annotation.imports.JiraImport;
import cucumber.api.CucumberOptions;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
import com.atlassian.jira.extension.JiraStartedEvent;
import javax.inject.Inject;
import com.atlassian.plugin.spring.scanner.annotation.export.ExportAsService;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.atlassian.sal.api.lifecycle.LifecycleAware;
import com.atlassian.jira.event.user.LoginEvent;
import com.atlassian.jira.component.ComponentAccessor;
import org.springframework.stereotype.Component;
import javax.inject.Named;
import java.io.IOException;

import java.io.File;
import org.apache.http.client.ClientProtocolException;


@ExportAsService
@Component
@Named("eventListener")
/*@RunWith(FeatureFile.class)
@CucumberOptions(
        features = {"https://api.bitbucket.org/2.0/repositories/deshapuraja/testbdd/src/master/"}
)*/
public class IssueCreatedResolvedListener implements InitializingBean, DisposableBean {

    // public static  String IMPORTED_SCENARIO_DIRECTORY = System.getProperty("importedScenarioDirectory", "target/TestsImportedFromJira");

    public static Issue issue;
    public static final Logger log;
    public static Object obj;
    public static String file_Name = "";
    // public static byte[] fileContent= null;
    public static  String IMPORTED_SCENARIO_DIRECTORY = System.getProperty("importedScenarioDirectory","target/TestsImportedFromJira");

    static {
        log = LoggerFactory.getLogger(IssueCreatedResolvedListener.class);
        log.warn("\n\n\t#+#+# IssueCreatedResolvedListener: STATIC INITIALIZIER\n");
    }

    @ComponentImport
    private final EventPublisher eventPublisher;


    /*@Inject
    public IssueCreatedResolvedListener(EventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }*/
    @Inject
    public IssueCreatedResolvedListener(final EventPublisher eventPublisher) {
        log.info("Program is started");
        this.eventPublisher = eventPublisher;
        log.warn("\n\n\t#+#+# IssueCreatedResolvedListener: DEFAULT CONSTRUCTOR\n");
    }

    /**
     * Called when the plugin has been enabled.
     *
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("Enabling plugin");
        eventPublisher.register(this);
    }

    @EventListener
    public void processLoginEvent(LoginEvent loginEvent) {
        log.warn("\n\n\t#+#+# IssueCreatedResolvedListener: processLoginEvent (" + loginEvent.getUser().getUsername() + ") \n");
    }


    @EventListener
    public  static void processIssueEvent(IssueEvent issueEvent) throws ClientProtocolException, IOException {
    Long eventTypeId = issueEvent.getEventTypeId();
       log.info("\n *** The EventTypeID is {} ",issueEvent.getEventTypeId());

        issue = issueEvent.getIssue();
        log.info("\n******The Issue is {} *****.\n ", issue);
        //String value = JiraRest.getCucumbervalue(issue.getKey());
       // String fileName = BitbucketAPI.createFileBitbucket(file_Name);
        log.info("\n*********The File Name is {}",file_Name);
        //log.info("####The Value of JIRA Test is " + value);
       // if (value.equalsIgnoreCase("cucumber")) {
            // if it's an event we're interested in, log it
            if (eventTypeId.equals(EventType.ISSUE_CREATED_ID)) {
                log.info("\n\n\t#+#+# Issue {} has been created at {}.\n", issue.getKey(), issue.getCreated());
                log.info("\n\n calling the XrayAPi method \n");
                XrayApi.getIssuekeyfromJIRA(issue.getKey());
                log.info("\n\n Closed the XrayAPi method \n");
                //log.info("\n\n The Feature File File Name is {} ",FeatureFile.File_Name );
                log.info("\n\n calling the Bitbucket method \n");
                BitbucketAPI.createFileBitbucket(IMPORTED_SCENARIO_DIRECTORY +"_" + issue.getKey() + ".feature");
                log.info("\n\n Ending the Bitbucket method {} \n", IMPORTED_SCENARIO_DIRECTORY);
            }
               else if (eventTypeId.equals(EventType.ISSUE_UPDATED_ID)) {
                log.info("\n\n\t#+#+# Issue {} has been updated at {}.\n", issue.getKey(), issue.getUpdated());

            } else if (eventTypeId.equals(EventType.ISSUE_DELETED_ID)) {
                log.info("\n\n\t#+#+# Issue {} has been Deleted at {}.\n", issue.getKey(), issue.getUpdated());

            } else {
                log.warn("\n\n\t#+#+# Unhandled event type\n");
            }

    }





    @Override
    public void destroy() throws Exception {
        log.info("Disabling plugin");
        if (eventPublisher != null)
            eventPublisher.unregister(this);
    }
}



//getIssuekeyfromJIRA(FeatureFile.fileContent);
//fileContent = getIssuekeyfromJIRA(issue.getKey());
//log.info("\n\n\t#+#+# Issue {} description is  {}.\n", issue.getDescription());
//String Description = issue.getDescription();
//System.out.println("The Description is " + Description);
//log.info("\n\n\t#+#+# Issue {} type is  {}.\n", issue.getIssueType());
// log.info("\n\n\t#+#+# Issue {} Summary  {}.\n", issue.getSummary());
// getIssuekeyfromJIRA(FeatureFile.fileContent);
//fileContent = getIssuekeyfromJIRA(issue.getKey());
//getIssuekeyfromJIRA(FeatureFile.fileContent);
//fileContent = getIssuekeyfromJIRA(issue.getKey());

