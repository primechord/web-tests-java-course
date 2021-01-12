package addressbook.enums;

public enum ReasonFailure {
    COUNT_IS_DIFFERENT("Количество групп отличается"),
    COMPOSITION_IS_DIFFERENT("Состав групп отличается");

    private final String reason;

    ReasonFailure(String reason) {
        this.reason = reason;
    }

    public String r() {
        return reason;
    }

}
