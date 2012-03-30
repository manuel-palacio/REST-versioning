package net.palace.rest

import net.palace.rest.serializer.customer.CustomerSerializer
import net.palace.rest.serializer.customer.Serializer
import org.reflections.Reflections

import javax.ws.rs.core.Response

class CustomerResponseBuilder {

    static Map<String, CustomerSerializer> serializerMap = [:]

    static {
        Reflections reflections = new Reflections("net.palace.rest");
        Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(Serializer.class)

        annotated.each {
            def annotation = it.getAnnotation(Serializer.class)
            serializerMap["${annotation.version().toUpperCase()}:${annotation.format().toUpperCase()}"] = it.newInstance()
        }

    }

    Customer customer

    String format = "xml"

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
        CustomerSerializer serializer = serializerMap["${responseVersion.toUpperCase()}:${format.toUpperCase()}"]
        Response.ok(serializer.serialize(customer)).build()

    }

}
