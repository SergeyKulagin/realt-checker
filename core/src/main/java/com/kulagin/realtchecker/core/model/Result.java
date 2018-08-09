
package com.kulagin.realtchecker.core.model;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "apartments",
    "total",
    "page"
})
@Getter
public class Result {

    @JsonProperty("apartments")
    public List<Apartment> apartments = null;
    @JsonProperty("total")
    public Integer total;
    @JsonProperty("page")
    public Page page;

}
