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
public class OssFile extends BasicDomain {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String fileKey;
    @NotNull
    private String filename;
    @ManyToOne
    private User uploadBy;
}
