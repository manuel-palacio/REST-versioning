package net.palace.rest.serializer.customer.xml.v1

import groovy.xml.MarkupBuilder
import net.palace.rest.Customer
import net.palace.rest.serializer.customer.CustomerSerializer
import net.palace.rest.serializer.customer.Serializer

@Serializer(version="v1",format="xml")
class XmlSerializer implements CustomerSerializer{

    def writer = new StringWriter()
    def xml = new MarkupBuilder(writer)


    void addHomeAddress(Customer customer) {

        xml.customer {

            xml.name(customer.name)
            xml.email(customer.email)
            xml.homeAddress(customer.homeAddress)
        }

    }

    String serialize(Customer customer) {
        addHomeAddress(customer)
        return writer.toString()
    }
}
