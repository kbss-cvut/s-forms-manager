package cz.cvut.kbss.sformsmanager.service.data;

import cz.cvut.kbss.sformsmanager.rest.NotFoundException;
import cz.cvut.kbss.sformsmanager.utils.URLUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class RemoteDataLoader implements DataLoader {

    private final RestTemplate restTemplate;

    @Override
    public String loadData(String remoteUrl, Map<String, String> params) throws URISyntaxException {
        final HttpHeaders headers = processHeaders(params);
        final URI urlWithQuery = URLUtils.addParametersToUri(remoteUrl, params);
        final HttpEntity<Object> entity = new HttpEntity<>(null, headers);

        log.trace("Getting remote data using {}", urlWithQuery.toString());
        try {
            final ResponseEntity<String> result = restTemplate.exchange(urlWithQuery, HttpMethod.GET, entity, String.class);
            return result.getBody();
        } catch (Exception e) {
            log.error("Error when requesting remote data, url: {}.", urlWithQuery.toString(), e);
            throw new NotFoundException("Unable to fetch remote data.", e);
        }
    }

    private HttpHeaders processHeaders(Map<String, String> params) {
        final HttpHeaders headers = new HttpHeaders();
        // Set default accept type to JSON-LD
        headers.set(HttpHeaders.ACCEPT, "application/ld+json");
        String[] supportedHeaders = {HttpHeaders.ACCEPT, HttpHeaders.CONTENT_TYPE}; // TODO: make configurable
        for (String header : supportedHeaders) {
            if (params.containsKey(header)) {
                headers.set(header, params.get(header));
                params.remove(header);
            }
        }
        return headers;
    }
}
