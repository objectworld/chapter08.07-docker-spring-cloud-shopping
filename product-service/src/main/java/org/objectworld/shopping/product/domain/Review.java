package org.objectworld.shopping.product.domain;

import org.objectworld.shopping.common.domain.AbstractEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
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
 * A Review.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"title", "description", "rating"}, callSuper=true)
@ToString(of = {"title", "description", "rating"}, callSuper=true)
@Entity
@SequenceGenerator(
		name="review_seq_gen",
		sequenceName="review_seq",
		initialValue=1,
		allocationSize=1
		)
@Table(name = "review")
public class Review extends AbstractEntity {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(
    		strategy = GenerationType.SEQUENCE,
    		generator = "review_seq_gen"
    )
    private Long id;
    
    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @NotNull
    @Column(name = "rating", nullable = false)
    private Long rating;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinTable(name = "product_review"	// 조인 테이블명
		, joinColumns = @JoinColumn(name = "review_id")	// 외래 키
		, inverseJoinColumns = @JoinColumn(name = "product_id")	// Target 엔티티의 외래키
    )
    private Product product;
    
    @Builder
    public Review(@NotNull String title, @NotNull String description, @NotNull Long rating) {
        this.title = title;
        this.description = description;
        this.rating = rating;
    }
}
