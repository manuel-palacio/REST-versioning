package net.palace.rest

import com.sun.jersey.api.core.PackagesResourceConfig
import com.sun.jersey.spi.container.servlet.ServletContainer
import org.junit.AfterClass
import org.junit.BeforeClass
import org.junit.Test
import org.mortbay.jetty.Server
import org.mortbay.jetty.servlet.Context
import org.mortbay.jetty.servlet.ServletHolder

import static com.jayway.restassured.RestAssured.given
import static org.hamcrest.Matchers.equalTo
import static org.hamcrest.Matchers.hasXPath
import org.junit.Ignore

class RestVersionTest {
    static Server server;

    @BeforeClass
    public static void startServer() throws Exception {
        server = new Server(8080);
        Context root = new Context(server, "/resources", Context.SESSIONS);
        ServletContainer servletContainer = new ServletContainer(new PackagesResourceConfig("net.palace.rest.customer"))
        root.addServlet(new ServletHolder(servletContainer), "/*");
        server.start()
    }

    private def given(String mediaType) {
        given().header("Accept", mediaType)
    }

    @Test
    @Ignore
    public void getCustomerJson() {
        given("application/vnd.mycompany.myapp+json").expect().body("homeAddress", equalTo("home")).
                when().get("/resources/customer/1");
    }

    @Test
    public void getCustomerAsXml() {
        given("application/vnd.mycompany.myapp+xml").expect().body("customer.homeAddress", equalTo("home")).
                when().get("/resources/customer/1");
    }

    @Test
    public void getCustomerAsXmlV2() {
        given("application/vnd.mycompany.myapp-v2+xml").expect().body(hasXPath("/customer/addresses/homeAddress")).
                when().get("/resources/customer/1");
    }

    @Test
    public void getUnknownCustomerProduces404() {
        given("application/vnd.mycompany.myapp-v2+xml").expect().statusCode(404).when().get("/resources/customer/15");
    }

    @AfterClass
    public static void stopServer() throws Exception {
        server.stop();
    }
}
