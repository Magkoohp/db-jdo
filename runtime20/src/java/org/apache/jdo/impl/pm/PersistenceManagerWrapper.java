/*
 * Copyright 2005 The Apache Software Foundation.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at 
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and 
 * limitations under the License.
 */

/*
 * PersistenceManagerWrapper.java
 *
 * Created on January 16, 2001
 */
 
package org.apache.jdo.impl.pm;

import java.util.*;

import javax.jdo.*;
import javax.jdo.datastore.JDOConnection;
import javax.jdo.datastore.Sequence;
import javax.jdo.listener.InstanceLifecycleListener;

import org.apache.jdo.pm.PersistenceManagerInternal;
import org.apache.jdo.util.I18NHelper;


/**  
 * This is a thin wrapper for the current implemetation of javax.jdo.PersistenceManager
 * interface. Delegates most of method execution to the corresponding instance of 
 * the PersistenceManagerImpl. Becomes invalid after PersistenceManager is closed.
 *  
 * @author Marina Vatkina 
 */  
public class PersistenceManagerWrapper implements PersistenceManager {

    // Previous  PersistenceManagerWrapper
    private PersistenceManagerWrapper prev = null;

    // Actual  PersistenceManager
    private PersistenceManagerImpl pm = null;

    // Boolean flag that allows to use this wrapper
    private boolean isValid = false;

    /**
     * I18N message handler
     */
     private final static I18NHelper msg = 
        I18NHelper.getInstance("org.apache.jdo.impl.pm.Bundle"); // NOI18N

    // Constructed by  PersistenceManagerFactoryImpl
    PersistenceManagerWrapper(PersistenceManagerImpl pm) {
        this.pm = pm;
        prev = (PersistenceManagerWrapper)pm.getCurrentWrapper();
        pm.pushCurrentWrapper(this);
        isValid = true;
    }

    /** 
     * @see javax.jdo.PersistenceManager#isClosed()
     */
    public boolean isClosed() {
        if (isValid) {
            return pm.isClosed();
        } else {
            return true;
        }
    }


   /**
    * @see javax.jdo.PersistenceManager#close()
    */
    public void close() {
        if (isValid) { 
            pm.popCurrentWrapper(prev);
            isValid = false;
        } else { 
            throw new JDOFatalUserException(msg.msg(
                "EXC_PersistenceManagerClosed"));// NOI18N
        } 
    }

     /** 
      * @see javax.jdo.PersistenceManager#currentTransaction()
      */
    public Transaction currentTransaction() {
        if (isValid) { 
            return pm.currentTransaction();
        } else { 
            throw new JDOFatalUserException(msg.msg(
                "EXC_PersistenceManagerClosed"));// NOI18N
        } 
   }

    /**
     * @see javax.jdo.PersistenceManager#setIgnoreCache(boolean flag)
     */
    public void setIgnoreCache(boolean flag) {
        if (isValid) {
            pm.setIgnoreCache(flag);
        } else {
            throw new JDOFatalUserException(msg.msg(
                "EXC_PersistenceManagerClosed"));// NOI18N
        }
    }

    /**
     * @see javax.jdo.PersistenceManager#getIgnoreCache()
     */
    public boolean getIgnoreCache() {
        if (isValid) {
            return pm.getIgnoreCache();
        } else {
            throw new JDOFatalUserException(msg.msg(
                "EXC_PersistenceManagerClosed"));// NOI18N
        }

    }

    /**
     * @see javax.jdo.PersistenceManager#setIgnoreCache(boolean flag)
     */
    public void setDetachAllOnCommit(boolean flag) {
        if (isValid) {
            pm.setDetachAllOnCommit(flag);
        } else {
            throw new JDOFatalUserException(msg.msg(
                "EXC_PersistenceManagerClosed"));// NOI18N
        }
    }

    /**
     * @see javax.jdo.PersistenceManager#getIgnoreCache()
     */
    public boolean getDetachAllOnCommit() {
        if (isValid) {
            return pm.getDetachAllOnCommit();
        } else {
            throw new JDOFatalUserException(msg.msg(
                "EXC_PersistenceManagerClosed"));// NOI18N
        }
    }

    /**
     * @see javax.jdo.PersistenceManager#getMultithreaded()
     */
    public boolean getMultithreaded() {
        if (isValid) {
            return pm.getMultithreaded();
        } else {
            throw new JDOFatalUserException(msg.msg(
                "EXC_PersistenceManagerClosed"));// NOI18N
        }
    }

