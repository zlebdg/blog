package com.github.xuqplus2.blog.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Data
@Entity
public class Document extends BasicDomain {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @ManyToOne
    private OssFile file;
    @NotNull
    @ManyToOne
    private User user;
    @Enumerated(EnumType.ORDINAL)
    private Type type;

    public enum Type {
        GENERAL,
        SIGNED,
        ;
    }
}
