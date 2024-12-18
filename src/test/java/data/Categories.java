package data;

public enum Categories {
    CLOTHES("Одежда"),
    TECHNIQUE("Бытовая техника"),
    SHOES("Обувь");

    public final String description;

    Categories(String description) {
        this.description = description;
    }
}
