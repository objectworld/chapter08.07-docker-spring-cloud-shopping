package org.objectworld.shopping.product.domain;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import org.objectworld.shopping.common.domain.AbstractEntity;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

/**
 * A Category.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of={"name", "description"})
@Builder
@Entity
@SequenceGenerator(
		name="category_seq_gen"
		, sequenceName="category_seq"
		, initialValue=1
		, allocationSize=1	
		)
@Table(name = "category")
public class Category {

	private static final long serialVersionUID = 1L;
	
    @Id
    @GeneratedValue(
    		strategy = GenerationType.SEQUENCE,
    		generator = "category_seq_gen"
    )
    private Long id;
    
	@NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    @JsonIgnore
    @Builder.Default
    private Set<Product> products = new HashSet<Product>();
    
    @CreatedDate
    @Column(name = "created_date", nullable = false)
    @JsonIgnore
    private Instant createdDate = Instant.now();

    @LastModifiedDate
    @Column(name = "last_modified_date")
    @JsonIgnore
    private Instant lastModifiedDate = Instant.now();
}
