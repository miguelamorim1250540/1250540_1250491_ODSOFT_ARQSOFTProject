package pt.psoft.g1.psoftg1.genremanagement.api;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import pt.psoft.g1.psoftg1.bookmanagement.services.GenreBookCountDTO;
import pt.psoft.g1.psoftg1.genremanagement.model.Genre;
import pt.psoft.g1.psoftg1.genremanagement.services.GenreLendingsDTO;
import pt.psoft.g1.psoftg1.genremanagement.services.GenreLendingsPerMonthDTO;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-10-08T16:31:21+0100",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.44.0.v20251001-1143, environment: Java 21.0.8 (Eclipse Adoptium)"
)
@Component
public class GenreViewMapperImpl extends GenreViewMapper {

    @Override
    public GenreView toGenreView(Genre genre) {
        if ( genre == null ) {
            return null;
        }

        GenreView genreView = new GenreView();

        genreView.setGenre( map( genre.getGenre() ) );

        return genreView;
    }

    @Override
    public GenreView mapStringToGenreView(String genre) {
        if ( genre == null ) {
            return null;
        }

        GenreView genreView = new GenreView();

        genreView.setGenre( map( genre ) );

        return genreView;
    }

    @Override
    public GenreBookCountView toGenreBookCountView(GenreBookCountDTO genreBookCountView) {
        if ( genreBookCountView == null ) {
            return null;
        }

        GenreBookCountView genreBookCountView1 = new GenreBookCountView();

        genreBookCountView1.setGenreView( mapStringToGenreView( genreBookCountView.getGenre() ) );
        genreBookCountView1.setBookCount( genreBookCountView.getBookCount() );

        return genreBookCountView1;
    }

    @Override
    public List<GenreBookCountView> toGenreBookCountView(List<GenreBookCountDTO> genreBookCountView) {
        if ( genreBookCountView == null ) {
            return null;
        }

        List<GenreBookCountView> list = new ArrayList<GenreBookCountView>( genreBookCountView.size() );
        for ( GenreBookCountDTO genreBookCountDTO : genreBookCountView ) {
            list.add( toGenreBookCountView( genreBookCountDTO ) );
        }

        return list;
    }

    @Override
    public GenreLendingsView toGenreAvgLendingsView(GenreLendingsDTO dto) {
        if ( dto == null ) {
            return null;
        }

        GenreLendingsView genreLendingsView = new GenreLendingsView();

        genreLendingsView.setGenre( map( dto.getGenre() ) );
        genreLendingsView.setValue( map( dto.getValue() ) );

        return genreLendingsView;
    }

    @Override
    public List<GenreLendingsView> toGenreAvgLendingsView(List<GenreLendingsDTO> dtos) {
        if ( dtos == null ) {
            return null;
        }

        List<GenreLendingsView> list = new ArrayList<GenreLendingsView>( dtos.size() );
        for ( GenreLendingsDTO genreLendingsDTO : dtos ) {
            list.add( toGenreAvgLendingsView( genreLendingsDTO ) );
        }

        return list;
    }

    @Override
    public GenreLendingsCountPerMonthView toGenreLendingsCountPerMonthView(GenreLendingsPerMonthDTO dto) {
        if ( dto == null ) {
            return null;
        }

        List<GenreLendingsView> lendingsCount = null;
        Integer month = null;
        Integer year = null;

        lendingsCount = toGenreAvgLendingsView( dto.getValues() );
        month = dto.getMonth();
        year = dto.getYear();

        GenreLendingsCountPerMonthView genreLendingsCountPerMonthView = new GenreLendingsCountPerMonthView( year, month, lendingsCount );

        return genreLendingsCountPerMonthView;
    }

    @Override
    public List<GenreLendingsCountPerMonthView> toGenreLendingsCountPerMonthView(List<GenreLendingsPerMonthDTO> dtos) {
        if ( dtos == null ) {
            return null;
        }

        List<GenreLendingsCountPerMonthView> list = new ArrayList<GenreLendingsCountPerMonthView>( dtos.size() );
        for ( GenreLendingsPerMonthDTO genreLendingsPerMonthDTO : dtos ) {
            list.add( toGenreLendingsCountPerMonthView( genreLendingsPerMonthDTO ) );
        }

        return list;
    }

    @Override
    public GenreLendingsAvgPerMonthView toGenreLendingsAveragePerMonthView(GenreLendingsPerMonthDTO dto) {
        if ( dto == null ) {
            return null;
        }

        List<GenreLendingsView> durationAverages = null;
        Integer month = null;
        Integer year = null;

        durationAverages = toGenreAvgLendingsView( dto.getValues() );
        month = dto.getMonth();
        year = dto.getYear();

        GenreLendingsAvgPerMonthView genreLendingsAvgPerMonthView = new GenreLendingsAvgPerMonthView( year, month, durationAverages );

        return genreLendingsAvgPerMonthView;
    }

    @Override
    public List<GenreLendingsAvgPerMonthView> toGenreLendingsAveragePerMonthView(List<GenreLendingsPerMonthDTO> dtos) {
        if ( dtos == null ) {
            return null;
        }

        List<GenreLendingsAvgPerMonthView> list = new ArrayList<GenreLendingsAvgPerMonthView>( dtos.size() );
        for ( GenreLendingsPerMonthDTO genreLendingsPerMonthDTO : dtos ) {
            list.add( toGenreLendingsAveragePerMonthView( genreLendingsPerMonthDTO ) );
        }

        return list;
    }
}
