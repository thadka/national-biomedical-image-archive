/**
* $Id$
*
* $Log: not supported by cvs2svn $
* Revision 1.1  2007/08/07 19:20:07  bauerd
* *** empty log message ***
*
* Revision 1.1  2007/08/05 21:44:38  bauerd
* Initial Check in of reorganized components
*
* Revision 1.6  2007/02/09 10:48:53  bauerd
* *** empty log message ***
*
* Revision 1.5  2007/02/09 09:20:38  bauerd
* *** empty log message ***
*
* Revision 1.4  2007/01/11 22:44:28  dietrich
* Defect 174
*
* Revision 1.3  2006/09/27 20:46:27  panq
* Reformated with Sun Java Code Style and added a header for holding CVS history.
*
*/
package gov.nih.nci.ncia.servlet;

import gov.nih.nci.nbia.factories.ApplicationFactory;
import gov.nih.nci.nbia.jobs.NodeLookupJob;
import gov.nih.nci.nbia.util.NCIAConfig;
import gov.nih.nci.ncia.newresults.LatestCurationDateJob;
import gov.nih.nci.ncia.newresults.NewResultsProcessor;

import java.util.Date;
import javax.servlet.http.HttpServlet;
import org.apache.log4j.Logger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerUtils;
import org.quartz.impl.StdSchedulerFactory;


public class StartupServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(StartupServlet.class);
    /**
     * Run upon initialization of the servlet
     *
     * @see javax.servlet.GenericServlet#init()
     */
    public void init() {
        // Set initial latest curation date
        Date latestCurationDate = null;
        try {
            latestCurationDate = new LatestCurationDateJob().getLatestCurationDate();
        } 
        catch (Exception e) {
            latestCurationDate = new Date();
        }
        finally{
           	ApplicationFactory.getInstance().setLatestCurationDate(latestCurationDate);
        }

        /*
        * Create the  3 Quatz Tasks
        * see http://www.opensymphony.com/quartz/
        */
        Trigger runNewDataFlagTrigger =
    	   	TriggerUtils.makeDailyTrigger(NCIAConfig.getHourToRunNewDataFlagUpdate(), 0);
        runNewDataFlagTrigger.setName("Daily Trigger for New Results Flag Update");
       JobDetail runNewDataFlagJobDetail = new JobDetail("NewDataFlag",
				                                         Scheduler.DEFAULT_GROUP, 
				                                         NewResultsProcessor.class);

       // Schedule it to get updated daliy at midnight
       Trigger latestCurationDateTrigger = TriggerUtils.makeDailyTrigger(0, 0);
       latestCurationDateTrigger.setName("Daily Trigger for Latest Curation Date");
       JobDetail  latestCurationDateJobDetail = new JobDetail("LatestCurationDate",
                                                              Scheduler.DEFAULT_GROUP,
                                                              LatestCurationDateJob.class);
       
       Integer hrs = Integer.valueOf(NCIAConfig.getDiscoverPeriodInHrs());
       Trigger nodeLookupTrigger = TriggerUtils.makeHourlyTrigger(hrs);
       
       nodeLookupTrigger.setName("Trigger for Node Lookup");
       JobDetail nodeLookupJobDetail = new JobDetail("NodeLookup",
                                                     Scheduler.DEFAULT_GROUP,
                                                     NodeLookupJob.class);
       
        //Schedule the tasks...
        try {
            SchedulerFactory sf = new StdSchedulerFactory();

            Scheduler scheduler = sf.getScheduler();
            //Job 1 - Latest Curation Date
            scheduler.scheduleJob(latestCurationDateJobDetail, latestCurationDateTrigger);

            //Job 3 - New Data Flag
            if(NCIAConfig.runNewDataFlagUpdate()) {
            	scheduler.scheduleJob(runNewDataFlagJobDetail, runNewDataFlagTrigger);
            }
            
            scheduler.scheduleJob(nodeLookupJobDetail, nodeLookupTrigger);

            scheduler.start();
        } catch (SchedulerException se) {
        	logger.error(se);
        }catch(Exception e){
        	logger.error(e);
        }

    }

}