    /**
     * @see javax.jdo.PersistenceManager#setMultithreaded(boolean flag)
     */
    public void setMultithreaded(boolean flag) {
        if (isValid) {
            pm.setMultithreaded(flag);
        } else {
            throw new JDOFatalUserException(msg.msg(
                "EXC_PersistenceManagerClosed"));// NOI18N
        }
    }

    /**
     * @see javax.jdo.PersistenceManager#evict(Object pc)
     */
    public  void evict(Object pc) {
        if (isValid) {
            pm.evict(pc);
        } else {
            throw new JDOFatalUserException(msg.msg(
                "EXC_PersistenceManagerClosed"));// NOI18N
        }
    }

    /**
     * @see javax.jdo.PersistenceManager#evictAll(Object[] pcs)
     */
    public  void evictAll(Object[] pcs) {
        if (isValid) {
            pm.evictAll(pcs);
        } else {
            throw new JDOFatalUserException(msg.msg(
                "EXC_PersistenceManagerClosed"));// NOI18N
        }
    }

    /**
     * @see javax.jdo.PersistenceManager#evictAll(Collection pcs)
     */
    public  void evictAll(Collection pcs) {
        if (isValid) {
            pm.evictAll(pcs);
        } else {
            throw new JDOFatalUserException(msg.msg(
                "EXC_PersistenceManagerClosed"));// NOI18N
        }
    }

    /**
     * @see javax.jdo.PersistenceManager#evictAll()
     */
    public  void evictAll() {
        if (isValid) {
            pm.evictAll();
        } else {
            throw new JDOFatalUserException(msg.msg(
                "EXC_PersistenceManagerClosed"));// NOI18N
        }
    }

    /**
     * @see javax.jdo.PersistenceManager#refresh(Object pc)
     */
    public  void refresh(Object pc) {
        if (isValid) {
            pm.refresh(pc);
        } else {
            throw new JDOFatalUserException(msg.msg(
                "EXC_PersistenceManagerClosed"));// NOI18N
        }
    }

    /**
     * @see javax.jdo.PersistenceManager#refreshAll(Object[] pcs)
     */
    public  void refreshAll(Object[] pcs) {
        if (isValid) {
            pm.refreshAll(pcs);
        } else {
            throw new JDOFatalUserException(msg.msg(
                "EXC_PersistenceManagerClosed"));// NOI18N
        }
    }

    /**
     * @see javax.jdo.PersistenceManager#refreshAll(Collection pcs)
     */
    public  void refreshAll(Collection pcs) {
        if (isValid) {
            pm.refreshAll(pcs);
        } else {
            throw new JDOFatalUserException(msg.msg(
                "EXC_PersistenceManagerClosed"));// NOI18N
        }
    }

    /**
     * @see javax.jdo.PersistenceManager#refreshAll()
     */
    public  void refreshAll() {
        if (isValid) {
            pm.refreshAll();
        } else {
            throw new JDOFatalUserException(msg.msg(
                "EXC_PersistenceManagerClosed"));// NOI18N
        }
    }

    /**
     * @see javax.jdo.PersistenceManager#refreshAll(JDOException jdoe)
     */
    public  void refreshAll(JDOException jdoe) {
        if (isValid) {
            pm.refreshAll(jdoe);
        } else {
            throw new JDOFatalUserException(msg.msg(
                "EXC_PersistenceManagerClosed"));// NOI18N
        }
    }

    /** 
     * @see javax.jdo.PersistenceManager#newQuery()
     */
    public Query newQuery(){
        if (isValid) { 
            return pm.newQuery();
        } else { 
            throw new JDOFatalUserException(msg.msg(
                "EXC_PersistenceManagerClosed"));// NOI18N
        } 
    }

    /** 
     * @see javax.jdo.PersistenceManager#newQuery(Object compiled)
     */
    public Query newQuery(Object compiled){
        if (isValid) { 
            return pm.newQuery(compiled);
        } else { 
            throw new JDOFatalUserException(msg.msg(
                "EXC_PersistenceManagerClosed"));// NOI18N
        } 
    }
    
