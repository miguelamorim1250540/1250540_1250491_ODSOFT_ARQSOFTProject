package pt.psoft.g1.psoftg1.readermanagement.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import pt.psoft.g1.psoftg1.external.service.ApiNinjasService;
import pt.psoft.g1.psoftg1.lendingmanagement.api.LendingViewMapper;
import pt.psoft.g1.psoftg1.lendingmanagement.services.LendingService;
import pt.psoft.g1.psoftg1.readermanagement.api.ReaderView;
import pt.psoft.g1.psoftg1.readermanagement.api.ReaderViewMapper;
import pt.psoft.g1.psoftg1.readermanagement.model.ReaderDetails;
import pt.psoft.g1.psoftg1.readermanagement.services.CreateReaderRequest;
import pt.psoft.g1.psoftg1.readermanagement.services.ReaderService;
import pt.psoft.g1.psoftg1.shared.services.ConcurrencyService;
import pt.psoft.g1.psoftg1.shared.services.FileStorageService;
import pt.psoft.g1.psoftg1.usermanagement.services.UserService;

@ExtendWith(MockitoExtension.class)
public class ReaderControllerOpaqueBoxTest {
    //@InjectMocks
    //private ReaderController readerController;

    @Mock
    private ReaderService readerService;
    @Mock
    private UserService userService;
    @Mock
    private ReaderViewMapper readerViewMapper;
    @Mock
    private LendingService lendingService;
    @Mock
    private LendingViewMapper lendingViewMapper;
    @Mock
    private ConcurrencyService concurrencyService;
    @Mock
    private FileStorageService fileStorageService;
    @Mock
    private ApiNinjasService apiNinjasService;

    private ReaderDetails readerDetails;
    private MultipartFile mockFile;

    @BeforeEach
    void setUp() {
        readerDetails = new ReaderDetails();
        //readerDetails.setReaderNumber("2025/1");
        //readerDetails.setVersion(1L);
        mockFile = mock(MultipartFile.class);
    }

    @Test
    void createReader_success() {
        CreateReaderRequest request = new CreateReaderRequest();
        request.setPhoto(mockFile);

        when(fileStorageService.getRequestPhoto(mockFile)).thenReturn("photo.jpg");
        when(readerService.create(any(), anyString())).thenReturn(readerDetails);
        when(readerViewMapper.toReaderView(readerDetails)).thenReturn(new ReaderView());

        //ResponseEntity<ReaderView> response = readerController.createReader(request);

        //assertEquals(201, response.getStatusCodeValue());
        //assertNotNull(response.getBody());
    }
}
