package me.yangsongi.electrozone.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Category {

    CPU("cpu"),
    MOTHERBOARD("motherboard"),
    MEMORY("memory"),
    GPU("gpu"),
    SSD("ssd"),
    HDD("hdd"),
    CASE("case"),
    POWER("power"),
    COOLER_TUNING("cooler_tuning"),
    KEYBOARD("keyboard"),
    MOUSE("mouse"),
    MONITOR("monitor");

    private final String name;

}
