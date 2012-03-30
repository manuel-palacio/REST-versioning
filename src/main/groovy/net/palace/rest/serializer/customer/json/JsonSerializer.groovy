package net.palace.rest.serializer.customer.json

import net.palace.rest.Customer
import net.palace.rest.serializer.customer.CustomerSerializer
import net.palace.rest.serializer.customer.Serializer


@Serializer(version="v1",format="json")

class JsonSerializer implements CustomerSerializer {


    String serialize(Customer customer) {
        return null  //To change body of implemented methods use File | Settings | File Templates.
    }
}
