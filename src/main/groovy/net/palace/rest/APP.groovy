package net.palace.rest

import net.palace.rest.customer.CustomerResource

import javax.ws.rs.ApplicationPath
import javax.ws.rs.core.Application


@ApplicationPath("/resources")
class App extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> s = new HashSet<Class<?>>();
        s.add(CustomerResource.class);
        return s;
    }

}
