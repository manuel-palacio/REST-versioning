package net.palace.rest

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

    enum Format {
        JSON, XML

        static Format getValue(String acceptHeader) {
           if(acceptHeader.endsWith("json")){
                JSON
            }

            return XML
        }

    }

    enum ResponseVersion {
        V1, V2

        static ResponseVersion getValue(String acceptHeader) {
            def matcher = (acceptHeader =~ /(\w\d)/)
            String version
            if (matcher.size() > 0) {
                version = matcher[0][1]
            }

            if (version) {
                valueOf(version.toUpperCase())
            } else {
                V1
            }
        }
    }

    List<Customer> customers = []

    @PostConstruct
    void init() {
        customers << new Customer(id: 1, name: "Mike", email: "email", homeAddress: "home", additionalAddresses:
                ["address1", "address2"])
    }

    @GET
    @Path("{id}")
    public Response getCustomer(@PathParam("id") String id, @HeaderParam("Accept") String acceptHeader) {

        Customer customer = checkNotNull(customers.find {it.id == id})

        new CustomerResponseBuilder().withCustomer(customer).withFormat(Format.getValue(acceptHeader)).
                withVersion(ResponseVersion.getValue(acceptHeader)).build()

    }
}
