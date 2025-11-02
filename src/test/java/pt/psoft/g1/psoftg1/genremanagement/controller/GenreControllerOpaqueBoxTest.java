package pt.psoft.g1.psoftg1.genremanagement.controller;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import pt.psoft.g1.psoftg1.bookmanagement.services.GenreBookCountDTO;
import pt.psoft.g1.psoftg1.genremanagement.api.GenreBookCountView;
import pt.psoft.g1.psoftg1.genremanagement.api.GenreController;
import pt.psoft.g1.psoftg1.genremanagement.api.GenreViewMapper;
import pt.psoft.g1.psoftg1.genremanagement.services.GenreService;

//SUT - GenreController + GenreService

@ExtendWith(MockitoExtension.class)
public class GenreControllerOpaqueBoxTest {
    @InjectMocks
    private GenreController genreController;

    @Mock
    private GenreService genreService;
    @Mock
    private GenreViewMapper genreViewMapper;

    @Test
    void getTop_success() {
        when(genreService.findTopGenreByBooks()).thenReturn((List<GenreBookCountDTO>) new GenreBookCountView());
        when(genreViewMapper.toGenreBookCountView(anyList())).thenReturn(Collections.singletonList(new GenreBookCountView()));

        var response = genreController.getTop();
        assertNotNull(response);
        assertFalse(response.getItems().isEmpty());
    }
}
