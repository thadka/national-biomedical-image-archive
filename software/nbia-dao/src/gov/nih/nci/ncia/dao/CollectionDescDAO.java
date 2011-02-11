package gov.nih.nci.ncia.dao;

import gov.nih.nci.ncia.dto.CollectionDescDTO;

import java.util.List;

import org.springframework.dao.DataAccessException;

/**
 * handle query for Editing collection description feature
 * @author lethai
 *
 */
public interface CollectionDescDAO  {
	/**
	 * retrieve list of collection name from trial data provenance table
	 */
	public List<String> findCollectionNames() throws DataAccessException;

	
	public CollectionDescDTO findCollectionDescByCollectionName(String collectionName) throws DataAccessException;
	
	public long save(CollectionDescDTO collectionDescDTO) throws DataAccessException;
}