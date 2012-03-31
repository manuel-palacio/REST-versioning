package net.palace.rest.customer

import com.sun.jersey.spi.resource.Singleton

import javax.annotation.PostConstruct
import javax.ws.rs.GET
import javax.ws.rs.HeaderParam
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.core.Response

import static net.palace.rest.RestPrecondition.*

@Path("/customer")
@Singleton
class CustomerResource {

    Collection<Customer> customers = []

    @PostConstruct
    void init() {
        customers << new Customer(id: 1, name: "Mike", email: "email", homeAddress: "home", additionalAddresses:
                ["address1", "address2"])
    }

    @GET
    @Path("{id}")
    public Response getCustomer(@PathParam("id") String id, @HeaderParam("Accept") String mediaType) {

        Customer customer = checkNotNull(customers.find {it.id == id})

        respond().with(customer).and(mediaType)

    }

    /**
     * Example of fluid interface with groovy
     * @return
     */
    def respond() {
        [with: { Customer customer ->
            [and: {String mediaType ->
                new CustomerResponseBuilder().withCustomer(customer).withMediaType(mediaType).build()
            }]
        }]

    }
}
