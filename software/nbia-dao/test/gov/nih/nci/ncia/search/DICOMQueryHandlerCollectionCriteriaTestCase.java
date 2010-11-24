package gov.nih.nci.ncia.search;

import gov.nih.nci.ncia.AbstractDbUnitTestForJunit4;
import gov.nih.nci.ncia.criteria.AuthorizationCriteria;
import gov.nih.nci.ncia.criteria.CollectionCriteria;
import gov.nih.nci.ncia.query.DICOMQuery;
import gov.nih.nci.ncia.security.AuthorizationManager;
import gov.nih.nci.ncia.security.NCIASecurityManager.RoleType;
import gov.nih.nci.ncia.util.SiteData;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/applicationContext-service.xml", "/applicationContext-hibernate-testContext.xml"})

public class DICOMQueryHandlerCollectionCriteriaTestCase extends AbstractDbUnitTestForJunit4 {

	@Test
	public void testCollectionCriteriaHqlQuery() throws Exception {
        DICOMQuery dicomQuery = new DICOMQuery();
        CollectionCriteria collectionCriteria = new CollectionCriteria();
		collectionCriteria.setCollectionValue("CT Colonography");
		dicomQuery.setCriteria(collectionCriteria);
		dicomQuery.setCriteria(createAuthorizationCriteria());

		List<PatientStudySeriesTriple> resultSets = dicomQueryHandler.findTriples(dicomQuery);
		Assert.assertTrue(resultSets.size() == 4);

		//////////////////////
		collectionCriteria = new CollectionCriteria();
		collectionCriteria.setCollectionValue("Virtual Colonoscopy");
		dicomQuery.setCriteria(collectionCriteria);

		resultSets = dicomQueryHandler.findTriples(dicomQuery);
		Assert.assertTrue(resultSets.size() == 4);

        /////////////////////
		collectionCriteria = new CollectionCriteria();
		collectionCriteria.setCollectionValue("garbage");
		dicomQuery.setCriteria(collectionCriteria);

		resultSets = dicomQueryHandler.findTriples(dicomQuery);
		Assert.assertTrue(resultSets.size() == 0);
	}

    //////////////////////////////PROTECTED/////////////////////////////////

    protected String getDataSetResourceSpec() {
    	return TEST_DB_FLAT_FILE;
    }



    ////////////////////////////////////PRIVATE/////////////////////////////////

    private static final String TEST_DB_FLAT_FILE = "dbunitscripts/collections_testdata.xml";
    @Autowired
    private DICOMQueryHandler dicomQueryHandler;


	private static AuthorizationCriteria createAuthorizationCriteria()
			throws Exception {
		AuthorizationManager am = new AuthorizationManager("kascice");
		List<SiteData> authorizedSites = am.getAuthorizedSites(RoleType.READ);
		List<String> authorizedCollections = am
				.getAuthorizedCollections(RoleType.READ);
		List<String> authorizedSeriesSecurityGroups = am
				.getAuthorizedSeriesSecurityGroups(RoleType.READ);

		AuthorizationCriteria authorizationCriteria = new AuthorizationCriteria();
		//authorizationCriteria.setCollections(authorizedCollections);
		authorizationCriteria.setSites(authorizedSites);
		authorizationCriteria
				.setSeriesSecurityGroups(authorizedSeriesSecurityGroups);

		return authorizationCriteria;
	}

}
