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

    String format

    String responseVersion

    CustomerResponseBuilder withCustomer(Customer customer) {
        this.customer = customer
        return this
    }



    CustomerResponseBuilder withMediaType(String mediaType) {
        this.responseVersion = getVersion(mediaType)
        this.format = getFormat(mediaType)

        return this
    }

    private getVersion(String mediaType) {
        def matcher = (mediaType =~ /(\w\d)/)
        String version
        if (matcher.size() > 0) {
            version = matcher[0][1]
        } else {
            version = "v1"
        }

        return version

    }

    private getFormat(String mediaType) {
        def matcher = (mediaType =~ /(\+\w*)/)
        String format
        if (matcher.size() > 0) {
            format = matcher[0][1]
        } else {
            return "xml"
        }

        return format.substring(1)
    }

    Response build() {
        CustomerSerializer serializer = serializerMap["${responseVersion.toUpperCase()}:${format.toUpperCase()}"]
        Response.ok(serializer.serialize(customer)).build()

    }

}
