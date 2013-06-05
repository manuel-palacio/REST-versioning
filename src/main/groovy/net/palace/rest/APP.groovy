package net.palace.rest

import com.sun.jersey.api.core.PackagesResourceConfig

import javax.ws.rs.ApplicationPath



@ApplicationPath("resources")
class App extends PackagesResourceConfig {
    public App() {
        super("net.palace.rest.customer");
    }
}
