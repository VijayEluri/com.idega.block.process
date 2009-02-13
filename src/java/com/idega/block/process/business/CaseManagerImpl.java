package com.idega.block.process.business;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.ejb.FinderException;
import javax.faces.component.UIComponent;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.idega.block.process.data.Case;
import com.idega.block.process.data.CaseCode;
import com.idega.block.process.presentation.beans.CasePresentation;
import com.idega.business.IBOLookup;
import com.idega.business.IBOLookupException;
import com.idega.business.IBORuntimeException;
import com.idega.presentation.IWContext;
import com.idega.presentation.paging.PagedDataCollection;
import com.idega.presentation.text.Link;
import com.idega.user.data.User;
import com.idega.util.ListUtil;

/**
 * Default implementation of CaseManager. 
 * 
 * @author donatas
 *
 */
@Scope("singleton")
@Service(CaseManagerImpl.beanIdentifier)
public class CaseManagerImpl implements CaseManager {

	public static final String beanIdentifier = "defaultCaseHandler";
	public static final String caseHandlerType = "CasesDefault";

	public String getBeanIdentifier() {
		return beanIdentifier;
	}

	public String getType() {
		return caseHandlerType;
	}
	
	public List<Long> getAllCaseProcessDefinitions() {
		return new ArrayList<Long>();
	}

	public Map<Long, String> getAllCaseProcessDefinitionsWithName() {
		throw new UnsupportedOperationException("Not implemented");
	}

	public List<Link> getCaseLinks(Case theCase, String componentType) {
		throw new UnsupportedOperationException("Not implemented");
	}

	public PagedDataCollection<CasePresentation> getCases(User user, String type, Locale locale, List<String> caseStatusesToHide, List<String> caseStatusesToShow, int startIndex,
			int count) {
		IWContext iwc = IWContext.getCurrentInstance();
		CaseBusiness caseBusiness = getCaseBusiness(iwc);
		try {
			CaseCode[] caseCodes = caseBusiness.getCaseCodesForUserCasesList();
			Collection<Case> cases = caseBusiness.getAllCasesForUserExceptCodes(user, caseCodes, startIndex, count);
			int caseCount = caseBusiness.getNumberOfCasesForUserExceptCodes(user, caseCodes);
			return new PagedDataCollection<CasePresentation>(convertToPresentationBeans(cases, locale), caseCount);
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (FinderException e) {
			e.printStackTrace();
		}
		return new PagedDataCollection<CasePresentation>(new ArrayList<CasePresentation>());
	}
	
	public List<Integer> getCaseIds(User user, String type, List<String> statusesToHide, List<String> statusesToShow) {
		throw new UnsupportedOperationException("Not implemented");
	}

	public Long getLatestProcessDefinitionIdByProcessName(String name) {
		throw new UnsupportedOperationException("Not implemented");
	}

	public Long getProcessDefinitionId(Case theCase) {
		throw new UnsupportedOperationException("Not implemented");
	}

	public String getProcessDefinitionName(Case theCase) {
		throw new UnsupportedOperationException("Not implemented");
	}

	public String getProcessIdentifier(Case theCase) {
		throw new UnsupportedOperationException("Not implemented");
	}

	public Long getProcessInstanceId(Case theCase) {
		throw new UnsupportedOperationException("Not implemented");
	}

	public String getProcessName(String processName, Locale locale) {
		throw new UnsupportedOperationException("Not implemented");
	}

	public UIComponent getView(IWContext iwc, Integer caseId, String caseProcessorType, String caseManagerType) {
		return null;
	}

	public PagedDataCollection<CasePresentation> getCasesByIds(List<Integer> ids, Locale locale) {
		Collection<Case> cases = getCaseBusiness(IWContext.getCurrentInstance()).getCasesByIds(ids);
		return new PagedDataCollection<CasePresentation>(convertToPresentationBeans(cases, locale), cases.size());
	}

	public Long getProcessInstanceIdByCaseId(Object id) {
		throw new UnsupportedOperationException("Not implemented");
	}
	
	/**
	 * Converts Case entities to presentation beans.
	 * 
	 * @param cases Collection of Case entities.
	 * @param locale Current locale.
	 * @return List of CasePresentation beans.
	 */
	protected List<CasePresentation> convertToPresentationBeans(Collection<? extends Case> cases, Locale locale) {
		if (ListUtil.isEmpty(cases)) {
			return new ArrayList<CasePresentation>(0);
		}
		List<CasePresentation> beans = new ArrayList<CasePresentation>(cases.size());
		for (Iterator<? extends Case> iterator = cases.iterator(); iterator.hasNext();) {
			Case caze = iterator.next();
			CasePresentation bean = convertToPresentation(caze, null, locale);
			beans.add(bean);
		}
		return beans;
	}

	protected CasePresentation convertToPresentation(Case theCase, CasePresentation bean, Locale locale) {
		
		IWContext iwc = IWContext.getCurrentInstance();
		
		if (bean == null) {
			bean = new CasePresentation();
		}
		bean.setPrimaryKey(theCase.getPrimaryKey() instanceof Integer ? (Integer) theCase.getPrimaryKey() : Integer.valueOf(theCase.getPrimaryKey().toString()));
		bean.setId(theCase.getId());
		bean.setUrl(theCase.getUrl());
		bean.setCaseManagerType(theCase.getCaseManagerType());
		bean.setOwner(theCase.getOwner());
		bean.setExternalId(theCase.getExternalId());
		bean.setCaseIdentifier(theCase.getCaseIdentifier());
		try {
			 bean.setSubject(getCaseBusiness(iwc).getCaseSubject(theCase, locale));
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		if (Case.class.equals(theCase.getClass())) {
			//this is only for Case instances.
			try {
				bean.setLocalizedStatus(getCaseBusiness(iwc).getLocalizedCaseStatusDescription(theCase,
						theCase.getCaseStatus(), locale));
			} catch (RemoteException e) {
				e.printStackTrace();
			}}
		bean.setCreated(theCase.getCreated());
		bean.setCode(theCase.getCode());
		return bean;
	}
	
	
	public PagedDataCollection<CasePresentation> getClosedCases(Collection groups) {
		return new PagedDataCollection<CasePresentation>(new ArrayList<CasePresentation>());
	}
	
	public PagedDataCollection<CasePresentation> getMyCases(User user) {
		return new PagedDataCollection<CasePresentation>(new ArrayList<CasePresentation>());
	}

	protected CaseBusiness getCaseBusiness(IWContext iwc) {
		
		try {
			return (CaseBusiness)IBOLookup.getServiceInstance(iwc, CaseBusiness.class);
		}
		catch (IBOLookupException ile) {
			throw new IBORuntimeException(ile);
		}
	}
}