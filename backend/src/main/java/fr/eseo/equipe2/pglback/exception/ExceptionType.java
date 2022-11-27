package fr.eseo.equipe2.pglback.exception;

public enum ExceptionType {
    ENTITY_NOT_FOUND("not.found"),
    DUPLICATE_ENTITY("duplicate"),
    BAD_CREDENTIALS("bad.credentials"),
    ENTITY_EXCEPTION("exception"),
    NO_VALUE("no.value");

    String value;

    ExceptionType(String value) {
        this.value = value;
    }

    String getValue() {
        return this.value;
    }
}
