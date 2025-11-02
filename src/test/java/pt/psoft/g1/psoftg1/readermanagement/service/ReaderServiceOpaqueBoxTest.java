package pt.psoft.g1.psoftg1.readermanagement.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import pt.psoft.g1.psoftg1.exceptions.ConflictException;
import pt.psoft.g1.psoftg1.genremanagement.model.Genre;
import pt.psoft.g1.psoftg1.genremanagement.repositories.GenreRepository;
import pt.psoft.g1.psoftg1.readermanagement.model.ReaderDetails;
import pt.psoft.g1.psoftg1.readermanagement.repositories.ReaderRepository;
import pt.psoft.g1.psoftg1.readermanagement.services.CreateReaderRequest;
import pt.psoft.g1.psoftg1.readermanagement.services.ReaderMapper;
import pt.psoft.g1.psoftg1.readermanagement.services.ReaderServiceImpl;
import pt.psoft.g1.psoftg1.shared.repositories.ForbiddenNameRepository;
import pt.psoft.g1.psoftg1.shared.repositories.PhotoRepository;
import pt.psoft.g1.psoftg1.usermanagement.repositories.UserRepository;

@ExtendWith(MockitoExtension.class)
public class ReaderServiceOpaqueBoxTest {
    @Mock private ReaderRepository readerRepo;
    @Mock private UserRepository userRepo;
    @Mock private ReaderMapper readerMapper;
    @Mock private GenreRepository genreRepo;
    @Mock private ForbiddenNameRepository forbiddenNameRepository;
    @Mock private PhotoRepository photoRepository;

    @InjectMocks private ReaderServiceImpl readerService;

    @Test
    void testCreateReaderSuccess() {
        CreateReaderRequest request = new CreateReaderRequest();
        request.setFullName("John Doe");
        request.setUsername("jdoe");
        request.setPhoneNumber("912345678");
        request.setBirthDate("2000-01-01");
        request.setInterestList(List.of("Fiction"));
        request.setPhoto(null);

        when(userRepo.findByUsername("jdoe")).thenReturn(Optional.empty());
        when(forbiddenNameRepository.findByForbiddenNameIsContained(anyString())).thenReturn(List.of());
        when(genreRepo.findByString("Fiction")).thenReturn(Optional.of(new Genre("1", "Fiction")));
        //when(readerMapper.createReader(request)).thenReturn(new Reader("jdoe","pass"));
        when(readerMapper.createReaderDetails(anyInt(), any(), any(), anyString(), anyList())).thenAnswer(i -> new ReaderDetails());

        ReaderDetails rd = readerService.create(request, null);

        assertNotNull(rd);
    }

    @Test
    void testCreateReaderUsernameConflict() {
        CreateReaderRequest request = new CreateReaderRequest();
        request.setUsername("jdoe");
        //when(userRepo.findByUsername("jdoe")).thenReturn(Optional.of(new Reader()));

        assertThrows(ConflictException.class, () -> readerService.create(request, null));
    }
}
