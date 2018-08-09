
package com.kulagin.realtchecker.core.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "total",
    "living",
    "kitchen"
})
@Builder
public class Area {

    @JsonProperty("total")
    public Double total;
    @JsonProperty("living")
    public Double living;
    @JsonProperty("kitchen")
    public Double kitchen;

}
