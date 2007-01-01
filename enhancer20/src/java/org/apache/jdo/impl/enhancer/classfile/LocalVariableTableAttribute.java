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
import java.util.Vector;
import java.util.Enumeration;

/**
 * Represents the LocalVariableTable attribute within a
 * method in a class file.
 */
public class LocalVariableTableAttribute extends ClassAttribute {
    /* The expected attribute name */
    public static final String expectedAttrName = "LocalVariableTable";

    /* The list of local variables */
    private Vector localTable;

    /* public accessors */

    /**
     * Returns an enumeration of the local variables in the table
     * Each element is a LocalVariable
     */
    Enumeration variables() {
        return localTable.elements();
    }

    /**
     * Constructor for a local variable table
     */
    public LocalVariableTableAttribute(
	ConstUtf8 nameAttr, Vector lvarTable) {
        super(nameAttr);
        localTable = lvarTable;
    }

    /* package local methods */

    static LocalVariableTableAttribute read(
	ConstUtf8 attrName, DataInputStream data, CodeEnv env)
        throws IOException {
        int nVars = data.readUnsignedShort();
        Vector lvarTable = new Vector();
        while (nVars-- > 0) {
            lvarTable.addElement(LocalVariable.read(data, env));
        }
        
        return new LocalVariableTableAttribute(attrName, lvarTable);
    }

    void write(DataOutputStream out) throws IOException {
        out.writeShort(attrName().getIndex());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream tmp_out = new DataOutputStream(baos);
        tmp_out.writeShort(localTable.size());
        for (int i=0; i<localTable.size(); i++)
            ((LocalVariable) localTable.elementAt(i)).write(tmp_out);

        tmp_out.flush();
        byte tmp_bytes[] = baos.toByteArray();
        out.writeInt(tmp_bytes.length);
        out.write(tmp_bytes, 0, tmp_bytes.length);
    }

    void print(PrintStream out, int indent) {
        ClassPrint.spaces(out, indent);
        out.println("LocalVariables: ");
        for (int i=0; i<localTable.size(); i++) {
            ((LocalVariable) localTable.elementAt(i)).print(out, indent+2);
        }
    }
}

