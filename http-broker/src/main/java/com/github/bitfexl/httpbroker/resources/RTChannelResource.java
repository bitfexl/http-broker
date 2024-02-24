package com.github.bitfexl.httpbroker.resources;

import com.github.bitfexl.httpbroker.services.RTChannelService;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.UUID;

@Path("/rtchannel")
public class RTChannelResource {

    @Inject
    RTChannelService channelService;

    public record CreateChannelResponse(String channelId) { }

    @GET
    @Path("/create")
    @Produces(MediaType.APPLICATION_JSON)
    public CreateChannelResponse createChannel() {
        return new CreateChannelResponse(UUID.randomUUID().toString());
    }

    public record CloseChannelResponse(String channelId, String status) { }

    @GET
    @Path("/close/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public CloseChannelResponse closeChannel(String id) {
        return new CloseChannelResponse(id, "CLOSED");
    }
}
