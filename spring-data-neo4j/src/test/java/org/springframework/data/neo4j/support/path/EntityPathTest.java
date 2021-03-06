/**
 * Copyright 2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.data.neo4j.support.path;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.neo4j.graphdb.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.Person;
import org.springframework.data.neo4j.core.EntityPath;
import org.springframework.data.neo4j.Person;
import org.springframework.data.neo4j.support.GraphDatabaseContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author mh
 * @since 26.02.11
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:org/springframework/data/neo4j/support/Neo4jGraphPersistenceTest-context.xml"})
public class EntityPathTest {

    @Autowired
    private GraphDatabaseContext ctx;

    @Test
    @Transactional
    public void shouldConvertNodePathToEntityPath() throws Exception {
        Person michael = new Person("Michael", 36).persist();
        Node node = michael.getPersistentState();
        NodePath path = new NodePath(node);
        EntityPath<Person, Person> entityPath = new ConvertingEntityPath<Person, Person>(ctx, path);

        Assert.assertEquals("start entity",michael, entityPath.startEntity());
        Assert.assertEquals("start node",node, path.startNode());
        Assert.assertEquals("end entity",michael, entityPath.endEntity());
        Assert.assertEquals("end node",node, path.endNode());
        Assert.assertNull("no relationship", entityPath.lastRelationshipEntity());
        Assert.assertNull("no relationship", path.lastRelationship());

        // todo all 6 iterators
    }
}
