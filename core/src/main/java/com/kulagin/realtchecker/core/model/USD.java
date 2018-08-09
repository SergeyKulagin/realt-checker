
package com.kulagin.realtchecker.core.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "amount",
    "currency"
})
public class USD {

    @JsonProperty("amount")
    public String amount;
    @JsonProperty("currency")
    public String currency;

}