    /** 
     * @see javax.jdo.PersistenceManager#newQuery(String query)
     */
    public Query newQuery(String query){
        if (isValid) { 
            return pm.newQuery(query);
        } else { 
            throw new JDOFatalUserException(msg.msg(
                "EXC_PersistenceManagerClosed"));// NOI18N
        } 
    }

    /** 
     * @see javax.jdo.PersistenceManager#newQuery(Class cls)
     */
    public Query newQuery(Class cls){
        if (isValid) { 
            return pm.newQuery(cls);
        } else { 
            throw new JDOFatalUserException(msg.msg(
                "EXC_PersistenceManagerClosed"));// NOI18N
        } 
    }
    
    /** 
     * @see javax.jdo.PersistenceManager#newQuery(Extent cln)
     */
    public Query newQuery(Extent cln){
        if (isValid) { 
            return pm.newQuery(cln);
        } else { 
            throw new JDOFatalUserException(msg.msg(
                "EXC_PersistenceManagerClosed"));// NOI18N
        } 
    }
    
    /** 
     * @see javax.jdo.PersistenceManager#newQuery(Class cls,Collection cln)
     */
    public Query newQuery(Class cls,Collection cln){
        if (isValid) { 
            return pm.newQuery(cls, cln);
        } else { 
            throw new JDOFatalUserException(msg.msg(
                "EXC_PersistenceManagerClosed"));// NOI18N
        } 
   }
    
    /** 
     * @see javax.jdo.PersistenceManager#newQuery(String language, Object query)
     */
    public Query newQuery (String language, Object query){
        if (isValid) { 
            return pm.newQuery(language, query);
        } else { 
            throw new JDOFatalUserException(msg.msg(
                "EXC_PersistenceManagerClosed"));// NOI18N
        } 
    }
    
    /** 
     * @see javax.jdo.PersistenceManager#newQuery(Class cls, String filter)
     */
    public Query newQuery (Class cls, String filter){
        if (isValid) { 
            return pm.newQuery(cls, filter);
        } else { 
            throw new JDOFatalUserException(msg.msg(
                "EXC_PersistenceManagerClosed"));// NOI18N
        } 
    }
    
    /** 
     * @see javax.jdo.PersistenceManager#newQuery(Class cls, Collection cln, String filter)
     */
    public Query newQuery (Class cls, Collection cln, String filter){
        if (isValid) { 
            return pm.newQuery(cls, cln, filter);
        } else { 
            throw new JDOFatalUserException(msg.msg(
                "EXC_PersistenceManagerClosed"));// NOI18N
        } 
    }
    
    /** 
     * @see javax.jdo.PersistenceManager#newQuery(Extent cln, String filter)
     */
    public Query newQuery (Extent cln, String filter){
        if (isValid) { 
            return pm.newQuery(cln, filter);
        } else { 
            throw new JDOFatalUserException(msg.msg(
                "EXC_PersistenceManagerClosed"));// NOI18N
        } 
    }

    /** 
     * @see javax.jdo.PersistenceManager#newNamedQuery(Class cls, String queryName)
     */
    public Query newNamedQuery(Class cls, String queryName) {
        if (isValid) { 
            return pm.newNamedQuery(cls, queryName);
        } else { 
            throw new JDOFatalUserException(msg.msg(
                "EXC_PersistenceManagerClosed"));// NOI18N
        } 
    }
    
    /** 
     * @see javax.jdo.PersistenceManager#getExtent(Class persistenceCapableClass,
     * boolean subclasses)
     */
    public Extent getExtent(Class persistenceCapableClass,boolean subclasses){
        if (isValid) { 
            return pm.getExtent(persistenceCapableClass, subclasses);
        } else { 
            throw new JDOFatalUserException(msg.msg(
                "EXC_PersistenceManagerClosed"));// NOI18N
        } 
    }

    /** 
     * @see javax.jdo.PersistenceManager#getExtent(Class persistenceCapableClass)
     */
    public Extent getExtent(Class persistenceCapableClass){
        if (isValid) { 
            return pm.getExtent(persistenceCapableClass);
        } else { 
            throw new JDOFatalUserException(msg.msg(
                "EXC_PersistenceManagerClosed"));// NOI18N
        } 
    }

    /** 
     * @see javax.jdo.PersistenceManager#getObjectById(Object oid, boolean validate)
     */
    public Object getObjectById(Object oid, boolean validate){
        if (isValid) { 
            return pm.getObjectById(oid, validate);
        } else { 
            throw new JDOFatalUserException(msg.msg(
                "EXC_PersistenceManagerClosed"));// NOI18N
        } 
    }

