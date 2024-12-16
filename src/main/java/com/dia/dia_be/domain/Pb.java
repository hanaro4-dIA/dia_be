package com.dia.dia_be.domain;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter(AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Pb {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(columnDefinition = "INT UNSIGNED")
	private Long id;

	@Column(nullable = false, updatable = false, columnDefinition = "VARCHAR(20)")
	private String password;

	@Column(nullable = false, columnDefinition = "VARCHAR(20)")
	private String name;

	@Column(columnDefinition = "VARCHAR(250)")
	private String imageUrl;

	@Column(nullable = false, columnDefinition = "VARCHAR(300)")
	private String introduce;

	@Column(nullable = false, columnDefinition = "VARCHAR(300)")
	private String office;

	@Column(columnDefinition = "TEXT")
	private String career;

	@Column(nullable = false, columnDefinition = "VARCHAR(20)")
	private String loginId;

	@Column(nullable = false, columnDefinition = "VARCHAR(20)")
	private String tel;

	@Column(nullable = false, columnDefinition = "TINYINT(1)")
	private boolean availability;

	@OneToMany(mappedBy = "pb", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Customer> customer = new ArrayList<>();

	@OneToMany(mappedBy = "pb", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Hashtag> hashtag = new ArrayList<>();

	private Pb(String password, String name, String imageUrl, String introduce, String office, String career,
		String loginId, String tel, boolean availability) {
		this.password = password;
		this.name = name;
		this.imageUrl = imageUrl;
		this.introduce = introduce;
		this.office = office;
		this.career = career;
		this.loginId = loginId;
		this.tel = tel;
		this.availability = availability;
	}

	@Builder
	public static Pb create(String password, String name, String imageUrl, String introduce, String office,
		String career, String loginId, String tel, boolean availability) {
		return new Pb(password, name, imageUrl, introduce, office, career, loginId, tel, availability);
	}

	public Pb update(String password, String name, String imageUrl, String introduce, String office, String career,
		String tel) {
		this.password = password;
		this.name = name;
		this.imageUrl = imageUrl;
		this.introduce = introduce;
		this.office = office;
		this.career = career;
		this.tel = tel;
		return this;
	}

	public Pb update(String imageUrl, String introduce) {
		if (imageUrl != null) {
			this.imageUrl = imageUrl;
		}
		if (introduce != null) {
			this.introduce = introduce;
		}
		return this;
	}

	public Pb updateAvailability(Boolean availability) {
		if (availability != null) {
			this.availability = availability;
		}
		return this;
	}
}
