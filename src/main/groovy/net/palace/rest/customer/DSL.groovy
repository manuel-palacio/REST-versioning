package net.palace.rest.customer

class DSL {

    /**
     * Example of fluid interface with groovy
     * @return
     */
    static def respond() {
        [with: { Customer customer ->
            [for: {String mediaType ->
                new CustomerResponseBuilder().withCustomer(customer).withMediaType(mediaType).build()
            }]
        }]
    }
}
