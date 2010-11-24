package gov.nih.nci.ncia.dao;

import gov.nih.nci.ncia.AbstractDbUnitTestForJunit4;

import java.util.Date;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/applicationContext-service.xml", "/applicationContext-hibernate-testContext.xml"})
public class ImageDAOCurationDateTestCase extends AbstractDbUnitTestForJunit4 {

	@Test
	public void testLatestDate() throws Exception {

		Date latestDate = imageDAO.findLastCurationDate();

		String dateComparisonStr = "2006-11-20 10:07:49.0";

		Assert.assertEquals(latestDate.toString(), dateComparisonStr);
	}


    //////////////////////////////PROTECTED/////////////////////////////////


    protected String getDataSetResourceSpec() {
    	return TEST_DB_FLAT_FILE;
    }


    ////////////////////////////////////PRIVATE/////////////////////////////////

    private static final String TEST_DB_FLAT_FILE = "dbunitscripts/collections_testdata.xml";

    @Autowired
    private ImageDAO imageDAO;
}
