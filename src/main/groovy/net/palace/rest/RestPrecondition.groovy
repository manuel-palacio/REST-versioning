package net.palace.rest

import javax.ws.rs.WebApplicationException

import static javax.ws.rs.core.Response.Status.NOT_FOUND

class RestPrecondition {

    static <T> T checkNotNull(T ref) {
        if (!ref) {
            throw new WebApplicationException(NOT_FOUND)
        }

        return ref
    }
}
