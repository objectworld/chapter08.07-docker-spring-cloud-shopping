package org.objectworld.shopping.common.domain;


import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * An Address.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of={"address1", "address2", "city", "postcode", "country"})
@ToString()
@Builder
@Embeddable
public class Address implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Column(name = "address_1")
    private String address1;

    @Column(name = "address_2")
    private String address2;

    @Column(name = "city")
    private String city;

    @Size(max = 10)
    @Column(name = "postcode", length = 10)
    private String postcode;

    @Size(max = 2)
    @Column(name = "country", length = 2)
    private String country;
}
