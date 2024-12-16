package com.dia.dia_be.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter(AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Hashtag {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "pb_id", nullable = false, columnDefinition = "INT UNSIGNED")
	private Pb pb;

	@Column(nullable = false, columnDefinition = "VARCHAR(20)")
	private String name;

	private Hashtag(String name) {
		this.name = name;
	}

	@Builder
	public static Hashtag create(Pb pb, String name) {
		Hashtag pbHashtag = new Hashtag(name);
		pbHashtag.addPb(pb);
		return pbHashtag;
	}

	public Hashtag update(String name){
		this.name = name;
		return this;
	}

	private void addPb(Pb pb) {
		this.pb = pb;
		pb.getHashtag().add(this);
	}

}
