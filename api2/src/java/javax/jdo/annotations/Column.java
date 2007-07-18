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
package javax.jdo.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for a column in the datastore.
 * Maps across to the JDO2 element "column".
 * 
 * @version 2.1
 * @since 2.1
 */
@Target({ElementType.FIELD, ElementType.METHOD}) 
@Retention(RetentionPolicy.RUNTIME)
public @interface Column
{
    /**
     * Name of the column.
     * @return the name of the column
     */
    String name() default "";

    /**
     * Target column for this column in the other table when part of a 
     * foreign key relation.
     * @return the target column in the other table for this column 
     * when part of a foreign key relation.
     */
    String target() default "";

    /**
     * Target field in the other class or interface for this column 
     * when part of a bidirectional relation.
     * @return the target field for this column when part of 
     * a bidirectional relation.
     */
    String targetField() default "";

    /**
     * JDBC Type for this column.
     * @return JDBC type for this column
     */
    String jdbcType() default "";

    /**
     * SQL Type for this column.
     * @return SQL type for this column
     */
    String sqlType() default "";

    /**
     * Maximum length of data stored in this column.
     * @return the maximum length of data stored in this column
     */
    int length() default -1;

    /**
     * Scale for the column when handling floating point values.
     * @return the scale for the column when handling floating point values
     */
    int scale() default -1;

    /**
     * Whether the column allows null values to be inserted.
     * @return whether the column allows null values to be inserted
     */
    String allowsNull() default "";

    /**
     * Default value for this column.
     * @return the default value for this column
     */
    String defaultValue() default "";

    /**
     * Value to be inserted when this is an "unmapped" column
     * @return the value to be inserted when this is an "unmapped" column
     */
    String insertValue() default "";
}