    /** 
     * @see javax.jdo.PersistenceManager#getObjectById(Class cls, Object key)
     */
    public Object getObjectById(Class cls, Object key){
        if (isValid) { 
            return pm.getObjectById(cls, key);
        } else { 
            throw new JDOFatalUserException(msg.msg(
                "EXC_PersistenceManagerClosed"));// NOI18N
        } 
    }

    /** 
     * @see javax.jdo.PersistenceManager#getObjectById(Object oid)
     */
    public Object getObjectById(Object oid){
        if (isValid) { 
            return pm.getObjectById(oid);
        } else { 
            throw new JDOFatalUserException(msg.msg(
                "EXC_PersistenceManagerClosed"));// NOI18N
        } 
    }
    
    /** 
     * @see javax.jdo.PersistenceManager#getObjectId(Object pc)
     */
    public Object getObjectId(Object pc){
        if (isValid) { 
            return pm.getObjectId(pc);
        } else { 
            throw new JDOFatalUserException(msg.msg(
                "EXC_PersistenceManagerClosed"));// NOI18N
        } 
   }
    
    /** 
     * @see javax.jdo.PersistenceManager#getTransactionalObjectId (Object pc)
     */
    public Object getTransactionalObjectId (Object pc) {
        if (isValid) { 
            return pm.getTransactionalObjectId(pc);
        } else { 
            throw new JDOFatalUserException(msg.msg(
                "EXC_PersistenceManagerClosed"));// NOI18N
        } 
   }
    
    /** 
     * @see javax.jdo.PersistenceManager#newObjectIdInstance (Class pcClass, Object key)
     */
    public Object newObjectIdInstance (Class pcClass, Object key) {
        if (isValid) { 
            return pm.newObjectIdInstance (pcClass, key);
        } else { 
            throw new JDOFatalUserException(msg.msg(
                "EXC_PersistenceManagerClosed"));// NOI18N
        } 
    }

    /** 
     * @see javax.jdo.PersistenceManager#getObjectsById (Collection oids, boolean validate)
     */
    public Collection getObjectsById (Collection oids, boolean validate) {
        if (isValid) { 
            return pm.getObjectsById (oids, validate);
        } else { 
            throw new JDOFatalUserException(msg.msg(
                "EXC_PersistenceManagerClosed"));// NOI18N
        } 
    }
    
    /** 
     * @see javax.jdo.PersistenceManager#getObjectsById (Collection oids)
     */
    public Collection getObjectsById (Collection oids) {
        if (isValid) { 
            return pm.getObjectsById (oids);
        } else { 
            throw new JDOFatalUserException(msg.msg(
                "EXC_PersistenceManagerClosed"));// NOI18N
        } 
    }

    /** 
     * @see javax.jdo.PersistenceManager#getObjectsById (Object[] oids, boolean validate)
     */
    public Object[] getObjectsById (Object[] oids, boolean validate) {
        if (isValid) { 
            return pm.getObjectsById (oids, validate);
        } else { 
            throw new JDOFatalUserException(msg.msg(
                "EXC_PersistenceManagerClosed"));// NOI18N
        } 
    }

    /** 
     * @see javax.jdo.PersistenceManager#getObjectsById (Object[] oids)
     */
    public Object[] getObjectsById (Object[] oids) {
        if (isValid) { 
            return pm.getObjectsById (oids);
        } else { 
            throw new JDOFatalUserException(msg.msg(
                "EXC_PersistenceManagerClosed"));// NOI18N
        } 
    }

    /** 
     * @see javax.jdo.PersistenceManager#makePersistent(Object pc)
     */
    public void makePersistent(Object pc){
        if (isValid) { 
            pm.makePersistent(pc);
        } else { 
            throw new JDOFatalUserException(msg.msg(
                "EXC_PersistenceManagerClosed"));// NOI18N
        } 
   }
    
    /** 
     * @see javax.jdo.PersistenceManager#makePersistentAll(Object[] pc)
     */
    public void makePersistentAll(Object[] pcs){
        if (isValid) { 
            pm.makePersistentAll(pcs);
        } else { 
            throw new JDOFatalUserException(msg.msg(
                "EXC_PersistenceManagerClosed"));// NOI18N
        } 
   }
    
