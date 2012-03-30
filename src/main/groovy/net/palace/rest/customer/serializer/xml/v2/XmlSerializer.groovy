package net.palace.rest.customer.serializer.xml.v2

import groovy.xml.MarkupBuilder
import net.palace.rest.customer.Customer
import net.palace.rest.customer.serializer.CustomerSerializer
import net.palace.rest.customer.serializer.Serializer


@Serializer(version = "v2", format = "xml")
class XmlSerializer implements CustomerSerializer {

    def writer = new StringWriter()
    def xml = new MarkupBuilder(writer)



    private addAllAddresses(Customer customer) {

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

        writer.toString()
    }

}
