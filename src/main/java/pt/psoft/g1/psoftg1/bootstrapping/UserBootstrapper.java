package pt.psoft.g1.psoftg1.bootstrapping;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pt.psoft.g1.psoftg1.genremanagement.model.Genre;
import pt.psoft.g1.psoftg1.genremanagement.repositories.GenreRepository;
import pt.psoft.g1.psoftg1.readermanagement.model.ReaderDetails;
import pt.psoft.g1.psoftg1.readermanagement.repositories.ReaderRepository;
import pt.psoft.g1.psoftg1.usermanagement.model.Librarian;
import pt.psoft.g1.psoftg1.usermanagement.model.Reader;
import pt.psoft.g1.psoftg1.usermanagement.model.User;
import pt.psoft.g1.psoftg1.usermanagement.repositories.UserRepository;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Component
@RequiredArgsConstructor
@Profile("bootstrap")
@Order(1)
public class UserBootstrapper implements CommandLineRunner {

    private final UserRepository userRepository;
    private final ReaderRepository readerRepository;
    private final GenreRepository genreRepository;
    private final JdbcTemplate jdbcTemplate;

    @Override
    @Transactional
    public void run(final String... args)  {
        createReaders();
        createLibrarian();
    }

    // ---------- helpers ----------
    private void setCreatedAt(String username, LocalDateTime when) {
        // NOTA: sem "PUBLIC." (H2) nem "dbo." (MSSQL) → deixa o schema por defeito
        jdbcTemplate.update(
                "UPDATE T_USER SET CREATED_AT = ? WHERE USERNAME = ?",
                Timestamp.valueOf(when),
                username
        );
    }

    private Optional<Genre> byGenre(String name) { return genreRepository.findByString(name); }

    // ---------- seed ----------
    private void createReaders() {
        // Reader1 - Manuel
        if (userRepository.findByUsername("manuel@gmail.com").isEmpty()) {
            final Reader manuel = Reader.newReader("manuel@gmail.com", "Manuelino123!", "Manuel Sarapinto das Coives");
            userRepository.save(manuel);
            setCreatedAt(manuel.getUsername(), LocalDateTime.of(2024,1,20,0,0));

            List<Genre> interestList = new ArrayList<>();
            byGenre("Fantasia").ifPresent(interestList::add);
            byGenre("Infantil").ifPresent(interestList::add);

            readerRepository.findByReaderNumber(LocalDate.now().getYear() + "/1")
                    .or(() -> Optional.of(readerRepository.save(
                            new ReaderDetails(
                                    1, manuel, "2000-01-01", "919191919",
                                    true, true, true, "readerPhotoTest.jpg", interestList))))
                    .isPresent();
        }

        // Reader2 - Miguel
        if (userRepository.findByUsername("miguelamorim@gmail.com").isEmpty()) {
            final Reader miguel = Reader.newReader("miguelamorim@gmail.com", "Miguel1250540!", "Miguel Amorim");
            userRepository.save(miguel);
            setCreatedAt(miguel.getUsername(), LocalDateTime.of(2024,3,20,0,0));

            readerRepository.findByReaderNumber(LocalDate.now().getYear() + "/2")
                    .or(() -> Optional.of(readerRepository.save(
                            new ReaderDetails(
                                    2, miguel, "1995-06-02", "929292929",
                                    true, false, false, null, null))))
                    .isPresent();
        }

        // Reader3 - Pedro
        if (userRepository.findByUsername("pedro@gmail.com").isEmpty()) {
            final Reader pedro = Reader.newReader("pedro@gmail.com", "Pedrodascenas!123", "Pedro Das Cenas");
            userRepository.save(pedro);
            setCreatedAt(pedro.getUsername(), LocalDateTime.of(2024,1,20,0,0));

            readerRepository.findByReaderNumber(LocalDate.now().getYear() + "/3")
                    .or(() -> Optional.of(readerRepository.save(
                            new ReaderDetails(
                                    3, pedro, "2001-12-03", "939393939",
                                    true, false, true, null, null))))
                    .isPresent();
        }

        // Reader4 - Catarina
        if (userRepository.findByUsername("catarina@gmail.com").isEmpty()) {
            final Reader catarina = Reader.newReader("catarina@gmail.com", "Catarinamartins!123", "Catarina Martins");
            userRepository.save(catarina);
            setCreatedAt(catarina.getUsername(), LocalDateTime.of(2024,3,20,0,0));

            readerRepository.findByReaderNumber(LocalDate.now().getYear() + "/4")
                    .or(() -> Optional.of(readerRepository.save(
                            new ReaderDetails(
                                    4, catarina, "2002-03-20", "912345678",
                                    true, false, true, null, null))))
                    .isPresent();
        }

        // Reader5 - Marcelo
        if (userRepository.findByUsername("marcelo@gmail.com").isEmpty()) {
            final Reader marcelo = Reader.newReader("marcelo@gmail.com", "Marcelosousa!123", "Marcelo Rebelo de Sousa");
            userRepository.save(marcelo);
            setCreatedAt(marcelo.getUsername(), LocalDateTime.of(2024,1,20,0,0));

            readerRepository.findByReaderNumber(LocalDate.now().getYear() + "/5")
                    .or(() -> Optional.of(readerRepository.save(
                            new ReaderDetails(
                                    5, marcelo, "2000-06-03", "912355678",
                                    true, false, true, null, null))))
                    .isPresent();
        }

        // Reader6 - Luís
        if (userRepository.findByUsername("luis@gmail.com").isEmpty()) {
            final Reader luis = Reader.newReader("luis@gmail.com", "Luismontenegro!123", "Luís Montenegro");
            userRepository.save(luis);
            setCreatedAt(luis.getUsername(), LocalDateTime.of(2024,3,20,0,0));

            readerRepository.findByReaderNumber(LocalDate.now().getYear() + "/6")
                    .or(() -> Optional.of(readerRepository.save(
                            new ReaderDetails(
                                    6, luis, "1999-03-03", "912355678",
                                    true, false, true, null, null))))
                    .isPresent();
        }

        // Reader7 - António
        if (userRepository.findByUsername("antonio@gmail.com").isEmpty()) {
            final Reader antonio = Reader.newReader("antonio@gmail.com", "Antoniocosta!123", "António Costa");
            userRepository.save(antonio);
            setCreatedAt(antonio.getUsername(), LocalDateTime.of(2024,6,20,0,0));

            readerRepository.findByReaderNumber(LocalDate.now().getYear() + "/7")
                    .or(() -> Optional.of(readerRepository.save(
                            new ReaderDetails(
                                    7, antonio, "2001-03-03", "912355778",
                                    true, false, true, null, null))))
                    .isPresent();
        }

        // Reader8 - André
        if (userRepository.findByUsername("andre@gmail.com").isEmpty()) {
            final Reader andre = Reader.newReader("andre@gmail.com", "Andreventura!123", "André Ventura");
            userRepository.save(andre);
            setCreatedAt(andre.getUsername(), LocalDateTime.of(2024,5,20,0,0));

            readerRepository.findByReaderNumber(LocalDate.now().getYear() + "/8")
                    .or(() -> Optional.of(readerRepository.save(
                            new ReaderDetails(
                                    8, andre, "2001-03-03", "912355888",
                                    true, false, true, null, null))))
                    .isPresent();
        }
    }

    private void createLibrarian(){
        if (userRepository.findByUsername("maria@gmail.com").isEmpty()) {
            final User maria = Librarian.newLibrarian("maria@gmail.com", "Mariaroberta!123", "Maria Roberta");
            userRepository.save(maria);
        }
    }
}