    /** 
     * @see javax.jdo.PersistenceManager#makePersistentAll(Collection pcs)
     */
    public void makePersistentAll (Collection pcs){
        if (isValid) { 
            pm.makePersistentAll(pcs);
        } else { 
            throw new JDOFatalUserException(msg.msg(
                "EXC_PersistenceManagerClosed"));// NOI18N
        } 
   }
    
    /** 
     * @see javax.jdo.PersistenceManager#deletePersistent(Object pc)
     */
    public void deletePersistent(Object pc){
        if (isValid) { 
            pm.deletePersistent(pc);
        } else { 
            throw new JDOFatalUserException(msg.msg(
                "EXC_PersistenceManagerClosed"));// NOI18N
        } 
   }
    
    /** 
     * @see javax.jdo.PersistenceManager#deletePersistentAll(Object[] pc)
     */
    public void deletePersistentAll (Object[] pcs){
        if (isValid) { 
            pm.deletePersistentAll(pcs);
        } else { 
            throw new JDOFatalUserException(msg.msg(
                "EXC_PersistenceManagerClosed"));// NOI18N
        } 
   }
    
    /** 
     * @see javax.jdo.PersistenceManager#deletePersistentAll(Collection pc)
     */
    public void deletePersistentAll (Collection pcs){
        if (isValid) { 
            pm.deletePersistentAll(pcs);
        } else { 
            throw new JDOFatalUserException(msg.msg(
                "EXC_PersistenceManagerClosed"));// NOI18N
        } 
   }
    
    /** 
     * @see javax.jdo.PersistenceManager#makeTransient(Object pc)
     */
    public void makeTransient(Object pc){
        if (isValid) { 
            pm.makeTransient(pc);
        } else { 
            throw new JDOFatalUserException(msg.msg(
                "EXC_PersistenceManagerClosed"));// NOI18N
        } 
   }
    
    /** 
     * @see javax.jdo.PersistenceManager#makeTransientAll(Object[] pc)
     */
    public void makeTransientAll(Object[] pcs){
        if (isValid) { 
            pm.makeTransientAll(pcs);
        } else { 
            throw new JDOFatalUserException(msg.msg(
                "EXC_PersistenceManagerClosed"));// NOI18N
        } 
   }
    
    /** 
     * @see javax.jdo.PersistenceManager#makeTransientAll(Collection pcs)
     */
    public void makeTransientAll (Collection pcs){
        if (isValid) { 
            pm.makeTransientAll(pcs);
        } else { 
            throw new JDOFatalUserException(msg.msg(
                "EXC_PersistenceManagerClosed"));// NOI18N
        } 
   }
    
    /** 
     * @see javax.jdo.PersistenceManager#makeTransactional(Object pc)
     */
    public void makeTransactional(Object pc){
        if (isValid) { 
            pm.makeTransactional(pc);
        } else { 
            throw new JDOFatalUserException(msg.msg(
                "EXC_PersistenceManagerClosed"));// NOI18N
        } 
   }
    
    /** 
     * @see javax.jdo.PersistenceManager#makeTransactionalAll(Object[] pc)
     */
    public void makeTransactionalAll(Object[] pcs){
        if (isValid) { 
            pm.makeTransactionalAll(pcs);
        } else { 
            throw new JDOFatalUserException(msg.msg(
                "EXC_PersistenceManagerClosed"));// NOI18N
        } 
   }
    
    /** 
     * @see javax.jdo.PersistenceManager#makeTransactionalAll(Collection pcs)
     */
    public void makeTransactionalAll (Collection pcs){
        if (isValid) { 
            pm.makeTransactionalAll(pcs);
        } else { 
            throw new JDOFatalUserException(msg.msg(
                "EXC_PersistenceManagerClosed"));// NOI18N
        } 
   }
    
    /*
     * @see javax.jdo.PersistenceManager#makeNontransactional(Object pc)
     */
    public void makeNontransactional(Object pc){
        if (isValid) { 
            pm.makeNontransactional(pc);
        } else { 
            throw new JDOFatalUserException(msg.msg(
                "EXC_PersistenceManagerClosed"));// NOI18N
        } 
   }
    
