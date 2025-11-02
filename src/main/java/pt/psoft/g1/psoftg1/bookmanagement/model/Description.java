package pt.psoft.g1.psoftg1.bookmanagement.model;

import pt.psoft.g1.psoftg1.shared.model.StringUtilsCustom;

public class Description {

    private static final int DESC_MAX_LENGTH = 4096;
    private String description;

    public Description(String description) {
        setDescription(description);
    }

    protected Description() {
        // ORM-safe
    }

    public void setDescription(String description) {
        if (description == null || description.isBlank()) {
            this.description = null;
        } else if (description.length() > DESC_MAX_LENGTH) {
            throw new IllegalArgumentException("Description has a maximum of 4096 characters");
        } else {
            this.description = StringUtilsCustom.sanitizeHtml(description);
        }
    }

    public String toString() {
        return this.description;
    }
}
