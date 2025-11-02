package pt.psoft.g1.psoftg1.lendingmanagement.model;

import lombok.Getter;
import pt.psoft.g1.psoftg1.bookmanagement.model.Book;
import pt.psoft.g1.psoftg1.readermanagement.model.ReaderDetails;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.Optional;

@Getter
public class Lending {

    private final LendingNumber lendingNumber;
    private final Book book;
    private final ReaderDetails readerDetails;
    private final LocalDate startDate;
    private final LocalDate limitDate;
    private LocalDate returnedDate;
    private String commentary;
    private final int fineValuePerDayInCents;

    public Lending(Book book, ReaderDetails readerDetails, LendingNumber lendingNumber,
                LocalDate startDate, LocalDate limitDate, int fineValuePerDayInCents) {
        this.book = Objects.requireNonNull(book);
        this.readerDetails = Objects.requireNonNull(readerDetails);
        this.lendingNumber = lendingNumber;
        this.startDate = startDate;
        this.limitDate = limitDate;
        this.fineValuePerDayInCents = fineValuePerDayInCents;
    }

    public Lending(Object object, Object object2, int i, int j, int k) {
        this.lendingNumber = null;
        this.book = new Book();
        this.readerDetails = new ReaderDetails();
        this.startDate = null;
        this.limitDate = null;
        //TODO Auto-generated constructor stub
        this.fineValuePerDayInCents = 0;
    }

    public void setReturned(String commentary) {
        if (this.returnedDate != null)
            throw new IllegalArgumentException("Book already returned!");
        this.commentary = commentary;
        this.returnedDate = LocalDate.now();
    }

    public int getDaysDelayed() {
        LocalDate endDate = (returnedDate != null) ? returnedDate : LocalDate.now();
        return Math.max((int) ChronoUnit.DAYS.between(limitDate, endDate), 0);
    }

    public Optional<Integer> getFineValueInCents() {
        int days = getDaysDelayed();
        if (days > 0) return Optional.of(fineValuePerDayInCents * days);
        return Optional.empty();
    }

    public long getVersion() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getVersion'");
    }

    public static Lending newBootstrappingLending(Book book2, ReaderDetails readerDetails2, int i, int seq,
            LocalDate startDate2, LocalDate returnedDate2, int lendingDurationInDays, int fineValuePerDayInCents2) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'newBootstrappingLending'");
    }
}
