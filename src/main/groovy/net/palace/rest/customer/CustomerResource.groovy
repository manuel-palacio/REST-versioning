package net.palace.rest.customer

import com.sun.jersey.spi.resource.Singleton

import javax.annotation.PostConstruct
import javax.ws.rs.GET
import javax.ws.rs.HeaderParam
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.core.Response

import static net.palace.rest.RestPrecondition.*
import static net.palace.rest.customer.DSL.*
import com.tinkerpop.blueprints.pgm.impls.tg.TinkerGraph
import com.tinkerpop.gremlin.groovy.Gremlin
import com.tinkerpop.blueprints.pgm.impls.tg.TinkerGraphFactory

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

        def customer = checkNotNull(graphDb.getVertex(id))

        respond().withEntity(customer).withVersion(mediaType)

    }
}
