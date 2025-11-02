package pt.psoft.g1.psoftg1.bookmanagement.infrastructure.repositories.impl.mappers;

import pt.psoft.g1.psoftg1.bookmanagement.infrastructure.repositories.impl.DataModelSQL.TitleDataModelSQL;
import pt.psoft.g1.psoftg1.bookmanagement.model.Title;

public class TitleMapper {

    public static TitleDataModelSQL toDataModel(Title title) {
        if (title == null) return null;
        return new TitleDataModelSQL(title.toString());
    }

    public static Title toDomain(TitleDataModelSQL entity) {
        if (entity == null) return null;
        return new Title(entity.getTitle());
    }
}
