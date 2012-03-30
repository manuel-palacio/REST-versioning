package net.palace.rest.customer

import net.palace.rest.customer.serializer.CustomerSerializer
import net.palace.rest.customer.serializer.Serializer
import org.reflections.Reflections

import javax.ws.rs.core.Response

import static net.palace.rest.RestPrecondition.checkNotNull

class CustomerResponseBuilder {

    static Map<String, CustomerSerializer> serializerMap = [:]

    static {
        Reflections reflections = new Reflections("net.palace.rest.customer");
        Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(Serializer.class)

        annotated.each {
            def annotation = it.getAnnotation(Serializer.class)
            serializerMap["${annotation.version()}:${annotation.format()}"] = it.newInstance()
        }

        if(serializerMap.isEmpty()) throw new IllegalStateException("Cannot initialize response builder")

    }

    Customer customer

    String format

    String responseVersion = "v1"

    CustomerResponseBuilder withCustomer(Customer customer) {
        this.customer = customer
        return this
    }


    CustomerResponseBuilder withMediaType(String mediaType) {
        getVersion(mediaType)
        getFormat(mediaType)

        return this
    }

    private void getVersion(String mediaType) {
        def matcher = (mediaType =~ /(\w\d)/)
        if (matcher.size() > 0) {
            this.responseVersion = matcher[0][1]
        }

    }

    private void getFormat(String mediaType) {
        def matcher = (mediaType =~ /(\+\w*)/)
        if (matcher.size() > 0) {
            this.format = matcher[0][1].substring(1)
        }

    }

    Response build() {

        CustomerSerializer serializer = serializerMap["$responseVersion:$format"]

        Response.ok(checkNotNull(serializer).serialize(checkNotNull(customer))).build()
    }
}
