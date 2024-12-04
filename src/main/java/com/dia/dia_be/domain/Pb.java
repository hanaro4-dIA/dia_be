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

	@Column(nullable = false, columnDefinition = "VARCHAR(20)")
	private String password;

	@Column(nullable = false, columnDefinition = "VARCHAR(20)")
	private String name;

	@Column(columnDefinition = "VARCHAR(100)")
	private String image_url;

	@Column(nullable = false, columnDefinition = "VARCHAR(300)")
	private String introduce;

	@Column(nullable = false, columnDefinition = "VARCHAR(300)")
	private String office;

	@Column(columnDefinition = "TEXT")
	private String career;

	@Column(nullable = false, columnDefinition = "VARCHAR(20)")
	private String login_id;

	@Column(nullable = false, columnDefinition = "VARCHAR(20)")
	private String tel;

	@Column(nullable = false, columnDefinition = "TINYINT(1)")
	private boolean availability;

	@OneToMany(mappedBy = "pb", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Customer_pb> customer_pb = new ArrayList<>();

	@OneToMany(mappedBy = "pb", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Hashtag> hashtag = new ArrayList<>();

	private Pb(String password, String name, String image_url, String introduce, String office, String career,
		String login_id, String tel, boolean availability) {
		this.password = password;
		this.name = name;
		this.image_url = image_url;
		this.introduce = introduce;
		this.office = office;
		this.career = career;
		this.login_id = login_id;
		this.tel = tel;
		this.availability = availability;
	}

	@Builder
	public static Pb create(String password, String name, String image_url, String introduce, String office,
		String career,
		String login_id, String tel, boolean availability) {
		return new Pb(password, name, image_url, introduce, office, career, login_id, tel, availability);
	}
}
