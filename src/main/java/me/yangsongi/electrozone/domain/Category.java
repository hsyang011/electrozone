package me.yangsongi.electrozone.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Category {

    CPU("cpu"),
    MAINBOARD("mainboard"),
    MEMORY("memory"),
    GPU("gpu"),
    SSD("ssd"),
    HDD("hdd"),
    CASE("case"),
    POWER("power"),
    CPU_COOLER("cpu_cooler"),
    MONITOR("monitor"),
    SOFTWARE("software"),
    MOUSE("mouse"),
    KEYBOARD("keyboard"),
    SPEAKER("speaker"),
    HEADSET("headset");

    private final String name;

}
