package cz.cvut.kbss.sformsmanager.model.dto;


import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

@Value
@Builder
@ToString(of = "key")
@EqualsAndHashCode(of = "key")
public class FormGenRawJson {

    private String connectionName;
    private String contextUri;
    private String rawJson;

    @Builder.ObtainVia(method = "createKey")
    private String key;

}
