package com.dia.dia_be.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Pb_hashtag {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "pb_id", nullable = false, columnDefinition = "INT UNSIGNED")
	private Pb pb;

	@ManyToOne
	@JoinColumn(name = "hashtag_id", nullable = false, columnDefinition = "INT UNSIGNED")
	private Hashtag hashtag;

	@Builder
	public static Pb_hashtag create(Pb pb, Hashtag hashTag) {
		Pb_hashtag pb_hashtag = new Pb_hashtag();
		pb_hashtag.addPb(pb);
		pb_hashtag.addHashtag(hashTag);
		return pb_hashtag;
	}

	private void addPb(Pb pb) {
		this.pb = pb;
		pb.getPb_hashtag().add(this);
	}

	private void addHashtag(Hashtag hashtag) {
		this.hashtag = hashtag;
		hashtag.getPb_hashtag().add(this);
	}

}
