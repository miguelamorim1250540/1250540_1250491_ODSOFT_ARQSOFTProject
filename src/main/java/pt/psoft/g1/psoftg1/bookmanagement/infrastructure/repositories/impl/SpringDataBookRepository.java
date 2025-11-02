package pt.psoft.g1.psoftg1.bookmanagement.infrastructure.repositories.impl;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import pt.psoft.g1.psoftg1.bookmanagement.infrastructure.repositories.impl.DataModelSQL.BookDataModelSQL;

@Profile("sqlRedis")
public interface SpringDataBookRepository 
        extends JpaRepository<BookDataModelSQL, Long>, BookRepoCustom {
}
