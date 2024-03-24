package org.objectworld.shopping.product.domain;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import org.objectworld.shopping.common.domain.AbstractEntity;
import org.objectworld.shopping.product.domain.enumeration.ProductStatus;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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

/**
 * A Product.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = {"name", "description", "price", "status", "salesCounter", "category"})
@ToString(of = {"name", "description", "price", "status", "salesCounter", "category"}, callSuper=true)
@Entity
@SequenceGenerator(
		name="product_seq_gen"
		, sequenceName="product_seq"
		, initialValue = 1
		, allocationSize = 1
		)
@Table(name = "product")
public class Product {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(
    		strategy = GenerationType.SEQUENCE,
    		generator = "product_seq_gen"
    )
    private Long id;
    
    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @NotNull
    @Column(name = "price", precision = 10, scale = 2, nullable = false)
    private BigDecimal price;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ProductStatus status;

    @Column(name = "sales_counter")
    private Integer salesCounter;


    @CreatedDate
    @Column(name = "created_date", nullable = false)
    @JsonIgnore
    private Instant createdDate = Instant.now();

    @LastModifiedDate
    @Column(name = "last_modified_date")
    @JsonIgnore
    private Instant lastModifiedDate = Instant.now();

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "product_review"	// 조인 테이블명
    	, joinColumns = @JoinColumn(name = "product_id")	// 외래 키
    	, inverseJoinColumns = @JoinColumn(name = "review_id")	// Target 엔티티의 외래키
    )
    @JsonIgnore
    private Set<Review> reviews = new HashSet<Review>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;
}
