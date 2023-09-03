package com.example.wantednotion.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class SubPage {

    private String id;
    private String title;

    public SubPage(String id, String title) {
        this.id = id;
        this.title = title;
    }
}
