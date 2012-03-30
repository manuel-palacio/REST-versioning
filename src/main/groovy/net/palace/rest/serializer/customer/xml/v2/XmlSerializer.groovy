package net.palace.rest.serializer.customer.xml.v2

import groovy.xml.MarkupBuilder
import net.palace.rest.Customer
import net.palace.rest.serializer.customer.CustomerSerializer
import net.palace.rest.serializer.customer.Serializer


@Serializer(version = "v2", format = "xml")
class XmlSerializer implements CustomerSerializer {

    def writer = new StringWriter()
    def xml = new MarkupBuilder(writer)



    void addAllAddresses(Customer customer) {

        xml.customer {
            xml.name(customer.name)
            xml.email(customer.email)
            xml.addresses() {
                homeAddress customer.homeAddress
                customer.additionalAddresses.each {
                    additional it
                }
            }
        }
    }

    String serialize(Customer customer) {

        addAllAddresses(customer)

        return writer.toString()
    }

}
