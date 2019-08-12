package com.github.xuqplus2.blog.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
public class AppText implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Lob
    private String text;

    public AppText() {
    }

    public AppText(String text) {
        this.text = text;
    }
}
