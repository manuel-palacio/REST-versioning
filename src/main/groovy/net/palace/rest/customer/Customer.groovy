package net.palace.rest.customer

@Immutable
final class Customer {
    String id
    String name
    String email
    String homeAddress
    Collection additionalAddresses = []

}
