package com.rest.json.app;

import javax.json.stream.JsonGenerator;

import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

import com.rest.json.exception.EntityNotFoundMapper;
import com.rest.json.exception.WebApplicationMapper;
import com.rest.json.filter.AuthorizationRequestFilter;
import com.rest.json.filter.PoweredByResponseFilter;


public class MyApplication extends ResourceConfig {

    public MyApplication() {
        packages("com.rest.json.resources");
        register(AuthorizationRequestFilter.class);
        register(PoweredByResponseFilter.class);
        register(LoggingFilter.class);
        register(WebApplicationMapper.class);
        register(EntityNotFoundMapper.class);
        register(RolesAllowedDynamicFeature.class);
        property(JsonGenerator.PRETTY_PRINTING, true);
    }
}