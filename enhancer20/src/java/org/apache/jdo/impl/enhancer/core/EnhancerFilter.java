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

package org.apache.jdo.impl.enhancer.core;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;

import java.util.Properties;

import org.apache.jdo.impl.enhancer.ClassFileEnhancer;
import org.apache.jdo.impl.enhancer.EnhancerFatalError;
import org.apache.jdo.impl.enhancer.EnhancerUserException;
import org.apache.jdo.impl.enhancer.OutputStreamWrapper;
import org.apache.jdo.impl.enhancer.classfile.ClassFile;
import org.apache.jdo.impl.enhancer.meta.EnhancerMetaData;
import org.apache.jdo.impl.enhancer.meta.EnhancerMetaDataUserException;
import org.apache.jdo.impl.enhancer.util.Support;







/**
 * Provides a JDO byte-code enhancer.
 */
public class EnhancerFilter
    extends Support
    implements ClassFileEnhancer
{
    static public final String DO_TIMING_STATISTICS
        = "Enhancer.doTimingStatistics";
    static public final String DUMP_CLASS
        = "Enhancer.dumpClass";
    static public final String NO_AUGMENT
        = "Enhancer.noAugment";
    static public final String NO_ANNOTATE
        = "Enhancer.noAnnotate";
    static public final String VERBOSE_LEVEL
        = "Enhancer.verboseLevel";
    static public final String VERBOSE_LEVEL_QUIET
        = "quiet";
    static public final String VERBOSE_LEVEL_WARN
        = "warn";
    static public final String VERBOSE_LEVEL_VERBOSE
        = "verbose";
    static public final String VERBOSE_LEVEL_DEBUG
        = "debug";

    /* Central repository for the options selected by
     * the user and the current state of the Filter execution */
    private Environment env = new Environment();

    /**
     * Initializes an instance of a JDO enhancer.
     * @param metaData the JDO meta-data object
     * @param settings enhancement properties
     * @param out standard ouput stream for the enhancer
     */
    protected void init(EnhancerMetaData metaData,
                        Properties  settings,
                        PrintWriter out,
                        PrintWriter err)
        throws EnhancerUserException, EnhancerFatalError
    {
        if (metaData == null) {
            throw new EnhancerFatalError(
                getI18N("enhancer.internal_error",
                        "Illegal argument: metaData == null"));
        }

        env.setEnhancerMetaData(metaData);

        final String doTiming
            = (settings == null
               ? null
               : settings.getProperty(DO_TIMING_STATISTICS));
        env.setDoTimingStatistics(Boolean.valueOf(doTiming).booleanValue());

        final String dumpClass
            = (settings == null
               ? null
               : settings.getProperty(DUMP_CLASS));
        env.setDumpClass(Boolean.valueOf(dumpClass).booleanValue());

        final String noAugment
            = (settings == null
               ? null
               : settings.getProperty(NO_AUGMENT));
        env.setNoAugment(Boolean.valueOf(noAugment).booleanValue());

        final String noAnnotate
            = (settings == null
               ? null
               : settings.getProperty(NO_ANNOTATE));
        env.setNoAnnotate(Boolean.valueOf(noAnnotate).booleanValue());

        // set verbose level
        if  (err != null) {
            env.setErrorWriter(err);
        }
        if  (out != null) {
            env.setOutputWriter(out);
        }
        final String verboseLevel
            = (settings == null ? null : settings.getProperty(VERBOSE_LEVEL));
        if (VERBOSE_LEVEL_QUIET.equals(verboseLevel)) {
            env.setVerbose(false);
            env.setQuiet(true);
        } else if (VERBOSE_LEVEL_WARN.equals(verboseLevel)) {
            env.setVerbose(false);
            env.setQuiet(false);
        } else if (VERBOSE_LEVEL_VERBOSE.equals(verboseLevel)) {
            env.setVerbose(true);
            env.setQuiet(false);
        } else if (VERBOSE_LEVEL_DEBUG.equals(verboseLevel)) {
            env.setVerbose(true);
            env.setQuiet(false);
        } else {
            env.setVerbose(false);
            env.setQuiet(false);
        }
    }

    /**
     * Creates an instance of a JDO enhancer.
     * @param metaData the JDO meta-data object
     * @param settings enhancement properties
     * @param out standard ouput stream for the enhancer
     */
    public EnhancerFilter(EnhancerMetaData metaData,
                    Properties  settings,
                    PrintWriter out,
                    PrintWriter err)
        throws EnhancerUserException, EnhancerFatalError
    {
        init(metaData, settings, out, err);
    }

    /**
     * Enhances a given class according to the JDO meta-data.
     */
    private boolean enhanceClassFile1(InputStream inClassFile,
                                      OutputStreamWrapper outClassFile)
        throws EnhancerUserException
    {
        // check arguments
        affirm(inClassFile, "Illegal argument: inClassFile == null.");
        affirm(outClassFile, "Illegal argument: outClassFile == null.");

        // parse class
        final ClassFile cf;
        final Controller cc;
        try {
            // create class file
            final DataInputStream dis = new DataInputStream(inClassFile);
            final boolean allowJDK12ClassFiles = true;
            cf = new ClassFile(dis, allowJDK12ClassFiles);
            //@lars: do not close the input stream
            //dis.close();

            // create class control
            cc = new Controller(cf, env);

            // get real class name
            final String className = cf.classNameString();
        } catch (ClassFormatError ex) {
            throw new EnhancerUserException(
                getI18N("enhancer.class_format_error"),
                ex);
        }

        // enhance class
        cc.enhanceClass();
        if (env.errorCount() > 0) {
            // retrieve error messages
            env.getErrorWriter().flush();

            throw new EnhancerUserException(env.getLastErrorMessage());
        }
        affirm(env.errorCount() == 0);

        // write class
        boolean changed = cc.updated();
        try {
            if (changed) {
                env.message("writing enhanced class " + cf.userClassName()
                            + " to output stream");
            } else {
                env.message("no changes to class " + cf.userClassName());
            }
            outClassFile.setClassName(cf.userClassName());
            final DataOutputStream dos
                = new DataOutputStream(outClassFile.getStream());
            cf.write(dos);
            dos.flush();
        } catch (IOException ex) {
            throw new EnhancerUserException(
                getI18N("enhancer.io_error_while_writing_stream"),
                ex);
        }
        return changed;
    }

    /**
     * Enhances a given class according to the JDO meta-data.
     */
    public boolean enhanceClassFile(InputStream         inClassFile,
                                    OutputStreamWrapper outClassFile)
        throws EnhancerUserException, EnhancerFatalError
    {
        env.verbose("---------------------------------------------------------------------------");
        env.messageNL("Enhancer: enhancing classfile ...");

        // reset environment to clear class map etc.
        env.reset();

        // enhance class file; check Exceptions
        final boolean changed;
        try {
            changed = enhanceClassFile1(inClassFile, outClassFile);
        } catch (EnhancerUserException ex) {
            // reset environment to clear class map etc.
            env.reset();
            throw ex;
        } catch (EnhancerMetaDataUserException ex) {
            // note: catch EnhancerMetaDataUserException before RuntimeException

            // reset environment to clear class map etc.
            env.reset();
            throw new EnhancerUserException(
                getI18N("enhancer.error", ex.getMessage()),
                ex);
        } catch (RuntimeException ex) {
            // reset environment to clear class map etc.
            env.reset();
            ex.printStackTrace ();
            throw new EnhancerFatalError(
                getI18N("enhancer.internal_error", ex.getMessage()),
                ex);
        }

        env.messageNL(changed
                      ? "Enhancer: classfile enhanced successfully."
                      : "Enhancer: classfile not changed.");
        return changed;
    }

    public boolean enhanceClassFile(InputStream in,
                                    OutputStream out)
        throws EnhancerUserException,
        EnhancerFatalError
    {
        return enhanceClassFile(in, new OutputStreamWrapper(out));
    }
}
