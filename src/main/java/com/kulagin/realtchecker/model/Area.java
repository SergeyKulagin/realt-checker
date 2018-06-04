
package com.kulagin.realtchecker.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "total",
    "living",
    "kitchen"
})
public class Area {

    @JsonProperty("total")
    public Double total;
    @JsonProperty("living")
    public Double living;
    @JsonProperty("kitchen")
    public Double kitchen;

}
