package net.palace.rest.customer.serializer

import net.palace.rest.customer.Customer


public interface CustomerSerializer {

    String serialize(Customer customer)

}