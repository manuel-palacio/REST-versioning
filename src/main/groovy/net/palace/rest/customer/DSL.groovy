package net.palace.rest.customer

import com.tinkerpop.blueprints.pgm.Vertex

class DSL {

    /**
     * Example of fluid interface with groovy
     */
    static def respond() {
        [withEntity: { def customer ->
            [withVersion: {def mediaType ->
                new CustomerResponseBuilder().withCustomer(customer).withMediaType(mediaType).build()
            }]
        }]
    }
}
