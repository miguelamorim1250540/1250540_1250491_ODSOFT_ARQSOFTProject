package pt.psoft.g1.psoftg1.lendingmanagement.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import pt.psoft.g1.psoftg1.lendingmanagement.api.LendingController;
import pt.psoft.g1.psoftg1.lendingmanagement.api.LendingView;
import pt.psoft.g1.psoftg1.lendingmanagement.api.LendingViewMapper;
import pt.psoft.g1.psoftg1.lendingmanagement.model.Lending;
import pt.psoft.g1.psoftg1.lendingmanagement.services.CreateLendingRequest;
import pt.psoft.g1.psoftg1.lendingmanagement.services.LendingService;
import pt.psoft.g1.psoftg1.readermanagement.services.ReaderService;
import pt.psoft.g1.psoftg1.shared.services.ConcurrencyService;
import pt.psoft.g1.psoftg1.usermanagement.services.UserService;

//SUT - LendingController + LendingService

@ExtendWith(MockitoExtension.class)
public class LendingControllerOpaqueBoxTest {
    @InjectMocks
    private LendingController lendingController;

    @Mock
    private LendingService lendingService;
    @Mock
    private ReaderService readerService;
    @Mock
    private UserService userService;
    @Mock
    private ConcurrencyService concurrencyService;
    @Mock
    private LendingViewMapper lendingViewMapper;

    private Lending lending;

    @BeforeEach
    void setUp() {
        //lending = new Lending();
        //lending.setLendingNumber("2025/1");
        //lending.setVersion(1L);
    }

    @Test
    void createLending_success() {
        CreateLendingRequest request = new CreateLendingRequest();
        when(lendingService.create(request)).thenReturn(lending);
        when(lendingViewMapper.toLendingView(lending)).thenReturn(new LendingView());

        ResponseEntity<LendingView> response = lendingController.create(request);
        assertEquals(201, response.getStatusCodeValue());
        assertNotNull(response.getBody());
    }
}
