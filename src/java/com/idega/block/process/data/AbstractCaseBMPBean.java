package com.idega.block.process.data;

import com.idega.data.*;

import java.rmi.RemoteException;
import javax.ejb.*;
import java.sql.Timestamp;
import com.idega.user.data.User;
import java.util.Iterator;
import com.idega.core.ICTreeNode;

/**
 * Title:        idegaWeb
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:      idega software
 * @author <a href="mailto:tryggvi@idega.is">Tryggvi Larusson</a>
 * @version 1.0
 */

public abstract class AbstractCaseBMPBean extends GenericEntity implements Case{

  private Case _case;

  /**
   * Returns a unique Key to identify this CaseCode
   */
  public abstract String getCaseCodeKey();
  /**
   * Returns a description for the CaseCode associated with this case type
   */
  public abstract String getCaseCodeDescription();

  public void addGeneralCaseRelation(){
    this.addManyToOneRelationship(getIDColumnName(),"Case ID",Case.class);
    this.getAttribute(getIDColumnName()).setAsPrimaryKey(true);
  }

  public Object ejbCreate()throws CreateException{
    try{
      _case = this.getCaseHome().create();
      this.setPrimaryKey(_case.getPrimaryKey());
      return super.ejbCreate();
    }
    catch(RemoteException rme){
      throw new EJBException(rme.getMessage());
    }
  }


  public void setDefaultValues(){
    /*try{
      System.out.println("AbstractCase : Calling setDefaultValues()");
      setCode(getCaseCodeKey());
    }
    catch(RemoteException e){
      throw new EJBException(e.getMessage());
    }*/
  }

  public void insertStartData(){
    try{
      //CaseHome chome = (CaseHome)IDOLookup.getHome(Case.class);
      CaseCodeHome cchome = (CaseCodeHome)IDOLookup.getHome(CaseCode.class);
      CaseStatusHome cshome = (CaseStatusHome)IDOLookup.getHome(CaseStatus.class);

      CaseCode code = cchome.create();
      code.setCode(getCaseCodeKey());
      code.setDescription(getCaseCodeDescription());
      code.store();

      String[] statusKeys = this.getCaseStatusKeys();
      String[] statusDescs = this.getCaseStatusDescriptions();
      if(statusKeys!=null){
	for (int i = 0; i < statusKeys.length; i++) {
	    String statusKey = null;
	    try{
	      statusKey = statusKeys[i];
	      String statusDesc = null;

	      try{
		statusDesc = statusDescs[i];
	      }
	      catch(java.lang.NullPointerException ne){}
	      catch(java.lang.ArrayIndexOutOfBoundsException arre){}

	      CaseStatus status = cshome.create();
	      status.setStatus(statusKey);
	      if(statusDesc!=null){
		status.setDescription(statusDesc);
	      }
	      status.store();
	      code.addAssociatedCaseStatus(status);
	    }
	    catch(Exception e){
	      //e.printStackTrace();
	      System.err.println("Error inserting CaseStatus for key: "+statusKey);
	    }
	  }
	}

    }
    catch(Exception e){
      e.printStackTrace();
    }
  }

  /**
   * Could be ovverrided for extra CaseStatus Keys associated with this CaseCode
   * Returns an array of Strings.
   */
  public String[] getCaseStatusKeys(){
    return null;
  }

  /**
   * Could be ovverrided for extra CaseStatus Descriptions associated with this CaseCode
   * Returns an array of String descriptions
   * Does not need to return anything (but null), but if it returns a non-null value then the array must be as long as returned by getCaseStatusKeys()
   */
  public String[] getCaseStatusDescriptions(){
    return null;
  }

  protected boolean doInsertInCreate(){
    return true;
  }

  public Object ejbFindByPrimaryKey(Object key)throws FinderException{
    try{
      _case = this.getCaseHome().findByPrimaryKey(key);
      return super.ejbFindByPrimaryKey(key);
    }
    catch(RemoteException rme){
      throw new EJBException(rme.getMessage());
    }
  }

  public void store()throws IDOStoreException{
    try{
      if(this.getCode()==null){
	this.setCode(this.getCaseCodeKey());
      }
      getGeneralCase().store();
      super.store();
    }
    catch(RemoteException rme){
      throw new EJBException(rme.getMessage());
    }
  }


