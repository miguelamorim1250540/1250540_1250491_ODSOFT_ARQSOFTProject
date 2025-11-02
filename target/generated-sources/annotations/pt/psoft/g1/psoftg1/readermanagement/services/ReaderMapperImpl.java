package pt.psoft.g1.psoftg1.readermanagement.services;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import pt.psoft.g1.psoftg1.genremanagement.model.Genre;
import pt.psoft.g1.psoftg1.readermanagement.model.ReaderDetails;
import pt.psoft.g1.psoftg1.usermanagement.model.Reader;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-01T18:54:10+0000",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.44.0.v20251023-0518, environment: Java 21.0.8 (Eclipse Adoptium)"
)
@Component
public class ReaderMapperImpl extends ReaderMapper {

    @Override
    public Reader createReader(CreateReaderRequest request) {
        if ( request == null ) {
            return null;
        }

        String username = null;
        String password = null;

        username = request.getUsername();
        password = request.getPassword();

        Reader reader = new Reader( username, password );

        reader.setName( request.getFullName() );

        return reader;
    }

    @Override
    public ReaderDetails createReaderDetails(int readerNumber, Reader reader, CreateReaderRequest request, String photoURI, List<Genre> interestList) {
        if ( reader == null && request == null && photoURI == null && interestList == null ) {
            return null;
        }

        ReaderDetails readerDetails = new ReaderDetails();

        readerDetails.setReader( reader );
        readerDetails.setPhoto( photoURI );
        List<Genre> list = interestList;
        if ( list != null ) {
            readerDetails.setInterestList( new ArrayList<Genre>( list ) );
        }

        return readerDetails;
    }
}
