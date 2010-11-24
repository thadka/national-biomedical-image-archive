package gov.nih.nci.ncia.dynamicsearch.criteria;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

public class ContainsCriteriaFactory implements CriteriaFactory {

	public Criterion constructCriteria(String fieldName, String value, String fieldType)
	throws Exception
	{
		if (value.equalsIgnoreCase("Not Populated (NULL)"))
		{
			value = "null";
		}
		return Restrictions.ilike(fieldName, "%"+value+"%");

	}

}
