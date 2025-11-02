package pt.psoft.g1.psoftg1.shared.model;

import lombok.Getter;

@Getter
public class ForbiddenName {
    private final String forbiddenName;

    public ForbiddenName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Forbidden name cannot be null or blank");
        }
        this.forbiddenName = name;
    }

    @Override
    public String toString() {
        return forbiddenName;
    }
}
