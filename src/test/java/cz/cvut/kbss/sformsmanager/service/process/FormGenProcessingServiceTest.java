package cz.cvut.kbss.sformsmanager.service.process;

import cz.cvut.kbss.sformsmanager.model.dto.FormGenRawJson;
import cz.cvut.kbss.sformsmanager.model.persisted.FormGenMetadata;
import cz.cvut.kbss.sformsmanager.model.persisted.FormGenVersion;
import cz.cvut.kbss.sformsmanager.persistence.dao.FormGenInstanceDAO;
import cz.cvut.kbss.sformsmanager.persistence.dao.FormGenMetadataDAO;
import cz.cvut.kbss.sformsmanager.persistence.dao.FormGenVersionDAO;
import cz.cvut.kbss.sformsmanager.utils.OWLUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FormGenProcessingServiceTest {

    private static final String CONNECTION_NAME = "study-manager";
    private static final String CONTEXT_URI = "some-context-uri";
    private static final int HASH_CODE = 12312312;
    private static final String VERSION = "v/sm/0";

    private static FormGenRawJson formGenRawJson;

    private FormGenProcessingService processingService;

    @Mock
    private FormGenInstanceDAO instanceDAO;
    @Mock
    private FormGenVersionDAO versionTagDAO;
    @Mock
    private FormGenMetadataDAO metadataDAO;

    @BeforeAll
    public static void setup() throws IOException {
        String exampleFormJson = new String(Files.readAllBytes(ResourceUtils.getFile("classpath:app_generated_form_filled.txt").toPath()));
        formGenRawJson = new FormGenRawJson(CONNECTION_NAME, CONTEXT_URI, exampleFormJson);
    }

    @BeforeEach
    public void setUp() {
        processingService = new FormGenProcessingServiceImpl(instanceDAO, versionTagDAO, metadataDAO);

        when(versionTagDAO.findByKey(anyString())).thenReturn(Optional.empty());
        when(metadataDAO.findByKey(anyString())).thenReturn(Optional.empty());
        when(versionTagDAO.count()).thenReturn(0);
    }

    @Test
    public void processNewFormGen() throws IOException {

        FormGenMetadata metadata = processingService.getFormGenMetadata(formGenRawJson);
        assertThat(metadata.getFormGenVersion().getVersion()).isEqualTo(VERSION);
        assertThat(metadata.getFormGenVersion().getKey()).isNotNull();

        String expectedKey = OWLUtils.createInitialsAndConcatWithSlash(CONNECTION_NAME, CONTEXT_URI);
        assertThat(metadata.getConnectionName()).isEqualTo(CONNECTION_NAME);
        assertThat(metadata.getContextUri()).isEqualTo(CONTEXT_URI);
        assertThat(metadata.getKey()).isEqualTo(expectedKey);
        assertThat(metadata.getUri()).isNull();
    }

    @Test
    public void processTheSameFormGenTwice() throws IOException {
        FormGenMetadata metadata1 = processingService.getFormGenMetadata(formGenRawJson);
        FormGenMetadata metadata2 = processingService.getFormGenMetadata(formGenRawJson);
        assertThat(metadata1).isEqualTo(metadata2);
    }

    @Test
    public void processFormGenWithExistingVersion() throws IOException {
        FormGenMetadata metadata1 = processingService.getFormGenMetadata(formGenRawJson);

        when(versionTagDAO.findByKey(anyString())).thenReturn(Optional.of(metadata1.getFormGenVersion()));
        FormGenMetadata metadata2 = processingService.getFormGenMetadata(formGenRawJson);
        assertThat(metadata1).isEqualTo(metadata2);
    }

    @Test
    public void processExistingFormGen() throws IOException {
        FormGenMetadata metadata1 = processingService.getFormGenMetadata(formGenRawJson);
        when(versionTagDAO.findByKey(anyString())).thenReturn(Optional.of(metadata1.getFormGenVersion()));
        when(metadataDAO.findByKey(anyString())).thenReturn(Optional.of(metadata1));

        FormGenMetadata metadata2 = processingService.getFormGenMetadata(formGenRawJson);
        assertThat(metadata1).isSameAs(metadata2);
    }

    @Test
    public void processAnotherFormGen() throws IOException {
        String versionTagKey = OWLUtils.createInitialsAndConcatWithSlash(formGenRawJson.getConnectionName(), HASH_CODE);
        FormGenVersion expectedVersion = new FormGenVersion(null, formGenRawJson.getConnectionName() + versionTagDAO.count(), versionTagKey);
        when(versionTagDAO.findByKey(anyString())).thenReturn(Optional.of(expectedVersion));

        FormGenMetadata metadata1 = processingService.getFormGenMetadata(formGenRawJson);
        FormGenMetadata metadata2 = processingService.getFormGenMetadata(formGenRawJson);
        assertThat(metadata1).isEqualTo(metadata2);
    }
}