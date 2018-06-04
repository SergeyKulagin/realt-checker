
package com.kulagin.realtchecker.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "BYN",
    "USD"
})
public class Converted {

    @JsonProperty("BYN")
    public BYN bYN;
    @JsonProperty("USD")
    public USD uSD;

}
