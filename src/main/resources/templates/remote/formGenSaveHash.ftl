SELECT ?questionHash ?created
WHERE
{
    graph <${contextUri}>
    {
        ?s a <http://vfn.cz/ontologies/study-manager/patient-record> .
        OPTIONAL { ?s <http://vfn.cz/ontologies/study-manager/has-question> ?a . }
        ?s <http://purl.org/dc/terms/created> ?created .
        BIND (str(?a) as ?strA)
        BIND (md5(?strA) as ?questionHash)
    }
}