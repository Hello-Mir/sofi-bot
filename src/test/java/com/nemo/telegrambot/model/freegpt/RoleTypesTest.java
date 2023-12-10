package com.nemo.telegrambot.model.freegpt;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RoleTypesTest {
    @Test
    public void getRoleTypeLowCase() {
        Assertions.assertEquals("user", RoleTypes.USER.getRoleTypeLowCase());
    }
}
