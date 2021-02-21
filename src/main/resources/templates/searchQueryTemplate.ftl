SELECT ?x
WHERE {

<#if latestSavesOnly??>
    # latest saves only
    {
        SELECT ?formGenSaveHash (max(?modified) as ?lastModified)
        WHERE {
        ?m ?type <http://onto.fel.cvut.cz/ontologies/sformsmanager#FormGenMetadata> ;
                 <http://onto.fel.cvut.cz/ontologies/sformsmanager#connectionName> ?connectionName;
                 <http://onto.fel.cvut.cz/ontologies/sformsmanager#formGenSaveHashCode> ?formGenSaveHash;
                 <http://onto.fel.cvut.cz/ontologies/sformsmanager#formGenModified> ?modified .
        }
        GROUP BY ?formGenSaveHash
    }

    ?x <http://onto.fel.cvut.cz/ontologies/sformsmanager#formGenModified> ?lastModified .

</#if>

    ?x a <${classURI}> .

<#if versions??>
    # versions or synonyms
    ?x <${versionAssignedURI}> ?vObject .
    ?vObject <${versionClassURI}> ?v .
    OPTIONAL { ?vObject <${synonymURI}> ?sy . }
    FILTER (?v IN ( ${versions?map(version -> "\"" + version+ "\"")?join(", ")} ) || ?sy IN ( ${versions?map(version -> "\"" + version+ "\"")?join(", ")} ))

</#if>

<#if saveHashes??>
    # saves
    ?x <${saveHashURI}> ?s .
    { FILTER (?s IN ( ${saveHashes?map(saveHash -> "\"" + saveHash + "\"")?join(", ")} ) ) }
</#if>

}