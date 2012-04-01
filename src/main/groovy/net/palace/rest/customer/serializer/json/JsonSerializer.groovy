package net.palace.rest.customer.serializer.json

import net.palace.rest.customer.serializer.CustomerSerializer
import net.palace.rest.customer.serializer.Serializer

@Serializer(format = "json")
class JsonSerializer implements CustomerSerializer {

    def builder = new groovy.json.JsonBuilder()


    private addHomeAddress(def customer) {

        builder.customer {

            name(customer.name)
            email(customer.email)
            homeAddress(customer.homeAddress)
        }

    }

    String serialize(def customer) {
        addHomeAddress(customer)
        builder.toString()
    }
}