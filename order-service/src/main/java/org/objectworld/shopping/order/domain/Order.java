package org.objectworld.shopping.order.domain;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Set;

import org.objectworld.shopping.common.domain.AbstractEntity;
import org.objectworld.shopping.common.domain.Address;
import org.objectworld.shopping.order.domain.enumeration.OrderStatus;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
 * A Orders.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of={"totalPrice", "status", "shipped", "payment", "shipmentAddress"}, callSuper=true)
@ToString(callSuper=true)
@Builder
@Entity
@SequenceGenerator(
		name="order_seq_gen",
		sequenceName="order_seq",
		initialValue=1,
		allocationSize=1
		)
@Table(name = "orders")
public class Order extends AbstractEntity {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(
    		strategy = GenerationType.SEQUENCE,
    		generator = "order_seq_gen"
    )
    private Long id;
    
    @NotNull
    @Column(name = "total_price", precision = 10, scale = 2, nullable = false)
    private BigDecimal totalPrice;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private OrderStatus status;

    @Column(name = "shipped")
    private ZonedDateTime shipped;

    private Long cartId;

    @OneToOne(mappedBy="order")
    private Payment payment;

    @Embedded
    private Address shipmentAddress;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<OrderItem> orderItems;
}
