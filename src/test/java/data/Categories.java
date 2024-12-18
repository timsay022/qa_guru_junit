package data;

public enum Categories {
    clothers("Одежда"),
    technique("Бытовая техника"),
    shoes("Обувь");

    public final String description;

    Categories(String description) {
        this.description = description;
    }
}
