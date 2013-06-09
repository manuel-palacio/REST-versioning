package net.palace.rest

import net.palace.rest.customer.CustomerResource
import org.glassfish.jersey.server.ResourceConfig
import org.glassfish.jersey.test.JerseyTest
import org.junit.Test

import javax.ws.rs.core.Application

import static com.jayway.restassured.RestAssured.given
import static org.hamcrest.Matchers.equalTo
import static org.hamcrest.Matchers.hasXPath

class RestVersionTest extends JerseyTest {

    static def HOST = "http://localhost:9998"

    @Override
    protected Application configure() {
        return new ResourceConfig(CustomerResource.class);
    }

    private static def given(String mediaType) {
        given().header("Accept", mediaType)
    }

    @Test
    public void get_customer_as_json_succeeds() {
        given("application/vnd.mycompany.myapp+json").
                expect().
                header("Content-Type", "application/vnd.mycompany.myapp+json").
                body("customer.homeAddress", equalTo("home")).
                when().
                get("${HOST}/customer/1");
    }

    @Test
    public void get_customer_as_xml_succeeds() {
        given("application/vnd.mycompany.myapp+xml").
                expect().
                header("Content-Type", "application/vnd.mycompany.myapp+xml").
                body("customer.homeAddress", equalTo("home")).
                when().get("${HOST}/customer/1");
    }

    @Test
    public void get_customer_as_xml_v2_succeeds() {
        given("application/vnd.mycompany.myapp-v2+xml").
                expect().
                header("Content-Type", "application/vnd.mycompany.myapp-v2+xml").
                body(hasXPath("/customer/addresses/homeAddress")).
                when().get("${HOST}/customer/1");
    }

    @Test
    public void get_unknown_customer_produces_404() {
        given("application/vnd.mycompany.myapp-v2+xml").
                expect().
                statusCode(404).
                when().
                get("http://localhost:9998/customer/15");
    }
}
