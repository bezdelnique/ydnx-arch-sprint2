package dev.bzd9.event.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MovieEvent {
    @JsonProperty("movie_id")
    private Long movieId;
    @JsonProperty("user_id")
    private Long userId;
    private String title;
    private String action;
}
