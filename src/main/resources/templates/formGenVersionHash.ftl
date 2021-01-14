SELECT ?hash
WHERE
{
    {
        SELECT  (group_concat (?oStr) as ?originConcatStr)
        {
            SELECT ?oStr
            WHERE
            {
                GRAPH <${contextUri}>
                {
                    ?s <http://onto.fel.cvut.cz/ontologies/form/has-question-origin> ?o .
                    BIND (str(?o) as ?oStr)
                }
            }
            ORDER BY ?oStr
        }
    }
    BIND(md5(?originConcatStr)  as ?hash)
}
