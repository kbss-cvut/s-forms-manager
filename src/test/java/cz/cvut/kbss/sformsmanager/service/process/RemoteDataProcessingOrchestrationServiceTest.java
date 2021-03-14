package cz.cvut.kbss.sformsmanager.service.process;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class RemoteDataProcessingOrchestrationServiceTest {
//
//    private static final String CONNECTION_NAME = "study-manager";
//    private static final String CONTEXT_URI = "some-context-uri";
//    private static final int HASH_CODE = 12312312;
//    private static final String VERSION = "v/sm/0";
//
//    private static FormGenRawJson formGenRawJson;
//
//    private RemoteDataProcessingOrchestrationService processingService;
//
//    @Mock
//    private FormGenVersionDAO versionTagDAO;
//    @Mock
//    private FormGenMetadataDAO metadataDAO;
//    @Mock
//    private RecordSnapshotRemoteDAO recordRemoteDAO;
//    @Mock
//    private QuestionTemplateSnapshotRemoteDAO questionRemoteDAO;
//    @Mock
//    private QuestionTemplateSnapshotDAO questionTemplateSnapshotDAO;
//    @Mock
//    private RecordSnapshotDAO recordSnapshotDAO;
//
//    @BeforeAll
//    public static void setup() throws IOException {
//        String exampleFormJson = new String(Files.readAllBytes(ResourceUtils.getFile("classpath:app_generated_form_filled.txt").toPath()));
//        formGenRawJson = new FormGenRawJson(CONNECTION_NAME, CONTEXT_URI, exampleFormJson);
//    }
//
//    @BeforeEach
//    public void setUp() {
//        processingService = new RemoteDataProcessingOrchestrationServiceImpl(questionTemplateSnapshotDAO, recordRemoteDAO, recordSnapshotDAO, questionRemoteDAO);
//
//        when(versionTagDAO.findByKey(anyString())).thenReturn(Optional.empty());
//        when(metadataDAO.findByKey(anyString())).thenReturn(Optional.empty());
//        when(versionTagDAO.count()).thenReturn(0);
//    }
//
//    @Test
//    public void processNewFormGen() throws IOException {
//
//        FormGenMetadata metadata = processingService.processDataSnapshotInRemoteContext(CONNECTION_NAME, CONTEXT_URI);
//        assertThat(metadata.getFormGenVersion().getVersionName()).isEqualTo(VERSION);
//        assertThat(metadata.getFormGenVersion().getKey()).isNotNull();
//
//        String expectedKey = OWLUtils.createInitialsAndConcatWithSlash(CONNECTION_NAME, CONTEXT_URI);
//        assertThat(metadata.getConnectionName()).isEqualTo(CONNECTION_NAME);
//        assertThat(metadata.getContextUri()).isEqualTo(CONTEXT_URI);
//        assertThat(metadata.getKey()).isEqualTo(expectedKey);
//        assertThat(metadata.getUri()).isNull();
//    }
//
//    @Test
//    public void processTheSameFormGenTwice() throws IOException {
//        FormGenMetadata metadata1 = processingService.processDataSnapshotInRemoteContext(CONNECTION_NAME, CONTEXT_URI);
//        FormGenMetadata metadata2 = processingService.processDataSnapshotInRemoteContext(CONNECTION_NAME, CONTEXT_URI);
//        assertThat(metadata1).isEqualTo(metadata2);
//    }
//
//    @Test
//    public void processFormGenWithExistingVersion() throws IOException {
//        FormGenMetadata metadata1 = processingService.processDataSnapshotInRemoteContext(CONNECTION_NAME, CONTEXT_URI);
//
//        when(versionTagDAO.findByKey(anyString())).thenReturn(Optional.of(metadata1.getFormGenVersion()));
//        FormGenMetadata metadata2 = processingService.processDataSnapshotInRemoteContext(CONNECTION_NAME, CONTEXT_URI);
//        assertThat(metadata1).isEqualTo(metadata2);
//    }
//
//    @Test
//    public void processExistingFormGen() throws IOException {
//        FormGenMetadata metadata1 = processingService.processDataSnapshotInRemoteContext(CONNECTION_NAME, CONTEXT_URI);
//        when(versionTagDAO.findByKey(anyString())).thenReturn(Optional.of(metadata1.getFormGenVersion()));
//        when(metadataDAO.findByKey(anyString())).thenReturn(Optional.of(metadata1));
//
//        FormGenMetadata metadata2 = processingService.processDataSnapshotInRemoteContext(CONNECTION_NAME, CONTEXT_URI);
//        assertThat(metadata1).isSameAs(metadata2);
//    }
//
//    @Test
//    public void processAnotherFormGen() throws IOException {
//        String versionTagKey = OWLUtils.createInitialsAndConcatWithSlash(formGenRawJson.getConnectionName(), HASH_CODE);
//        FormGenVersion expectedVersion = new FormGenVersion("study-manager", null, formGenRawJson.getConnectionName() + versionTagDAO.count(), versionTagKey, CONTEXT_URI);
//        when(versionTagDAO.findByKey(anyString())).thenReturn(Optional.of(expectedVersion));
//
//        FormGenMetadata metadata1 = processingService.processDataSnapshotInRemoteContext(CONNECTION_NAME, CONTEXT_URI);
//        FormGenMetadata metadata2 = processingService.processDataSnapshotInRemoteContext(CONNECTION_NAME, CONTEXT_URI);
//        assertThat(metadata1).isEqualTo(metadata2);
//    }
}