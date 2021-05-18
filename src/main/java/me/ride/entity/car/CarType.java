package me.ride.entity.car;

public enum CarType {
    ECONOMY("Эконом"),
    COMPACT ("Компакт"),
    COMPACT_SUV ("Компактный кроссовер"),
    INTERMEDIATE ("Средний"),
    INTERMEDIATE_SUV ("Средний кроссовер"),
    STANDARD("Стандарт"),
    STANDARD_SUV("Стандартный кроссовер"),
    FULL_SIZE ("Полноразмерный"),
    FULL_SIZE_SUV ("Полноразмерный кроссовер"),
    PREMIUM ("Премиум"),
    MINIVAN ("Минивэн"),
    SPORT ("Спортивный");

    private String title;

    CarType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
