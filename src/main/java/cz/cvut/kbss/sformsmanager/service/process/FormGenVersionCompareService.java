package cz.cvut.kbss.sformsmanager.service.process;

import java.net.URISyntaxException;

public interface FormGenVersionCompareService {

    String getMergedVersionsJson(String connectionName, String versionName1, String versionName2) throws URISyntaxException;
}
