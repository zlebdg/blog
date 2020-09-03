package com.github.xuqplus2.blog.vo.req;

import com.github.xuqplus2.blog.domain.Document;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class SignReq implements Serializable {
    private List<Document> documents;
    private List<Stamper> stampers;
}
