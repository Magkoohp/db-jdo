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

package org.apache.jdo.impl.enhancer;

import java.io.PrintWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.BufferedInputStream;
import java.io.File;

import org.apache.jdo.impl.enhancer.util.PathResourceLocator;



/**
 * Base class for JDO command line enhancer and tests.
 *
 * @author Martin Zaun
 */
public class ClassArgMain
    extends GenericMain
{
    /**
     *  The options and arguments.
     */
    protected ClassArgOptions options;

    /**
     *  The locator for classes.
     */
    protected PathResourceLocator classes;

    /**
     * Creates an instance.
     */
    public ClassArgMain(PrintWriter out,
                        PrintWriter err)
    {
        this(out, err, new ClassArgOptions(out, err));
    }

    /**
     * Creates an instance.
     */
    public ClassArgMain(PrintWriter out,
                        PrintWriter err,
                        ClassArgOptions options)
    {
        super(out, err, options);
        this.options = options;
    }

    // ----------------------------------------------------------------------

    /**
     * Initializes the class locator.
     */
    protected void initClassLocator()
        throws IOException
    {
        // create resource locator for specified source path
        final String path = options.sourcePath.value;
        if (path != null) {
            affirm(path.length() > 0);
            final boolean verbose = options.verbose.value;
            classes = new PathResourceLocator(out, verbose, path);
        }
    }

    /**
     * Initializes all components.
     */
    protected void init()
        throws EnhancerFatalError, EnhancerUserException
    {
        try {
            initClassLocator();
        } catch (Exception ex) {
            throw new EnhancerFatalError(ex);
        }
    }
    
    // ----------------------------------------------------------------------

    /**
     *  Returns the file name for a class name.
     *  This is done by replacing <code>'.'</code> by <code>'/'</code>.
     *
     *  @param  className  the classname
     *  @return  the filename
     */
    static protected String getClassFileName(String className)
    {
        return className.replace('.', '/') + ".class";
    }

    /**
     *  Opens an input stream for the given filename
     *
     *  @param  fileName  the name of the file
     *  @return  the input stream
     *  @exception  FileNotFoundException  if the file could not be found
     */
    protected InputStream openFileInputStream(String fileName)
        throws FileNotFoundException
    {
        affirm(fileName != null);
        //^olsen: support for timing
        //if (options.doTiming.value) {...}
     	return new BufferedInputStream(new FileInputStream(fileName));
    }

    /**
     * Opens an input stream for the given classname. The input stream is
     * created via an URL that is obtained by the value of the sourcepath
     * option and zip/jar file arguments.
     * 
     * @param  className  the name of the class (dot-notation)
     * @return  the input stream
     * @exception IOException if an I/O error occured
     */
    protected InputStream openClassInputStream(String className)
        throws IOException
    {
        affirm(className != null);
        final String resName = className.replace('.', '/') + ".class";
        //^olsen: support for timing
        //if (options.doTiming.value) {...}
        final InputStream s = classes.getInputStreamForResource(resName);
        affirm(s != null);
        return new BufferedInputStream(s);
    }

    /**
     *  Closes an input stream.
     *
     *  @param  in  the input stream
     */
    protected void closeInputStream(InputStream in)
    {
        if (in != null) {
            try {
                in.close();
            } catch (IOException ex) {
                printlnErr("", ex);
            }
        }
    }

    // ----------------------------------------------------------------------

    /**
     * Runs this class
     */
    static public void main(String[] args)
    {
        final PrintWriter out = new PrintWriter(System.out, true);
        out.println("--> ClassArgMain.main()");
        final ClassArgMain main = new ClassArgMain(out, out);
        int res = main.run(args);
        out.println("<-- ClassArgMain.main(): exit = " + res);
        System.exit(res);
    }
}
