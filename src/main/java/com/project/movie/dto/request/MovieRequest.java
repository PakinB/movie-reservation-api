package com.project.movie.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class MovieRequest {
	
	@NotBlank
	@Size(min = 10, max = 70)
	private String movieName;
	
	@NotNull
	@Min(1900)
	@Max(2100)
	private Integer movieYear;
	
	@NotBlank
	@Size(min = 10, max = 70)
	private String genre;
	
	@NotBlank
	private String description;
	
	@NotBlank
	private String movieImage;
	
}
