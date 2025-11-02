package pt.psoft.g1.psoftg1.lendingmanagement.model;

import lombok.Getter;
import lombok.Setter;
import java.util.Objects;

@Getter
public class Fine {

    private final int fineValuePerDayInCents;
    private final int centsValue;

    @Setter
    private Lending lending;

    public Fine(Lending lending) {
        if (lending.getDaysDelayed() <= 0)
            throw new IllegalArgumentException("Lending is not overdue");

        this.fineValuePerDayInCents = lending.getFineValuePerDayInCents();
        this.centsValue = fineValuePerDayInCents * lending.getDaysDelayed();
        this.lending = Objects.requireNonNull(lending);
    }
}
