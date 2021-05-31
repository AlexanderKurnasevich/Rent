package me.ride.entity.system;

public enum OrderStatus {
    UNDER_CONSIDERATION("Обрабатывается"),
    ACCEPTED("Одобрен"),
    PAID("Оплачен"),
    RETURNED("Завершён"),
    CAR_DAMAGED("Машина повреждена"),
    REFUSED("Отказано");

    private final String title;

    OrderStatus(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
