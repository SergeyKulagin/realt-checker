
package com.kulagin.realtchecker.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "author_id",
    "location",
    "price",
    "resale",
    "number_of_rooms",
    "floor",
    "number_of_floors",
    "area",
    "photo",
    "seller",
    "created_at",
    "last_time_up",
    "up_available_in",
    "url",
    "auction_bid"
})
public class Apartment {

    @JsonProperty("id")
    public Integer id;
    @JsonProperty("author_id")
    public Integer authorId;
    @JsonProperty("location")
    public Location location;
    @JsonProperty("price")
    public Price price;
    @JsonProperty("resale")
    public Boolean resale;
    @JsonProperty("number_of_rooms")
    public Integer numberOfRooms;
    @JsonProperty("floor")
    public Integer floor;
    @JsonProperty("number_of_floors")
    public Integer numberOfFloors;
    @JsonProperty("area")
    public Area area;
    @JsonProperty("photo")
    public String photo;
    @JsonProperty("seller")
    public Seller seller;
    @JsonProperty("created_at")
    public String createdAt;
    @JsonProperty("last_time_up")
    public String lastTimeUp;
    @JsonProperty("up_available_in")
    public Integer upAvailableIn;
    @JsonProperty("url")
    public String url;
    @JsonProperty("auction_bid")
    public Object auctionBid;

}