    /** 
     * @see javax.jdo.PersistenceManager#makeNontransactionalAll(Object[] pc)
     */
    public void makeNontransactionalAll(Object[] pcs){
        if (isValid) { 
            pm.makeNontransactionalAll(pcs);
        } else { 
            throw new JDOFatalUserException(msg.msg(
                "EXC_PersistenceManagerClosed"));// NOI18N
        } 
   }
    
    /** 
     * @see javax.jdo.PersistenceManager#makeNontransactionalAll(Collection pcs)
     */
    public void makeNontransactionalAll (Collection pcs){
        if (isValid) { 
            pm.makeNontransactionalAll(pcs);
        } else { 
            throw new JDOFatalUserException(msg.msg(
                "EXC_PersistenceManagerClosed"));// NOI18N
        } 
   }
    
    /** Retrieve an instance from the store.  This is only a hint to
     * the PersistenceManager that the application intends to use the
     * instance, and its field values should be retrieved.
     * <P>The PersistenceManager might use policy information about the
     * class to retrieve associated instances.
     */
    public void retrieve(Object pc) {
        if (isValid) { 
            pm.retrieve(pc);
        } else { 
            throw new JDOFatalUserException(msg.msg(
                "EXC_PersistenceManagerClosed"));// NOI18N
        } 
    }
    
    /** Retrieve field values of instances from the store.  This tells
     * the <code>PersistenceManager</code> that the application intends to use the
     * instances, and all field values must be retrieved.
     * <P>The <code>PersistenceManager</code> might use policy information about the
     * class to retrieve associated instances.
     * @param pcs the instances
     */
    public void retrieveAll(Object[] pcs) {
        if (isValid) { 
            pm.retrieveAll(pcs);
        } else { 
            throw new JDOFatalUserException(msg.msg(
                "EXC_PersistenceManagerClosed"));// NOI18N
        } 
    }
    
    /** Retrieve field values of instances from the store.  This tells
     * the <code>PersistenceManager</code> that the application intends to use the
     * instances, and their field values should be retrieved.  The fields
     * in the default fetch group must be retrieved, and the implementation
     * might retrieve more fields than the default fetch group.
     * <P>The <code>PersistenceManager</code> might use policy information about the
     * class to retrieve associated instances.
     * @param pcs the instances
     * @param DFGOnly whether to retrieve only the default fetch group fields
     * @since 1.0.1
     */
    public void retrieveAll (Object[] pcs, boolean DFGOnly) {
        if (isValid) { 
            pm.retrieveAll(pcs, DFGOnly);
        } else { 
            throw new JDOFatalUserException(msg.msg(
                "EXC_PersistenceManagerClosed"));// NOI18N
        } 
    }
           
    /** Retrieve field values of instances from the store.  This tells
     * the <code>PersistenceManager</code> that the application intends to use the
     * instances, and all field values must be retrieved.
     * <P>The <code>PersistenceManager</code> might use policy information about the
     * class to retrieve associated instances.
     * @param pcs the instances
     */
    public void retrieveAll(Collection pcs) {
        if (isValid) { 
            pm.retrieveAll(pcs);
        } else { 
            throw new JDOFatalUserException(msg.msg(
                "EXC_PersistenceManagerClosed"));// NOI18N
        } 
    }

    /** Retrieve field values of instances from the store.  This tells
     * the <code>PersistenceManager</code> that the application intends to use the
     * instances, and their field values should be retrieved.  The fields
     * in the default fetch group must be retrieved, and the implementation
     * might retrieve more fields than the default fetch group.
     * <P>The <code>PersistenceManager</code> might use policy information about the
     * class to retrieve associated instances.
     * @param pcs the instances
     * @param DFGOnly whether to retrieve only the default fetch group fields
     * @since 1.0.1
     */
    public void retrieveAll (Collection pcs, boolean DFGOnly) {
        if (isValid) {
            pm.retrieveAll(pcs, DFGOnly);
        } else {
            throw new JDOFatalUserException(msg.msg(
                "EXC_PersistenceManagerClosed"));// NOI18N
        } 
    }
            
    
    /** 
     * @see javax.jdo.PersistenceManager#getPersistenceManagerFactory()
     */
    public PersistenceManagerFactory getPersistenceManagerFactory(){
        if (isValid) { 
            return pm.getPersistenceManagerFactory();
        } else { 
            throw new JDOFatalUserException(msg.msg(
                "EXC_PersistenceManagerClosed"));// NOI18N
        } 
   }
    
