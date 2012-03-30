package net.palace.rest.customer

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@EqualsAndHashCode
@ToString
class Customer {
    String id
    String name
    String email
    String homeAddress
    Collection additionalAddresses = []

}
