package net.palace.rest.customer.serializer.json

import net.palace.rest.customer.Customer
import net.palace.rest.customer.serializer.CustomerSerializer
import net.palace.rest.customer.serializer.Serializer


@Serializer(format = "json")
class JsonSerializer implements CustomerSerializer {

    def builder = new groovy.json.JsonBuilder()


    private addHomeAddress(Customer customer) {

        builder.customer {

            name(customer.name)
            email(customer.email)
            homeAddress(customer.homeAddress)
        }

    }

    String serialize(Customer customer) {
        addHomeAddress(customer)
        builder.toString()
    }
}