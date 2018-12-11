/*
 * $Id: CaseLogHomeImpl.java 1.1 5.12.2004 laddi Exp $
 * Created on 5.12.2004
 *
 * Copyright (C) 2004 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package com.idega.block.process.data;

import java.sql.Timestamp;
import java.util.Collection;

import javax.ejb.FinderException;

import com.idega.data.IDOException;
import com.idega.data.IDOFactory;
import com.idega.user.data.User;


/**
 * Last modified: $Date: 2004/06/28 09:09:50 $ by $Author: laddi $
 *
 * @author <a href="mailto:laddi@idega.com">laddi</a>
 * @version $Revision: 1.1 $
 */
public class CaseLogHomeImpl extends IDOFactory implements CaseLogHome {

	private static final long serialVersionUID = 1L;

	@Override
	protected Class getEntityInterfaceClass() {
		return CaseLog.class;
	}

	@Override
	public CaseLog create() throws javax.ejb.CreateException {
		return (CaseLog) super.createIDO();
	}

	@Override
	public CaseLog findByPrimaryKey(Object pk) throws javax.ejb.FinderException {
		return (CaseLog) super.findByPrimaryKeyIDO(pk);
	}

	@Override
	public Collection findAllCaseLogsByCase(Case aCase) throws FinderException {
		com.idega.data.IDOEntity entity = this.idoCheckOutPooledEntity();
		java.util.Collection ids = ((CaseLogBMPBean) entity).ejbFindAllCaseLogsByCase(aCase);
		this.idoCheckInPooledEntity(entity);
		return this.getEntityCollectionForPrimaryKeys(ids);
	}

	@Override
	public Collection findAllCaseLogsByCaseOrderedByDate(Case aCase) throws FinderException {
		com.idega.data.IDOEntity entity = this.idoCheckOutPooledEntity();
		java.util.Collection ids = ((CaseLogBMPBean) entity).ejbFindAllCaseLogsByCaseOrderedByDate(aCase);
		this.idoCheckInPooledEntity(entity);
		return this.getEntityCollectionForPrimaryKeys(ids);
	}

	@Override
	public CaseLog findLastCaseLogForCase(Case aCase) throws FinderException {
		com.idega.data.IDOEntity entity = this.idoCheckOutPooledEntity();
		Object pk = ((CaseLogBMPBean) entity).ejbFindLastCaseLogForCase(aCase);
		this.idoCheckInPooledEntity(entity);
		return this.findByPrimaryKey(pk);
	}

	@Override
	public Collection findAllCaseLogsByDate(Timestamp fromDate, Timestamp toDate) throws FinderException {
		com.idega.data.IDOEntity entity = this.idoCheckOutPooledEntity();
		java.util.Collection ids = ((CaseLogBMPBean) entity).ejbFindAllCaseLogsByDate(fromDate, toDate);
		this.idoCheckInPooledEntity(entity);
		return this.getEntityCollectionForPrimaryKeys(ids);
	}

	@Override
	public Collection findAllCaseLogsByCaseAndDate(String caseCode, Timestamp fromDate, Timestamp toDate) throws FinderException {
		com.idega.data.IDOEntity entity = this.idoCheckOutPooledEntity();
		java.util.Collection ids = ((CaseLogBMPBean) entity).ejbFindAllCaseLogsByCaseAndDate(caseCode, fromDate, toDate);
		this.idoCheckInPooledEntity(entity);
		return this.getEntityCollectionForPrimaryKeys(ids);
	}

	@Override
	public Collection<CaseLog> findAllCaseLogs(Case theCase,
			Timestamp fromDate, Timestamp toDate, User performer) throws FinderException {
		com.idega.data.IDOEntity entity = this.idoCheckOutPooledEntity();
		Collection<Long> ids = ((CaseLogBMPBean) entity).ejbFindAllCaseLogsByCaseAndDate(theCase, fromDate, toDate, performer);
		this.idoCheckInPooledEntity(entity);
		return this.getEntityCollectionForPrimaryKeys(ids);
	}

	@Override
	public Collection findAllCaseLogsByDateAndStatusChange(Timestamp fromDate, Timestamp toDate, String statusBefore, String statusAfter) throws FinderException {
		com.idega.data.IDOEntity entity = this.idoCheckOutPooledEntity();
		java.util.Collection ids = ((CaseLogBMPBean) entity).ejbFindAllCaseLogsByDateAndStatusChange(fromDate, toDate, statusBefore, statusAfter);
		this.idoCheckInPooledEntity(entity);
		return this.getEntityCollectionForPrimaryKeys(ids);
	}

	@Override
	public Collection findAllCaseLogsByCaseAndDateAndStatusChange(String caseCode, Timestamp fromDate, Timestamp toDate, String statusBefore, String statusAfter) throws FinderException {
		com.idega.data.IDOEntity entity = this.idoCheckOutPooledEntity();
		java.util.Collection ids = ((CaseLogBMPBean) entity).ejbFindAllCaseLogsByCaseAndDateAndStatusChange(caseCode, fromDate, toDate, statusBefore, statusAfter);
		this.idoCheckInPooledEntity(entity);
		return this.getEntityCollectionForPrimaryKeys(ids);
	}

	@Override
	public int getCountByStatusChange(Case theCase, String statusBefore, String statusAfter) throws IDOException {
		com.idega.data.IDOEntity entity = this.idoCheckOutPooledEntity();
		int theReturn = ((CaseLogBMPBean) entity).ejbHomeGetCountByStatusChange(theCase, statusBefore, statusAfter);
		this.idoCheckInPooledEntity(entity);
		return theReturn;
	}


	@Override
	public Collection<CaseLog> findAllCaseLogs(Case theCase,
			Timestamp fromDate, Timestamp toDate, User performer, Boolean useGeneralAppend) throws FinderException {
		com.idega.data.IDOEntity entity = this.idoCheckOutPooledEntity();
		Collection<Long> ids = ((CaseLogBMPBean) entity).ejbFindAllCaseLogsByCaseAndDate(theCase, fromDate, toDate, performer, useGeneralAppend);
		this.idoCheckInPooledEntity(entity);
		return this.getEntityCollectionForPrimaryKeys(ids);
	}

}
