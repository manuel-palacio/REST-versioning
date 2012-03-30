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

    CustomerResource.Format format

    CustomerResource.ResponseVersion responseVersion

    CustomerResponseBuilder withCustomer(Customer customer) {
        this.customer = customer
        return this
    }

    CustomerResponseBuilder withFormat(CustomerResource.Format format) {
        this.format = format

        return this
    }

    CustomerResponseBuilder withVersion(CustomerResource.ResponseVersion version) {
        this.responseVersion = version

        return this
    }


    Response build() {
        CustomerSerializer serializer = serializerMap["${responseVersion.name()}:${format.name()}"]
        Response.ok(serializer.serialize(customer)).build()

    }
}
