
package com.kulagin.realtchecker.core.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "limit",
    "items",
    "current",
    "last"
})
@Getter
public class Page {

    @JsonProperty("limit")
    public Integer limit;
    @JsonProperty("items")
    public Integer items;
    @JsonProperty("current")
    public Integer current;
    @JsonProperty("last")
    public Integer last;

}
