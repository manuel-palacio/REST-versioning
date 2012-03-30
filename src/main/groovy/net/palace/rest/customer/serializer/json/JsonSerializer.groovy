package net.palace.rest.customer.serializer.json

import net.palace.rest.customer.Customer
import net.palace.rest.customer.serializer.CustomerSerializer
import net.palace.rest.customer.serializer.Serializer


@Serializer(version="v1",format="json")
class JsonSerializer implements CustomerSerializer {


    String serialize(Customer customer) {
        return null  //To change body of implemented methods use File | Settings | File Templates.
    }
}
