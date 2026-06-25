package com.project.movie.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovieResponse {
	
	private Integer movieId;
	
	private String movieName;
	
	private Integer movieYear;
	
	private String genre;
	
	private String description;
	
	private String movieImage;

}
