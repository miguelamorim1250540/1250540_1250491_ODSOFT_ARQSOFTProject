package pt.psoft.g1.psoftg1.bookmanagement.infrastructure.repositories.impl.DataModelSQL;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "title")
@Getter
@Setter
@NoArgsConstructor
public class TitleDataModelSQL {

    @Id
    @Column(name = "title", length = 128, nullable = false)
    private String title;

    public TitleDataModelSQL(String title) {
        this.title = title;
    }
}
