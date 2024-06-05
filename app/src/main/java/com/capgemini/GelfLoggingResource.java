package com.capgemini;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.jboss.logging.Logger;

@Path("/gelf-logging")
@ApplicationScoped
public class GelfLoggingResource {
    private static final Logger LOG = Logger.getLogger(GelfLoggingResource.class);

    @GET
    public void log() {
        LOG.info("Some useful log message");
    }

}