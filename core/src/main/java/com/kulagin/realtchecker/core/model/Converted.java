
package com.kulagin.realtchecker.core.model;

import com.fasterxml.jackson.annotation.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "USD"
})
@JsonIgnoreProperties(ignoreUnknown = true)
public class Converted {

    @JsonProperty("USD")
    public USD uSD;

}
