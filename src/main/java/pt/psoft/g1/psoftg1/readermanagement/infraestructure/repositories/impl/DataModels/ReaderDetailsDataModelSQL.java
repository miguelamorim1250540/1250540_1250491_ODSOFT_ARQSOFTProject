package pt.psoft.g1.psoftg1.readermanagement.infraestructure.repositories.impl.DataModels;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pt.psoft.g1.psoftg1.readermanagement.model.*;
import pt.psoft.g1.psoftg1.usermanagement.model.Reader;
import pt.psoft.g1.psoftg1.genremanagement.model.Genre;

import java.util.List;

@Entity
@Table(name = "READER_DETAILS")
@Getter
@Setter
@NoArgsConstructor
public class ReaderDetailsDataModelSQL {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long pk;

    @OneToOne
    @JoinColumn(name = "reader_id", nullable = false)
    private Reader reader;

    @Embedded
    private ReaderNumber readerNumber;

    @Embedded
    private BirthDate birthDate;

    @Embedded
    private PhoneNumber phoneNumber;

    private boolean gdprConsent;
    private boolean marketingConsent;
    private boolean thirdPartySharingConsent;

    @Version
    private Long version;

    @ManyToMany
    @JoinTable(
        name = "reader_interest_genres",
        joinColumns = @JoinColumn(name = "reader_id"),
        inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private List<Genre> interestList;

    private String photoUri;
}