package com.idega.block.process.presentation.beans;

import java.util.Collection;
import java.util.List;
import java.util.Locale;

import com.idega.io.MemoryFileBuffer;
import com.idega.user.data.User;

public interface CasesSearchResultsHolder {

	public static final String SPRING_BEAN_IDENTIFIER = "casesSearchResultsHolder";

	public void setSearchResults(String id, CasesSearchResults results);

	public Integer getNextCaseId(String id, Integer currentId, String processDefinitionName);

	public Integer getNextCaseId(String id, Integer currentId);

	public boolean isSearchResultStored(String id);

	public boolean isAllDataLoaded(String id);

	public boolean doExport(String id,boolean exportContacts, boolean showCompany);

	public MemoryFileBuffer getExportedSearchResults(String id,boolean exportContacts, boolean showCompany);
	public MemoryFileBuffer getUsersExport(Collection<User> users,Locale locale,boolean showUserCompany);

	public MemoryFileBuffer getExportedCases(String id,boolean exportContacts, boolean showCompany);
	public boolean setCasesToExport(String id, List<CasePresentation> cases);

	public boolean clearSearchResults(String id);

	public Collection<CasePresentation> getSearchResults(String id);

	public CasesSearchCriteriaBean getSearchCriteria(String id);

	public void concatExternalData(String id, List<CasePresentation> externalData);
}