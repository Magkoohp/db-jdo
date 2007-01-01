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


package org.apache.jdo.impl.enhancer.classfile;

import java.io.*;
import java.util.Stack;

/**
 * Abstract base class of the types which represent entries in
 * the class constant pool.
 */
abstract public class ConstBasic implements VMConstants {
    /* The index of the constant entry in the constant pool */
    protected int index = 0;

    /* public accessors */

    /* Get the index of this constant entry */
    public int getIndex() { return index; }

    /* Return the type of the constant entry - see VMConstants */
    public abstract int tag();

    /* package local methods */

    /**
     * Sets the index of this constant with its containing constant pool
     */
    void setIndex(int ind) { index = ind; }

    /**
     * Write this Constant pool entry to the output stream
     */
    abstract void formatData(DataOutputStream b) throws IOException;

    /**
     * Resolve integer index references to the actual constant pool
     * entries that they represent.  This is used during class file 
     * reading because a constant pool entry could have a forward
     * reference to a higher numbered constant.
     */
    abstract void resolve(ConstantPool p);

    /**
     * Return the index of this constant in the constant pool as
     * a decimal formatted String.
     */
    String indexAsString() { return Integer.toString(index); }

    /**
     * The constructor for subtypes
     */
    ConstBasic() {}

    /**
     * Compares this instance with another for structural equality.
     */
    //@olsen: added method
    public boolean isEqual(Stack msg, Object obj) {
        if (!(obj instanceof ConstBasic)) {
            msg.push("obj/obj.getClass() = "
                     + (obj == null ? null : obj.getClass()));
            msg.push("this.getClass() = "
                     + this.getClass());
            return false;
        }
        ConstBasic other = (ConstBasic)obj;

        return true;
    }
}
