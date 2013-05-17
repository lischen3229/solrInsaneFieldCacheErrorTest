package com.test;

import com.test.helper.MockCourseDocument;
import com.test.helper.QueryBuilder;
import com.test.model.CourseDocument;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.embedded.EmbeddedSolrServer;
import org.apache.solr.util.AbstractSolrTestCase;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;


/**
 * Main Test Case for replicating the insane FieldCache exception
 */
public class InsaneFieldCacheTest extends AbstractSolrTestCase
{
    private static SolrServer solrServer;
    private static QueryBuilder queryBuilder;

    @BeforeClass
    public static void init() throws Exception
    {
        initCore("solrconfig.xml", "schema.xml", "solr", "insane");
        solrServer = new EmbeddedSolrServer(h.getCoreContainer(), h.getCore().getName());
        queryBuilder = new QueryBuilder(solrServer);

        /* create initial data set */
        MockCourseDocument doc = new MockCourseDocument(0, 11);
        solrServer.addBean(doc.title("Geld vermehren 1x1").appTitle("Termin 1").studyTech("Online").desc("short description").build());
        doc = new MockCourseDocument(1, 22);
        solrServer.addBean(doc.title("Koma-Saufen - cool?").appTitle("Termin 1").studyTech("Master").desc("short description").build());
        doc = new MockCourseDocument(2, 33);
        solrServer.addBean(doc.title("Salsa 1").appTitle("Termin Köln, Rheinpark").studyTech("Master").desc("short description").build());
        doc = new MockCourseDocument(3, 44);
        solrServer.addBean(doc.title("Salsa für Fortgeschrittene").appTitle("xxx").desc("Salsa Beschreibung Salsa").studyTech("Präsenzveranstaltung").build());
        doc = new MockCourseDocument(4, 55);
        solrServer.addBean(doc.title("Praxiswissen Baukostenermittlung").appTitle("xxx").desc("Beschreibung").studyTech("Online").build());
       doc = new MockCourseDocument(5, 66);
        solrServer.addBean(doc.title("Hacker round table").appTitle("Java Me in München").desc("Beschreibung").studyTech("Präsenzveranstaltung").build());
        doc = new MockCourseDocument(6, 77);
        solrServer.addBean(doc.title("Java für Entwickler").appTitle("Java Me in München").desc("Beschreibung").studyTech("Online").build());
        doc = new MockCourseDocument(7, 88);
        solrServer.addBean(doc.appTitle("Praxiswissen Pflanzenpflege").appTitle("xxx").desc("Beschreibung").studyTech("Online").build());
        doc = new MockCourseDocument(8, 99);
        solrServer.addBean(doc.title("Java für BWLer").studyTech("Online").build());
        doc = new MockCourseDocument(9, 111);
        solrServer.addBean(doc.title("Recht für IT - Praxiswissen").studyTech("Online").build());
        doc = new MockCourseDocument(10, 222);
        solrServer.addBean(doc.title("Wie setze ich neue Medien ein").studyTech("Universität").build());
        doc = new MockCourseDocument(11, 33);
        solrServer.addBean(doc.title("Tanzen für Anfänger").studyTech("Universität").build());

        solrServer.commit();
    }

    @AfterClass
    public static void shutdown()
    {
        solrServer.shutdown();
    }

    /**
     * Testing empty search.
     */
    @Test
    public void testEmptySearch()
    {
        //no query - NO documents in the solr index should be returned
        List<CourseDocument> courses = queryBuilder.search(null);
        assertNull(courses);

        //empty query - NO documents in the solr index should be returned
        courses = queryBuilder.search("");
        assertNull(courses);
    }


    /**
     * Testing parameter "query"
     */
    @Test
    public void testSearchByQuery()
    {
        //test match in title
        List<CourseDocument> courses = queryBuilder.search("Praxiswissen");
        assertEquals(2, courses.size());

        //test match in description
        courses = queryBuilder.search("Beschreibung");
        assertEquals(5, courses.size());

        //test match in course_studying_technique
        courses = queryBuilder.search("Online");
        assertEquals(5, courses.size());

        //test no match
        courses = queryBuilder.search("Berlin");
        assertNull(courses);
    }


}
