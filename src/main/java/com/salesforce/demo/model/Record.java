package com.salesforce.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Record{

    public Attributes attributes;
    @JsonProperty("Id")
    public String id;
    @JsonProperty("Name")
    public String name;

}