    /** 
     * @see javax.jdo.PersistenceManager#setUserObject(Object o)
     */
    public void setUserObject(Object o){
        if (isValid) { 
            pm.setUserObject(o);
        } else { 
            throw new JDOFatalUserException(msg.msg(
                "EXC_PersistenceManagerClosed"));// NOI18N
        } 
    }
    
    /** 
     * @see javax.jdo.PersistenceManager#getUserObject()
     */
    public Object getUserObject(){
        if (isValid) { 
            return pm.getUserObject();
        } else { 
            throw new JDOFatalUserException(msg.msg(
                "EXC_PersistenceManagerClosed"));// NOI18N
        } 
    }
    
    /** 
     * @see javax.jdo.PersistenceManager#getObjectIdClass(Class cls)
     */
    public Class getObjectIdClass(Class cls){
        if (isValid) { 
            return pm.getObjectIdClass(cls);
        } else { 
            throw new JDOFatalUserException(msg.msg(
                "EXC_PersistenceManagerClosed"));// NOI18N
        } 
    }

    /** 
     * @see javax.jdo.PersistenceManager#detachCopy (Object pc)
     */
    public Object detachCopy (Object pc) {
        if (isValid) { 
            return pm.detachCopy(pc);
        } else { 
            throw new JDOFatalUserException(msg.msg(
                "EXC_PersistenceManagerClosed"));// NOI18N
        } 
    }

    /** 
     * @see javax.jdo.PersistenceManager#detachCopyAll (Collection pcs)
     */
    public Collection detachCopyAll (Collection pcs) {
        if (isValid) { 
            return pm.detachCopyAll(pcs);
        } else { 
            throw new JDOFatalUserException(msg.msg(
                "EXC_PersistenceManagerClosed"));// NOI18N
        } 
    }

    /** 
     * @see javax.jdo.PersistenceManager#detachCopyAll (Object [] pcs)
     */
    public Object[] detachCopyAll (Object [] pcs) {
        if (isValid) { 
            return pm.detachCopyAll(pcs);
        } else { 
            throw new JDOFatalUserException(msg.msg(
                "EXC_PersistenceManagerClosed"));// NOI18N
        } 
    }

    /** 
     * @see javax.jdo.PersistenceManager#attachCopy (Object pc, boolean makeTransactional)
     */
    public Object attachCopy (Object pc, boolean makeTransactional) {
        if (isValid) { 
            return pm.attachCopy(pc, makeTransactional);
        } else { 
            throw new JDOFatalUserException(msg.msg(
                "EXC_PersistenceManagerClosed"));// NOI18N
        } 
    }

    /** 
     * @see javax.jdo.PersistenceManager#attachCopyAll (Collection pcs, boolean makeTransactional)
     */
    public Collection attachCopyAll (Collection pcs, boolean makeTransactional) {
        if (isValid) { 
            return pm.attachCopyAll(pcs, makeTransactional);
        } else { 
            throw new JDOFatalUserException(msg.msg(
                "EXC_PersistenceManagerClosed"));// NOI18N
        } 
    }

    /** 
     * @see javax.jdo.PersistenceManager#attachCopyAll (Object[] pcs, boolean makeTransactional)
     */
    public Object[] attachCopyAll (Object[] pcs, boolean makeTransactional) {
        if (isValid) { 
            return pm.attachCopyAll(pcs, makeTransactional);
        } else { 
            throw new JDOFatalUserException(msg.msg(
                "EXC_PersistenceManagerClosed"));// NOI18N
        } 
    }

    /** 
     * @see javax.jdo.PersistenceManager#putUserObject (Object key, Object val)
     */
    public Object putUserObject (Object key, Object val) {
        if (isValid) { 
            return pm.putUserObject(key, val);
        } else { 
            throw new JDOFatalUserException(msg.msg(
                "EXC_PersistenceManagerClosed"));// NOI18N
        } 
    }

    /** 
     * @see javax.jdo.PersistenceManager#getUserObject (Object key)
     */
    public Object getUserObject (Object key) {
        if (isValid) { 
            return pm.getUserObject(key);
        } else { 
            throw new JDOFatalUserException(msg.msg(
                "EXC_PersistenceManagerClosed"));// NOI18N
        } 
    }