  public void remove()throws RemoveException{
    try{
      super.remove();
      getGeneralCase().remove();
    }
    catch(RemoteException rme){
      throw new RemoveException(rme.getMessage());
    }
  }

  protected CaseHome getCaseHome()throws RemoteException{
    return (CaseHome)com.idega.data.IDOLookup.getHome(Case.class);
  }

  protected Case getGeneralCase()throws RemoteException{
    if(_case == null){
      try{
	_case = getCaseHome().findByPrimaryKey(this.getPrimaryKey());
      }
      catch(FinderException fe){
	fe.printStackTrace();
	throw new EJBException(fe.getMessage());
      }
    }
    return _case;
  }
  public Timestamp getCreated() throws java.rmi.RemoteException {
    return getGeneralCase().getCreated();
  }
  public void setCaseCode(CaseCode p0) throws java.rmi.RemoteException {
    getGeneralCase().setCaseCode(p0);
  }

  public void setParentCase(Case p0) throws java.rmi.RemoteException {
    getGeneralCase().setParentCase(p0);
  }

  public void setStatus(String p0) throws java.rmi.RemoteException {
    getGeneralCase().setStatus(p0);
  }

  public String getCode() throws java.rmi.RemoteException {
    return this.getGeneralCase().getCode();
  }
  public void setCaseStatus(CaseStatus p0) throws java.rmi.RemoteException {
    this.getGeneralCase().setCaseStatus(p0);
  }
  public CaseCode getCaseCode() throws java.rmi.RemoteException {
    return this.getGeneralCase().getCaseCode();
  }
  public void setOwner(User p0) throws java.rmi.RemoteException {
    this.getGeneralCase().setOwner(p0);
  }
  public Case getParentCase() throws java.rmi.RemoteException {
    return this.getGeneralCase().getParentCase();
  }
  public void setCode(String p0) throws java.rmi.RemoteException {
    this.getGeneralCase().setCode(p0);
  }
  public User getOwner() throws java.rmi.RemoteException {
    return this.getGeneralCase().getOwner();
  }
  public CaseStatus getCaseStatus() throws java.rmi.RemoteException {
    return this.getGeneralCase().getCaseStatus();
  }
  public String getStatus() throws java.rmi.RemoteException {
    return this.getGeneralCase().getStatus();
  }
  public void setCreated(Timestamp p0) throws java.rmi.RemoteException {
    this.getGeneralCase().setCreated(p0);
  }

  public Iterator getChildren() {
    try{
      return this.getGeneralCase().getChildren();
    }
    catch(RemoteException rme){
      throw new EJBException(rme.getMessage());
    }
  }

  public boolean getAllowsChildren() {
    try{
      return this.getGeneralCase().getAllowsChildren();
    }
    catch(RemoteException rme){
      throw new EJBException(rme.getMessage());
    }
  }
  public ICTreeNode getChildAtIndex(int childIndex) {
    try{
      return this.getGeneralCase().getChildAtIndex(childIndex);
    }
    catch(RemoteException rme){
      throw new EJBException(rme.getMessage());
    }
  }
  public int getChildCount() {
    try{
      return this.getGeneralCase().getChildCount();
    }
    catch(RemoteException rme){
      throw new EJBException(rme.getMessage());
    }
  }
  public int getIndex(ICTreeNode node) {
    try{
      return this.getGeneralCase().getIndex(node);
    }
    catch(RemoteException rme){
      throw new EJBException(rme.getMessage());
    }
  }
  public ICTreeNode getParentNode() {
    try{
      return this.getGeneralCase().getParentNode();
    }
    catch(RemoteException rme){
      throw new EJBException(rme.getMessage());
    }
  }
  public boolean isLeaf() {
    try{
      return this.getGeneralCase().isLeaf();
    }
    catch(RemoteException rme){
      throw new EJBException(rme.getMessage());
    }
  }
  public String getNodeName() {
    try{
      return this.getGeneralCase().getNodeName();
    }
    catch(RemoteException rme){
      throw new EJBException(rme.getMessage());
    }
  }
  public int getNodeID() {
    try{
      return this.getGeneralCase().getNodeID();
    }
    catch(RemoteException rme){
      throw new EJBException(rme.getMessage());
    }
  }
  public int getSiblingCount() {
    try{
      return this.getGeneralCase().getSiblingCount();
    }
    catch(RemoteException rme){
      throw new EJBException(rme.getMessage());
    }
  }

}