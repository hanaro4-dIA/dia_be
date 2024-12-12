package com.dia.dia_be.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter(AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Consulting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "INT UNSIGNED")
    private Long id;

    @Column(columnDefinition = "VARCHAR(50)")
    private String title;

    @Column(nullable = false, updatable = false)
    private LocalDate hope_date;


    @Column(nullable = false, updatable = false)
    private LocalTime hope_time;

    @CreationTimestamp
    @Column(nullable = false, updatable = false, columnDefinition = "DATE")
    private LocalDate reserve_date;

    @CreationTimestamp
    @Column(nullable = false, updatable = false, columnDefinition = "TIME")
    private LocalTime reserve_time;

    @Column(nullable = false, columnDefinition = "VARCHAR(500)")
    private String content;

    @Column(nullable = false, columnDefinition = "TINYINT(1)")
    private boolean approve;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="category_id")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="customer_id")
    private Customer customer;

    @OneToOne(mappedBy = "consulting", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Journal journal;

    private Consulting(String title, LocalDate hope_date, LocalTime hope_time, LocalDate reserve_date, LocalTime reserve_time, String content, boolean approve) {
        this.title = title;
        this.hope_date = hope_date;
        this.hope_time = hope_time;
        this.reserve_date = reserve_date;
        this.reserve_time = reserve_time;
        this.content = content;
        this.approve = approve;
    }

    @Builder
    public static Consulting create(Category category, Customer customer, String title, LocalDate hope_date, LocalTime hope_time, LocalDate reserve_date, LocalTime reserve_time, String content, boolean approve) {
        Consulting consulting = new Consulting(title,hope_date, hope_time,reserve_date,reserve_time,content,approve);
        consulting.addCategory(category);
        consulting.addCustomer(customer);
        consulting.addJournal(new Journal());
        return consulting;
    }

    private void addCategory(Category category) {
        this.category = category;
        category.getConsulting().add(this);
    }

    private void addCustomer(Customer customer_) {
        this.customer = customer_;
        customer_.getConsulting().add(this);
    }

    private void addJournal(Journal journal) {
        this.journal = journal;
        journal.setConsulting(this);
    }
}
