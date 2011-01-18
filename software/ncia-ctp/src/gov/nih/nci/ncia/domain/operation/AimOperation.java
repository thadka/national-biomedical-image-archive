package gov.nih.nci.ncia.domain.operation;

import gov.nih.nci.ncia.internaldomain.GeneralSeries;

import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 */
public class AimOperation extends DomainOperation 
                          implements AimOperationInterface {
	

	public AimOperation() {
	}
	
	@Transactional (propagation=Propagation.REQUIRED)
	public void insertAimAnnotation(Map numbers, GeneralSeries series) throws Exception {
		
        //List annotationList = getHibernateTemplate().find(hql);    
	}	
}