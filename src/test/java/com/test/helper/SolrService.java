package com.test.helper;

import com.test.model.CourseDocument;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.beans.DocumentObjectBinder;
import org.apache.solr.client.solrj.response.Group;
import org.apache.solr.client.solrj.response.GroupCommand;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.CommonParams;
import org.apache.solr.common.params.GroupParams;

import java.util.ArrayList;
import java.util.List;

/**
 * Building the Solr query and searching in Solr
 */
public class SolrService {

    private SolrServer solrServer;

    public SolrService(SolrServer solrServer) {
        this.solrServer = solrServer;
    }

    /**
     * Searches on the server.
     */
    public List<CourseDocument> search(String query) {
        List<CourseDocument> documents = new ArrayList<>();
        SolrQuery solrQuery = buildSearchQuery(query, 1, 5);
        try {
            QueryResponse response = solrServer.query(solrQuery);

            // courses
            List<GroupCommand> groupCommands = response.getGroupResponse().getValues();
            GroupCommand currGroupCommand = groupCommands.get(0); //only one group since we're always grouping by course_id
            int matches = currGroupCommand.getNGroups();
            List<Group> groups = currGroupCommand.getValues();
            if (matches == 0)
            {
                return null;
            }

            for (Group group : groups) {
                SolrDocumentList list = group.getResult();
                DocumentObjectBinder binder = new DocumentObjectBinder();
                documents.addAll(binder.getBeans(CourseDocument.class, list));
            }
            return documents;
        }
        catch (SolrServerException e)
        {
            System.out.println("Could not get solr response for query: " + solrQuery.toString());
        }
        return null;
    }


    /**
     * Actually builds the query that is sent to Solr.
     */
    private SolrQuery buildSearchQuery(String query, int offset, int number) {
        SolrQuery solrQuery = new SolrQuery();

        //set grouping
        solrQuery.setQuery(query)
                .setStart(offset-1)
                .setRows(number)
                .set(GroupParams.GROUP, true)
                .set(GroupParams.GROUP_FIELD, "course_id")
                .set(GroupParams.GROUP_LIMIT, "4") //only four appointments to be returned for result lists

                // ------------------- CHANGE QUERY HERE TO REPRODUCE THE ERROR ------------------------
                //TODO: for reproducing the error, uncomment the next line with the GROUP_FACET

                //.set(GroupParams.GROUP_FACET, "true") //use group counts instead of document counts in facets
                .set(GroupParams.GROUP_TOTAL_COUNT, "true") //make sure the number of groups is returned for the total matches
                .set(CommonParams.FL, "*,score")
                .set("defType", "edismax")
                .set("qf", "course_title^10", "course_studying_technique^8", "course_description^6");


        //always do faceting
        solrQuery.setFacetMinCount(1)  // only options with hits
                .addFacetField("stf"); // studying technique facet

        return solrQuery;
    }

}
