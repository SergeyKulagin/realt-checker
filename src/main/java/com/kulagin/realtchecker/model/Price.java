
package com.kulagin.realtchecker.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "amount",
    "currency",
    "converted"
})
public class Price {

    @JsonProperty("amount")
    public String amount;
    @JsonProperty("currency")
    public String currency;
    @JsonProperty("converted")
    public Converted converted;

}
