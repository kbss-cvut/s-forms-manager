<#list prefixes as prefix>
PREFIX ${prefix.name}: <${prefix.uri}>
</#list>

SELECT ?x
WHERE {
    ?x a ${classURI} .

<#if latestSavesOnly??>
    # latest saves only
    {
        SELECT ?formGenSaveHash (max(?modified) as ?lastModified)
        WHERE {
        ?m ?type ${formGenMetadataURI} ;
           ${connectionNameURI} ?connectionName ;
           ${formGenSaveHashURI} ?formGenSaveHash ;
           ${formGenModifiedURI} ?modified .
        }
        GROUP BY ?formGenSaveHash
    }

    ?x ${formGenModifiedURI} ?lastModified .

</#if>
<#if versions??>
    # versions or synonyms
    ?x ${versionAssignedURI} ?vObject .
    ?vObject ${versionClassURI} ?v .
    OPTIONAL { ?vObject ${synonymURI} ?sy . }
    FILTER (?v IN ( ${versions?map(version -> "\"" + version+ "\"")?join(", ")} ) || ?sy IN ( ${versions?map(version -> "\"" + version+ "\"")?join(", ")} ))

</#if>
<#if saveHashes??>
    # saves
    ?x ${saveHashURI} ?s .
    { FILTER (?s IN ( ${saveHashes?map(saveHash -> "\"" + saveHash + "\"")?join(", ")} ) ) }
</#if>

}