/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
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
 *  PersistentDeleted.java    March 10, 2001
 */

package org.apache.jdo.impl.state;

import java.util.BitSet;

import javax.jdo.Transaction;
import javax.jdo.JDOUserException;

import org.apache.jdo.state.StateManagerInternal;
import org.apache.jdo.store.StoreManager;


/**
 * This class represents PersistentDeleted state specific state transitions as requested
 * by StateManagerImpl. This state is the result of a call to deletePersistent of a
 * persistent instance.
 *
 * @author Marina Vatkina
 */
class PersistentDeleted extends LifeCycleState {

    PersistentDeleted() {
        // these flags are set only in the constructor 
        // and shouldn't be changed afterwards
        // (cannot make them final since they are declared in superclass 
        // but their values are specific to subclasses)
        isPersistent = true;
        isTransactional = true;
        isDirty = true;
        isNew = false;
        isDeleted = true;

        isNavigable = false;
        isRefreshable = true;
        isBeforeImageUpdatable = false;
        isFlushed = false;

    // The following flag does not allow merge
        needMerge = false;

        stateType =  P_DELETED;
    }


   /**
    * @see LifeCycleState#transitionReadField(StateManagerImpl sm,
    * Transaction tx)
    * @throws JDOUserException on read access
    */
    protected LifeCycleState transitionReadField(StateManagerImpl sm,
        Transaction tx) {
        // Cannot read a deleted object
        throw new JDOUserException(msg.msg(
                "EXC_AccessDeletedField")); // NOI18N
    }

   /**
    * @see LifeCycleState#transitionWriteField(StateManagerImpl sm,
    * Transaction tx)
    * @throws JDOUserException on write access
    */
    protected LifeCycleState transitionWriteField(StateManagerImpl sm,
        Transaction tx) {
        // Cannot update a deleted object
        throw new JDOUserException(msg.msg(
                "EXC_AccessDeletedField")); // NOI18N
    }

   /**   
    * @see LifeCycleState#flush(BitSet loadedFields, BitSet dirtyFields,
    *   StoreManager srm, StateManagerImpl sm)
    */
    protected LifeCycleState flush(BitSet loadedFields, BitSet dirtyFields,
        StoreManager srm, StateManagerImpl sm) {
        if (srm.delete(loadedFields, dirtyFields, sm) ==
            StateManagerInternal.FLUSHED_COMPLETE) {
            sm.markAsFlushed();
            return changeState(P_DELETED_FLUSHED);
        }
        return this;
    }
}









