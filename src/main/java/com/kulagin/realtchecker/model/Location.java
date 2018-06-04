
package com.kulagin.realtchecker.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "address",
    "user_address",
    "latitude",
    "longitude"
})
@Getter
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
