import cz.cvut.kbss.sformsmanager.model.dto.FormGenRawJson;
import cz.cvut.kbss.sformsmanager.model.persisted.FormGenMetadata;
import cz.cvut.kbss.sformsmanager.model.persisted.FormGenVersionTag;
import cz.cvut.kbss.sformsmanager.persistence.dao.FormGenMetadataDAO;
import cz.cvut.kbss.sformsmanager.persistence.dao.FormGenVersionTagDAO;
import cz.cvut.kbss.sformsmanager.service.process.FormGenProcessingService;
import cz.cvut.kbss.sformsmanager.service.process.FormGenProcessingServiceImpl;
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

    private static final String CONNECTION_NAME = "sm";
    private static final String CONTEXT_URI = "some-context-uri";
    private static final String KEY = "study-manager-key";
    private static final int HASH_CODE = 12312312;

    private static FormGenRawJson formGenRawJson;

    private FormGenProcessingService processingService;

    @Mock
    private FormGenVersionTagDAO versionTagDAO;
    @Mock
    private FormGenMetadataDAO metadataDAO;

    @BeforeAll
    public static void setup() throws IOException {
        String exampleFormJson = new String(Files.readAllBytes(ResourceUtils.getFile("classpath:app_generated_form_filled.txt").toPath()));
        formGenRawJson = new FormGenRawJson(CONNECTION_NAME, CONTEXT_URI, exampleFormJson, KEY);
    }

    @BeforeEach
    public void setUp() {
        processingService = new FormGenProcessingServiceImpl(versionTagDAO, metadataDAO);

        when(versionTagDAO.findByKey(anyString())).thenReturn(Optional.empty());
        when(metadataDAO.findByKey(anyString())).thenReturn(Optional.empty());
        when(versionTagDAO.count()).thenReturn(0);
    }

    @Test
    public void processNewFormGen() throws IOException {

        FormGenMetadata metadata = processingService.getFormGenMetadata(formGenRawJson);
        assertThat(metadata.getVersionTag().getVersion()).isEqualTo(CONNECTION_NAME + 0);
        assertThat(metadata.getVersionTag().getKey()).isNotNull();

        String expectedKey = OWLUtils.createFormGenkey(CONNECTION_NAME, CONTEXT_URI);
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

        when(versionTagDAO.findByKey(anyString())).thenReturn(Optional.of(metadata1.getVersionTag()));
        FormGenMetadata metadata2 = processingService.getFormGenMetadata(formGenRawJson);
        assertThat(metadata1).isEqualTo(metadata2);
    }

    @Test
    public void processExistingFormGen() throws IOException {
        FormGenMetadata metadata1 = processingService.getFormGenMetadata(formGenRawJson);
        when(versionTagDAO.findByKey(anyString())).thenReturn(Optional.of(metadata1.getVersionTag()));
        when(metadataDAO.findByKey(anyString())).thenReturn(Optional.of(metadata1));

        FormGenMetadata metadata2 = processingService.getFormGenMetadata(formGenRawJson);
        assertThat(metadata1).isSameAs(metadata2);
    }

    @Test
    public void processAnotherFormGen() throws IOException {
        String versionTagKey = OWLUtils.createFormGenVersionTagKey(formGenRawJson.getConnectionName(), HASH_CODE);
        FormGenVersionTag versionTag = new FormGenVersionTag(formGenRawJson.getConnectionName() + versionTagDAO.count(), versionTagKey);
        when(versionTagDAO.findByKey(anyString())).thenReturn(Optional.of(versionTag));

        FormGenMetadata metadata1 = processingService.getFormGenMetadata(formGenRawJson);
        FormGenMetadata metadata2 = processingService.getFormGenMetadata(formGenRawJson);
        assertThat(metadata1).isEqualTo(metadata2);
    }
}