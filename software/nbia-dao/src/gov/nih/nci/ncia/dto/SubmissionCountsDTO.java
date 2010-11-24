package gov.nih.nci.ncia.dto;

/**
 * This is a DTO passed back by the SubmissionHistoryDAO.  It encapsulates
 * counts for patients, studies and series associated with a collection of
 * underlying submissions.  It also includes the count of these underlying
 * submissions.  It's up to the caller to know/interpret which kind
 * of submission is being counted (new image, annotation, etc.)
 * 
 * <p>This DTO is meant to be immutable.  i.e. no mutators/setters to screw with state.
 */
public class SubmissionCountsDTO {

	public SubmissionCountsDTO(long patientCount,
			                   long studyCount,
			                   long seriesCount,
			                   long submissionCount) {
		this.patientCount = patientCount;
		this.studyCount = studyCount;
		this.seriesCount = seriesCount;
		this.submissionCount = submissionCount;		
	}
	
	public long getPatientCount() {
		return patientCount;
	}

	public long getStudyCount() {
		return studyCount;
	}

	public long getSeriesCount() {
		return seriesCount;
	}

	public long getSubmissionCount() {
		return submissionCount;
	}

	//////////////////////////////////////PRIVATE/////////////////////////////////
	
	private long patientCount;
	private long studyCount;
	private long seriesCount;
	private long submissionCount;
}