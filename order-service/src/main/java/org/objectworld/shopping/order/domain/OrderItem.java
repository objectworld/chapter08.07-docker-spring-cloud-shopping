package org.objectworld.shopping.order.domain;

import org.objectworld.shopping.common.domain.AbstractEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

/**
 * A OrderItem.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of={"quantity", "order"}, callSuper=true)
@Builder
@Entity
@SequenceGenerator(
		name="order_item_seq_gen",
		sequenceName="order_item_seq",
		initialValue=1,
		allocationSize=1
		)
@Table(name = "order_item")
public class OrderItem extends AbstractEntity {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(
    		strategy = GenerationType.SEQUENCE,
    		generator = "order_item_seq_gen"
    )
    private Long id;
    
    @NotNull
    @Column(name = "quantity", nullable = false)
    private Long quantity;

    private Long productId;

    @ManyToOne(fetch = FetchType.LAZY)
    private Order order;
}
