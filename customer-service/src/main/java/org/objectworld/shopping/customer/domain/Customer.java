package org.objectworld.shopping.customer.domain;

import java.util.Set;

import org.objectworld.shopping.common.domain.AbstractEntity;
import org.objectworld.shopping.common.domain.Address;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * A Customer.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of= {"id", "firstName", "lastName", "email", "telephone"}, callSuper=true)
@ToString(callSuper=true)
@Builder
@Entity
@SequenceGenerator(
		name="customer_seq_gen",
		sequenceName="customer_seq",
		initialValue=1,
		allocationSize=1
		)
@Table(name = "customer")
public class Customer extends AbstractEntity {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(
    		strategy = GenerationType.SEQUENCE,
    		generator = "customer_seq_gen"
    )
    private Long id;
    
    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Email
    @Column(name = "email")
    private String email;

    @Column(name = "telephone")
    private String telephone;

    @OneToMany(mappedBy = "customer")
    @JsonIgnore
    private Set<Cart> carts;

    @Embedded
    @AttributeOverride(name = "address1", column = @Column(name = "home_address_1"))
    @AttributeOverride(name = "address2", column = @Column(name = "home_address_2"))
    @AttributeOverride(name = "city", column = @Column(name = "home_city"))
    @AttributeOverride(name = "country", column = @Column(name = "home_country"))
    @AttributeOverride(name = "postcode", column = @Column(name = "home_postcode"))
    private Address homeAddress;
    
    @Embedded
    @AttributeOverride(name = "address1", column = @Column(name = "office_address_1"))
    @AttributeOverride(name = "address2", column = @Column(name = "office_address_2"))
    @AttributeOverride(name = "city", column = @Column(name = "office_city"))
    @AttributeOverride(name = "country", column = @Column(name = "office_country"))
    @AttributeOverride(name = "postcode", column = @Column(name = "office_postcode"))
    private Address officeAddress;
    
    @Column(name = "enabled", nullable = false)
    private Boolean enabled;
}
