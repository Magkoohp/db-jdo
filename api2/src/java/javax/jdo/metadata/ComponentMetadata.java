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
package javax.jdo.metadata;

import javax.jdo.annotations.IdentityType;

/**
 * Represents a class or interface. Extended for the specifics of those cases.
 * @since 2.3
 */
public interface ComponentMetadata extends Metadata {
    /**
     * Accessor for the name of this component (set on construction).
     * 
     * @return The name
     */
    String getName();

    /**
     * Method to define the identity type to use.
     * 
     * @param id identity type
     */
    ComponentMetadata setIdentityType(IdentityType id);

    /**
     * Accessor for the identity type to use.
     * 
     * @return identity type
     */
    IdentityType getIdentityType();

    /**
     * Method to set the object-id (PK) class.
     * 
     * @param idclass Object-id class
     */
    ComponentMetadata setObjectIdClass(String idclass);

    /**
     * Accessor for the object-id class (if defined).
     * 
     * @return The object-id class
     */
    String getObjectIdClass();

    /**
     * Method to set whether the component requires an extent.
     * 
     * @param extent Requires extent?
     */
    ComponentMetadata setRequiresExtent(boolean extent);

    /**
     * Accessor for whether the component requires an extent.
     * 
     * @return Requires extent?
     */
    boolean getRequiresExtent();

    /**
     * Method to set whether this is detachable
     * 
     * @param detachable Detachable?
     */
    ComponentMetadata setDetachable(boolean detachable);

    /**
     * Accessor for whether this is detachable.
     * 
     * @return Detachable?
     */
    boolean getDetachable();

    /**
     * Method to set whether this is cacheable
     * 
     * @param cacheable Cacheable?
     */
    ComponentMetadata setCacheable(boolean cacheable);

    /**
     * Accessor for whether this is cacheable.
     * 
     * @return Detachable?
     */
    boolean getCacheable();

    /**
     * Method to set whether it is stored only as embedded in other objects.
     * 
     * @param embedded Whether it is only stored embedded
     */
    ComponentMetadata setEmbeddedOnly(boolean extent);

    /**
     * Accessor for whether this is embedded only.
     * 
     * @return Only stored as embedded
     */
    Boolean getEmbeddedOnly();

    /**
     * Method to set the catalog (ORM) for this component
     * 
     * @param catalog Catalog name
     */
    ComponentMetadata setCatalog(String catalog);

    /**
     * Accessor for the catalog (ORM) for this component
     * 
     * @return The catalog
     */
    String getCatalog();

    /**
     * Method to set the schema (ORM) for this component
     * 
     * @param schema Schema name
     */
    ComponentMetadata setSchema(String schema);

    /**
     * Accessor for the schema (ORM) for this component
     * 
     * @return The schema
     */
    String getSchema();

    /**
     * Method to set the table name.
     * 
     * @param table Table name
     */
    ComponentMetadata setTable(String table);

    /**
     * Accessor for the name of the table.
     * 
     * @return The name
     */
    String getTable();

    /**
     * Method to define the inheritance metadata.
     * 
     * @return The InheritanceMetadata
     */
    InheritanceMetadata newInheritanceMetadata();

    /**
     * Accessor for the inheritance (if any).
     * 
     * @return inheritance
     */
    InheritanceMetadata getInheritanceMetadata();

    /**
     * Method to define the version metadata.
     * 
     * @return The VersionMetadata
     */
    VersionMetadata newVersionMetadata();

    /**
     * Accessor for the version (if any).
     * 
     * @return version
     */
    VersionMetadata getVersionMetadata();

    /**
     * Method to define the datastore identity metadata details.
     * 
     * @return The DatastoreIdentityMetadata
     */
    DatastoreIdentityMetadata newDatastoreIdentityMetadata();

    /**
     * Accessor for the datastore identity details.
     * 
     * @return datastore identity details
     */
    DatastoreIdentityMetadata getDatastoreIdentityMetadata();

    /**
     * Method to define the primary key details.
     * 
     * @return The PrimaryKeyMetadata
     */
    PrimaryKeyMetadata newPrimaryKeyMetadata();

    /**
     * Accessor for the primary key (if any).
     * 
     * @return primary key details
     */
    PrimaryKeyMetadata getPrimaryKeyMetadata();

    /**
     * Accessor for all joins(s) defined on the component.
     * 
     * @return The join(s)
     */
    JoinMetadata[] getJoins();

    /**
     * Add a join for this component.
     * 
     * @return The JoinMetadata
     */
    JoinMetadata newJoinMetadata();

    /**
     * Accessor for the number of join(s) defined for this component.
     * 
     * @return The number of join(s)
     */
    int getNumberOfJoins();

    /**
     * Accessor for all fk(s) defined on the component.
     * 
     * @return The fk(s)
     */
    ForeignKeyMetadata[] getForeignKeys();

    /**
     * Add a new FK for this component.
     * 
     * @return The ForeignKeyMetadata
     */
    ForeignKeyMetadata newForeignKeyMetadata();

    /**
     * Accessor for the number of FKs defined for this component.
     * 
     * @return The number of FKs
     */
    int getNumberOfForeignKeys();

    /**
     * Accessor for all index(s) defined on the component.
     * 
     * @return The index(s)
     */
    IndexMetadata[] getIndices();

    /**
     * Add a new index for this component.
     * 
     * @return The IndexMetadata
     */
    IndexMetadata newIndexMetadata();

    /**
     * Accessor for the number of indices defined for this component.
     * 
     * @return The number of indices
     */
    int getNumberOfIndices();

    /**
     * Accessor for all unique constraints defined on the component.
     * 
     * @return The unique constraints
     */
    UniqueMetadata[] getUniques();

    /**
     * Add a new unique constraint for this component.
     * 
     * @return The UniqueMetadata
     */
    UniqueMetadata newUniqueMetadata();

    /**
     * Accessor for the number of unique constraints defined for this component.
     * 
     * @return The number of unique constraints
     */
    int getNumberOfUniques();

    /**
     * Accessor for all fields/properties defined on the component.
     * 
     * @return The members
     */
    MemberMetadata[] getMembers();

    /**
     * Accessor for the number of fields/properties defined for this component.
     * 
     * @return The number of members
     */
    int getNumberOfMembers();

    /**
     * Add a new property for this component.
     * 
     * @param name Name of the property
     * @return The PropertyMetadata
     */
    PropertyMetadata newPropertyMetadata(String name);

    /**
     * Accessor for all named queries defined on the component.
     * 
     * @return The queries
     */
    QueryMetadata[] getQueries();

    /**
     * Add a new query for this component.
     * 
     * @param name
     *            Name of the query to add
     * @return The QueryMetadata
     */
    QueryMetadata newQueryMetadata(String name);

    /**
     * Accessor for the number of named queries defined for this component.
     * 
     * @return The number of named queries
     */
    int getNumberOfQueries();

    /**
     * Accessor for all FetchGroup defined on the component.
     * 
     * @return The FetchGroups
     */
    FetchGroupMetadata[] getFetchGroups();

    /**
     * Add a new FetchGroup for this component.
     * 
     * @param name Name of the FetchGroup
     * @return The FetchGroupMetadata
     */
    FetchGroupMetadata newFetchGroupMetadata(String name);

    /**
     * Accessor for the number of fetchGroups defined for this component.
     * 
     * @return The number of fetch groups
     */
    int getNumberOfFetchGroups();
}