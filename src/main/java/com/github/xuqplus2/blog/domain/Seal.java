package com.github.xuqplus2.blog.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Data
@Entity
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class Seal extends BasicDomain {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private Integer width;
    @NotNull
    private Integer height;
    @NotNull
    private String name;
    @ManyToOne
    private OssFile file;
    @NotNull
    @ManyToOne
    private User user;
}
