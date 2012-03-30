package net.palace.rest.customer.serializer.xml.v1

import groovy.xml.MarkupBuilder
import net.palace.rest.customer.Customer
import net.palace.rest.customer.serializer.CustomerSerializer
import net.palace.rest.customer.serializer.Serializer

@Serializer
class XmlSerializer implements CustomerSerializer{

    def writer = new StringWriter()
    def xml = new MarkupBuilder(writer)


    private addHomeAddress(Customer customer) {

        xml.customer {

            xml.name(customer.name)
            xml.email(customer.email)
            xml.homeAddress(customer.homeAddress)
        }

    }

    String serialize(Customer customer) {
        addHomeAddress(customer)
        writer.toString()
    }
}
