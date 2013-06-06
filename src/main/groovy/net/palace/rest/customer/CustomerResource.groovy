package net.palace.rest.customer

import com.sun.jersey.spi.resource.Singleton
import com.tinkerpop.blueprints.impls.tg.TinkerGraph
import com.tinkerpop.gremlin.groovy.Gremlin

import javax.annotation.PostConstruct
import javax.ws.rs.GET
import javax.ws.rs.HeaderParam
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.core.Response

import static net.palace.rest.RestPrecondition.checkNotNull
import static net.palace.rest.customer.DSL.respond

@Path("/customer")
@Singleton
class CustomerResource {

    static {
        Gremlin.load()
      }

    def graphDb = new TinkerGraph()   //in memory graph database. Check https://github.com/tinkerpop/gremlin/wiki

    @PostConstruct
    void init() {

        def customer = graphDb.addVertex(1)
        customer.email = "email"
        customer.homeAddress = "home"
        customer.name = "Mike"
        customer.additionalAddresses = ["address1", "address2"]

    }

    @GET
    @Path("{id}")
    public Response getCustomer(@PathParam("id") String id, @HeaderParam("Accept") String mediaType) {

        def customer = checkNotNull(graphDb.v(id))

        respond().withEntity(customer).withVersion(mediaType)

    }
}
