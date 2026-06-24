package com.project.movie.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@Builder
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "movies")
public class Movie {

	@Id
	@SequenceGenerator(name = "movie_id_seq", sequenceName = "movies_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "movie_id_seq")
	@Column(nullable = false)
	private Integer movieId;
	
	@NotNull
	@Column(nullable = false)
	private String movieName;
	
	@NotNull
	@Column(nullable = false)
	private Integer movieYear;
	
	@NotNull
	@Column(nullable = false)
	private String genre;
	
	@NotNull
	@Column(nullable = false)
	private String description;
	
	@Column(name = "image_url")
	private String movieImage;
	
	

}
