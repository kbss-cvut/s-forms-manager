package cz.cvut.kbss.sformsmanager.service.data;

import java.net.URISyntaxException;
import java.util.Map;

public interface DataLoader {

    String loadData(String remoteUrl, Map<String, String> params, Map<String, String> headers) throws URISyntaxException;
}
