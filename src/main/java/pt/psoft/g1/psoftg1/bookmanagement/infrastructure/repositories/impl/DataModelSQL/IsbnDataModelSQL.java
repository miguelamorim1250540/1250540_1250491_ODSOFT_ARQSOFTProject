package pt.psoft.g1.psoftg1.bookmanagement.infrastructure.repositories.impl.DataModelSQL;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "isbn")
@Getter
@Setter
@NoArgsConstructor
public class IsbnDataModelSQL {

    @Id
    @Column(name = "isbn", length = 16, nullable = false)
    private String isbn;

    public IsbnDataModelSQL(String isbn) {
        this.isbn = isbn;
    }
}
