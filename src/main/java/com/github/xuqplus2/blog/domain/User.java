package com.github.xuqplus2.blog.domain;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Builder
@Data
public class User {
    @Id
    private Long id;
    private String name;
}
