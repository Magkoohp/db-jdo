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

/**
 * AnnotatedClassAttribute represents a class level attribute
 * class file which identifies the level of annotation of the class.
 */
public class AnnotatedClassAttribute extends ClassAttribute {

    /* The expected attribute name */
    public final static String expectedAttrName = "filter.annotatedClass";

    /* The expected attribute version */
    public final static short expectedAttrVersion = 1;

    /* Bit mask indicating that the class was filter generated */
    public final static short generatedFlag = 0x1;

    /* Bit mask indicating that the class was filter annotated */
    public final static short annotatedFlag = 0x2;

    /* Bit mask indicating that the class was "repackaged" or similarly
     * modified */
    public final static short modifiedFlag = 0x4;

    /* The version of the attribute */
    private short attrVersion;

    /* Flags associated with the annotation */
    private short annotationFlags;

    /* The modification date of the class file at the time of modification */
    private long classModTime;

    /* The date of the annotation */
    private long classAnnotationTime;

    /* public accessors */

    public short getVersion() {
        return attrVersion;
    }

    public void setVersion(short version) {
        attrVersion = version;
    }

    public short getFlags() {
        return annotationFlags;
    }

    public void setFlags(short flags) {
        annotationFlags = flags;
    }

    public long getModTime() {
        return classModTime;
    }

    public void setModTime(long time) {
        classModTime = time;
    }

    public long getAnnotationTime() {
        return classAnnotationTime;
    }

    public void setAnnotationTime(long time) {
        classAnnotationTime = time;
    }

    /**
     * Constructor
     */
    public AnnotatedClassAttribute(
	ConstUtf8 nameAttr, short version, short annFlags,
	long modTime, long annTime) {
        super(nameAttr);
        attrVersion = version;
        annotationFlags = annFlags;
        classModTime = modTime;
        classAnnotationTime = annTime;
    }

    /* package local methods */

    static AnnotatedClassAttribute read(
	ConstUtf8 attrName, DataInputStream data, ConstantPool pool)
        throws IOException {
        short version = data.readShort();
        short annFlags = data.readShort();
        long modTime = data.readLong();
        long annTime = data.readLong();
        return  new AnnotatedClassAttribute(attrName, version, annFlags,
                                            modTime, annTime);
    }

    void write(DataOutputStream out) throws IOException {
        out.writeShort(attrName().getIndex());
        out.writeShort(20);
        out.writeShort(attrVersion);
        out.writeShort(annotationFlags);
        out.writeLong(classModTime);
        out.writeLong(classAnnotationTime);
    }

    void print(PrintStream out, int indent) {
        ClassPrint.spaces(out, indent);
        out.println("version: " + attrVersion);
        out.println(" flags: " + annotationFlags);
        out.println(" modTime: " + classModTime);
        out.println(" annTime: " + classAnnotationTime);
    }
}
