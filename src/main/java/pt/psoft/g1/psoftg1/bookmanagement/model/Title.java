package pt.psoft.g1.psoftg1.bookmanagement.model;

import pt.psoft.g1.psoftg1.shared.model.StringUtilsCustom;

public class Title {

    private static final int TITLE_MAX_LENGTH = 128;
    private String title;

    public Title(String title) {
        setTitle(title);
    }

    protected Title() {
        // para uso interno ou frameworks se necessário
    }

    void setTitle(String title) {
        if (title == null)
            throw new IllegalArgumentException("Title cannot be null");

        if (title.isBlank())
            throw new IllegalArgumentException("Title cannot be blank");

        if (title.length() > TITLE_MAX_LENGTH)
            throw new IllegalArgumentException("Title has a maximum of " + TITLE_MAX_LENGTH + " characters");

        // Sanitizar (remover espaços e HTML indesejado, se aplicável)
        this.title = StringUtilsCustom.sanitizeHtml(title.strip());
    }

    @Override
    public String toString() {
        return this.title;
    }

    public String getTitle() {
        return title;
    }
}
