package gov.nih.nci.ncia.domain.operation;

import gov.nih.nci.ncia.internaldomain.Annotation;
import gov.nih.nci.ncia.internaldomain.GeneralSeries;

import java.util.Map;

public interface AnnotationOperationInterface {
	public Object validate(Map numbers) throws Exception;
	public void updateAnnotation(GeneralSeries series) throws Exception;
	public void insertOrReplaceAnnotation(Annotation annotation,
            String filename, 
            String studyInstanceUID) throws Exception;
	
	
}