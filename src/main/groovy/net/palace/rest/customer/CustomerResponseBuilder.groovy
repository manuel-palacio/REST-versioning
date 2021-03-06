package net.palace.rest.customer

import net.palace.rest.customer.serializer.CustomerSerializer
import net.palace.rest.customer.serializer.Serializer
import org.reflections.Reflections

import javax.ws.rs.core.Response

import static net.palace.rest.RestPrecondition.checkNotNull

class CustomerResponseBuilder {

    static Map<String, CustomerSerializer> serializers = [:]

    static {
        Reflections reflections = new Reflections("net.palace.rest.customer");
        Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(Serializer.class)

        annotated.each {
            def annotation = it.getAnnotation(Serializer.class)
            serializers["${annotation.version()}:${annotation.format()}"] = it.newInstance()
        }

        if (serializers.isEmpty()) throw new IllegalStateException("Cannot initialize response builder")

    }

    def customer

    def format

    def responseVersion = "v1"  //default

    CustomerResponseBuilder withCustomer(def customer) {
        this.customer = customer
        return this
    }


    CustomerResponseBuilder withMediaType(String mediaType) {
        def matcher = (mediaType =~ /(\w\d)/)
        if (matcher) {
            this.responseVersion = matcher[0][1]
        }

        matcher = (mediaType =~ /(\+\w*)/)
        if (matcher) {
            this.format = matcher[0][1].substring(1)
        }

        return this
    }

    Response build() {

        CustomerSerializer serializer = serializers["$responseVersion:$format"]

        Response.ok(checkNotNull(serializer).serialize(checkNotNull(customer))).build()
    }
}
