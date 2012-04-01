package net.palace.rest.customer

class DSL {

    /**
     * Example of fluid interface with groovy
     */
    static def respond() {
        [withEntity: { Customer customer ->
            [withVersion: {String mediaType ->
                new CustomerResponseBuilder().withCustomer(customer).withMediaType(mediaType).build()
            }]
        }]
    }
}
