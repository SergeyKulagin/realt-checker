
package com.kulagin.realtchecker.core.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "address",
    "user_address",
    "latitude",
    "longitude"
})
@Getter
@Setter
@Builder
public class Location {

    @JsonProperty("address")
    public String address;
    @JsonProperty("user_address")
    public String userAddress;
    @JsonProperty("latitude")
    public Double latitude;
    @JsonProperty("longitude")
    public Double longitude;

}
