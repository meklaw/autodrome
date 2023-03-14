package ru.meklaw.autocontroller.dto;

public enum ReportType {
    CAR("Пробег автомобиля за период");

    private final String description;

    ReportType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }
}
