package net.palace.rest.customer

class DSL {

    static def respond() {
        [withEntity: { def customer ->
            [withVersion: {def mediaType ->
                new CustomerResponseBuilder().withCustomer(customer).withMediaType(mediaType).build()
            }]
        }]
    }
}
