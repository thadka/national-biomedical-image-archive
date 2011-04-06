package gov.nih.nci.nbia.beans.collectiondescriptions;

import gov.nih.nci.nbia.beans.BeanManager;
import gov.nih.nci.nbia.beans.security.SecurityBean;
import gov.nih.nci.nbia.collectiondescription.CollectionDescProcessor;
import gov.nih.nci.nbia.dto.CollectionDescDTO;
import gov.nih.nci.nbia.util.NCIAConfig;
import gov.nih.nci.nbia.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;

import com.icesoft.faces.component.inputrichtext.InputRichText;

public class CollectionDescBean {
	private static Logger logger = Logger.getLogger(CollectionDescBean.class);
	private List<String> collectionNames = new ArrayList<String>();
	private CollectionDescProcessor processor;
	private List<SelectItem> collectionItems;
	private String selectedCollectionName;
	private String defaultSelectValue="please select";
	private String defaultSelectLabel="--Please Select--";
	private SelectItem defaultSelectItem = new SelectItem(defaultSelectValue, defaultSelectLabel);
	private InputRichText inputRichText;
	private String collectionDesc = "";
	private CollectionDescDTO collectionDescDTO;
	private SecurityBean sb;

	private boolean infoMessage = false;
	private String message;
	
	
	public CollectionDescBean() {
		collectionDescDTO = new CollectionDescDTO();
		selectedCollection = new CollectionDescDTO();
		processor = new CollectionDescProcessor();
		collectionItems = new ArrayList<SelectItem>();
		collectionItems.add(0, defaultSelectItem);
		sb = BeanManager.getSecurityBean();
		load();
	}

	private CollectionDescDTO selectedCollection;

	public String load() {
		collectionNames = processor.getCollectionNames();
		for(String s : collectionNames){
			SelectItem item = new SelectItem(s, s);
			collectionItems.add(item);
		}
		return "editCollectionDescriptions";
	}
	
	public void  collectionValueChangeListener(ValueChangeEvent event){
		String name = (String) event.getNewValue();
		message="";
		infoMessage = false;
		if(name.equals(defaultSelectValue)){
			logger.info("no changes need");
			selectedCollectionName="";
			selectedCollection = collectionDescDTO;
			this.inputRichText.resetValue();
			//return;
		}else{
			System.out.println("selectedCollectionName: " +selectedCollectionName);
			// retrieve the description based on the collectionName
			CollectionDescDTO dto = processor.getCollectionDescByCollectionName(name);
			selectedCollection = dto;
			this.inputRichText.resetValue();
		}
		
	}
	public String submit() {
		if(selectedCollectionName.equals(defaultSelectValue)){
			logger.info("no changes need");
			message = "Please choose a collection";
			infoMessage = true;
			return "";
		}
		String removeHTML = StringUtil.removeHTML(selectedCollection.getDescription());
		int collectionDescriptionMaxlength = NCIAConfig.getCollectionDescriptionMaxlength();
		if(removeHTML.length() > collectionDescriptionMaxlength){
			message="Your description is too long. Please reduce to " + collectionDescriptionMaxlength + " characters.";
			infoMessage = true;
			return "";
		}
		logger.info("updating collection description for " + selectedCollectionName);
		CollectionDescDTO dto = new CollectionDescDTO();
		dto.setCollectionName(selectedCollection.getCollectionName());
		dto.setId(selectedCollection.getId());
		dto.setDescription(selectedCollection.getDescription());
		dto.setUserName(sb.getUsername());
		processor.update(dto);
		message="The description for (" + selectedCollection.getCollectionName() + ") has been saved successfully.";
		infoMessage=true;
		return "";
	}

	public String reset() {
		selectedCollectionName = defaultSelectValue;
		selectedCollection = collectionDescDTO;
		message = "";
		infoMessage = false;
		return "";
	}
	
	public List<String> getCollectionNames() {
		return collectionNames;
	}

	public void setCollectionNames(List<String> collectionNames) {
		this.collectionNames = collectionNames;
	}

	public boolean isInfoMessage() {
		return infoMessage;
	}

	public void setInfoMessage(boolean infoMessage) {
		this.infoMessage = infoMessage;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public CollectionDescDTO getSelectedCollection() {
		return selectedCollection;
	}

	public void setSelectedCollection(CollectionDescDTO selectedCollection) {
		this.selectedCollection = selectedCollection;
	}	

	public InputRichText getInputRichText() {
		return inputRichText;
	}

	public void setInputRichText(InputRichText inputRichText) {
		this.inputRichText = inputRichText;
	}
	
	public String getCollectionDesc() {
		return collectionDesc;
	}

	public void setCollectionDesc(String collectionDesc) {
		this.collectionDesc = collectionDesc;
	}
	
	public List<SelectItem> getCollectionItems() {
		return collectionItems;
	}

	public void setCollectionItems(List<SelectItem> collectionItems) {
		this.collectionItems = collectionItems;
	}

	public String getSelectedCollectionName() {
		return selectedCollectionName;
	}

	public void setSelectedCollectionName(String selectedCollectionName) {
		this.selectedCollectionName = selectedCollectionName;
	}
}