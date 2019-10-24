package com.globant.brainwaves.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;

@Document(indexName = "elastichq", type = "wave")
public class Wave implements Serializable {

    private static final long serialVersionUID=1;

    @Id
    private String id;
    private String name;

    public Wave() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
