solrInsaneFieldCacheErrorTest
=============================

Testcase for recreating the Exception: Insane FieldCache usage(s) - VALUEMISMATCH: Multiple distinct value objects for SegmentCoreReader


Exeption when executing the test case in src/test/java/com/test/InsaneFieldCacheTest.java:
*** BEGIN testSearchByGeoLocation(...): Insane FieldCache usage(s) ***
VALUEMISMATCH: Multiple distinct value objects for SegmentCoreReader(owner=_0(4.2.1):C12)+course_id
  'SegmentCoreReader(owner=_0(4.2.1):C12)'=>'course_id',int,org.apache.lucene.search.FieldCache.NUMERIC_UTILS_INT_PARSER=>org.apache.lucene.search.FieldCacheImpl$IntsFromArray#349256174 (size =~ 80 bytes)
	'SegmentCoreReader(owner=_0(4.2.1):C12)'=>'course_id',class org.apache.lucene.index.SortedDocValues,0.5=>org.apache.lucene.search.FieldCacheImpl$SortedDocValuesImpl#1338536878 (size =~ 320 bytes)
*** END testSearchByGeoLocation(...): Insane FieldCache usage(s) ***


When enabling the group.facet in src/test/java/com/test/helper/SolrService.java (line 80), the test case throws the error. When disabling the facet, the test case runs as expected.

