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
 
package org.apache.jdo.tck.lifecycle;

import java.util.Iterator;

import javax.jdo.Extent;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Transaction;

import org.apache.jdo.tck.JDO_Test;
import org.apache.jdo.tck.pc.mylib.PCRect;
import org.apache.jdo.tck.pc.mylib.PCPoint;
import org.apache.jdo.tck.util.BatchTestRunner;

/**
 *<B>Title:</B> Test that Hollow Instances maintains PK
 *<BR>
 *<B>Keywords:</B> lifecycle hollow instance PK
 *<BR>
 *<B>Assertion IDs:</B> A5.5.4-3
 *<BR>
 *<B>Assertion Description: </B>
 If the instance is of a class using application identity, the hollow instance maintains its primary key fields.
 */

public class HollowInstanceMaintainsPK extends JDO_Test {

    /** */
    private static final String ASSERTION_FAILED = 
        "Assertion A5.5.4-3 (HollowInstanceMaintainsPK) failed: ";
    
    /**
     * The <code>main</code> is called when the class
     * is directly executed from the command line.
     * @param args The arguments passed to the program.
     */
    public static void main(String[] args) {
        BatchTestRunner.run(HollowInstanceMaintainsPK.class);
    }

    /** */
    public void test() {
        if (!isApplicationIdentitySupported()) {
            if (debug) logger.debug("Application Identity is not supported");
        }
        else {
            pm = getPM();
            pm.currentTransaction().begin();

            PCRect obj = getPersistentNewInstance();

            long beforeValue=obj.getId();
            pm.currentTransaction().commit(); // obj should transition to HOLLOW
            long afterValue=obj.getId();
            if (beforeValue!=afterValue) {
                fail(ASSERTION_FAILED,
                     "Key field value incorrect after commit. Expected: " + 
                     beforeValue + " Found: " + afterValue);
            }
        }
    }

    /** */
    private PCRect getPersistentNewInstance() {
        PCRect obj = new PCRect(0, new PCPoint(1,5), new PCPoint(7,3));
        pm.makePersistent(obj); // obj should transition to persistent-new
        int curr = currentState(obj);
        if( curr != PERSISTENT_NEW ){
            fail(ASSERTION_FAILED,
                 "StateTransitions: Unable to create persistent-new instance " +
                 "from transient instance via makePersistent(), state is " +
                 states[curr]);
        }
        return obj;
    }
}
