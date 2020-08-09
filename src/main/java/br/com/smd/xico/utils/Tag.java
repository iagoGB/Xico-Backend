package br.com.smd.xico.utils;


import lombok.Getter;

@Getter
public enum Tag {
    DESIGN("Design"),
    MODELAGEM("Modelagem"),
    PROGRAMACAO("Programacao"),
    AUDIOVISUAL("Audiovisual"),
    GAME("Game");

    private String value;

    Tag(final String tag) {
        this.value = tag;
    }
}
