package org.objectworld.shopping.customer.domain;

import org.objectworld.shopping.common.domain.AbstractEntity;
import org.objectworld.shopping.customer.domain.enumeration.CartStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
 * A Cart.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of= {"customer", "status"}, callSuper=true)
@ToString(callSuper=true)
@Builder
@Entity
@SequenceGenerator(
		name="cart_seq_gen",
		sequenceName="cart_seq",
		initialValue=1,
		allocationSize=1
		)
@Table(name = "cart")
public class Cart extends AbstractEntity {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(
    		strategy = GenerationType.SEQUENCE,
    		generator = "cart_seq_gen"
    )
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    private Customer customer;

    @NotNull
    @Enumerated(EnumType.STRING)
    private CartStatus status;
}
