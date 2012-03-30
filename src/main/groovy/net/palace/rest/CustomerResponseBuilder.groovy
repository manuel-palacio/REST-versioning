package net.palace.rest

import com.sun.jersey.core.spi.factory.ResponseBuilderImpl
import net.palace.rest.serializer.customer.CustomerSerializer
import net.palace.rest.serializer.customer.Serializer
import org.reflections.Reflections

import javax.ws.rs.core.Response

import static javax.ws.rs.core.Response.Status.OK

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
        new ResponseBuilderImpl().entity(serializer.serialize(customer)).status(OK).build()

    }
}