    /** 
     * @see javax.jdo.PersistenceManager#removeUserObject (Object key)
     */
    public Object removeUserObject (Object key) {
        if (isValid) { 
            return pm.removeUserObject(key);
        } else { 
            throw new JDOFatalUserException(msg.msg(
                "EXC_PersistenceManagerClosed"));// NOI18N
        } 
    }

    /** 
     * @see javax.jdo.PersistenceManager#flush ()
     */
    public void flush () {
        if (isValid) { 
            pm.flush();
        } else { 
            throw new JDOFatalUserException(msg.msg(
                "EXC_PersistenceManagerClosed"));// NOI18N
        } 
    }

    /** 
     * @see javax.jdo.PersistenceManager#checkConsistency ()
     */
    public void checkConsistency () {
        if (isValid) { 
            pm.checkConsistency();
        } else { 
            throw new JDOFatalUserException(msg.msg(
                "EXC_PersistenceManagerClosed"));// NOI18N
        } 
    }

    /** 
     * @see javax.jdo.PersistenceManager#getFetchPlan ()
     */
    public FetchPlan getFetchPlan () {
        if (isValid) { 
            return pm.getFetchPlan();
        } else { 
            throw new JDOFatalUserException(msg.msg(
                "EXC_PersistenceManagerClosed"));// NOI18N
        } 
    }

    /** 
     * @see javax.jdo.PersistenceManager#newInstance (Class pcClass)
     */
    public Object newInstance (Class pcClass) {
        if (isValid) { 
            return pm.newInstance(pcClass);
        } else { 
            throw new JDOFatalUserException(msg.msg(
                "EXC_PersistenceManagerClosed"));// NOI18N
        } 
    }

    /** 
     * @see javax.jdo.PersistenceManager#getSequence (String name)
     */
    public Sequence getSequence (String name) {
        if (isValid) { 
            return pm.getSequence(name);
        } else { 
            throw new JDOFatalUserException(msg.msg(
                "EXC_PersistenceManagerClosed"));// NOI18N
        } 
    }

    /** 
     * @see javax.jdo.PersistenceManager#getDataStoreConnection ()
     */
    public JDOConnection getDataStoreConnection () {
        if (isValid) { 
            return pm.getDataStoreConnection();
        } else { 
            throw new JDOFatalUserException(msg.msg(
                "EXC_PersistenceManagerClosed"));// NOI18N
        } 
    }

    /** 
     * @see javax.jdo.PersistenceManager#addInstanceLifecycleListener (
     * InstanceLifecycleListener listener, Class[] classes)
     */
    public void addInstanceLifecycleListener (
        InstanceLifecycleListener listener, Class[] classes) {
        if (isValid) { 
            pm.addInstanceLifecycleListener(listener, classes);
        } else { 
            throw new JDOFatalUserException(msg.msg(
                "EXC_PersistenceManagerClosed"));// NOI18N
        } 
    }

    /** 
     * @see javax.jdo.PersistenceManager#removeInstanceLifecycleListener (
     * InstanceLifecycleListener listener)
     */
    public void removeInstanceLifecycleListener (
        InstanceLifecycleListener listener) {
        if (isValid) { 
            pm.removeInstanceLifecycleListener(listener);
        } else { 
            throw new JDOFatalUserException(msg.msg(
                "EXC_PersistenceManagerClosed"));// NOI18N
        } 
    }

    /**
     * Returns PersistenceManagerInternal associated with this wrapper.
     * This method should be accessed by the PersistenceManagerInternal
     * only.
     * @return PersistenceManagerInternal.
     */
    protected PersistenceManagerInternal getPersistenceManager() {
        return (PersistenceManagerInternal)pm;
    }

    /** 
     * Returns a hash code value for this PersistenceManagerWrapper.
     * @return  a hash code value for this PersistenceManagerWrapper.
     */
    public int hashCode() {
        return pm.hashCode();
    }

    /**  
     * Indicates whether some other object is "equal to" this one.
     * @param   obj   the reference object with which to compare.
     * @return  <code>true</code> if this object is the same as the obj
     *          argument; <code>false</code> otherwise.
     */  
    public boolean equals(Object obj) {
        if (obj instanceof PersistenceManagerWrapper) {
            return (((PersistenceManagerWrapper)obj).pm == this.pm);

        } else if (obj instanceof PersistenceManagerImpl) {
            return (((PersistenceManagerImpl)obj) == this.pm);
        }
        return false;
    }
}
