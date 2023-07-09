package com.nemo.telegrambot.model.freegpt;

public enum Model {

    GPT_3_5_TURBO("gpt-3.5-turbo"),
    GPT_3_5_TURBO_0613("gpt-3.5-turbo-0613"),
    GPT_3_5_TURBO_16K_0613("gpt-3.5-turbo-16k-0613"),
    GPT_3_5_TURBO_16K("gpt-3.5-turbo-16k"),
    GPT_4("gpt-4");
    private String value;
     Model(String value) {
         this.value = value;
    }
}
