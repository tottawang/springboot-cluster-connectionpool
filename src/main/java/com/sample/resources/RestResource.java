package com.sample.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.sample.service.NodeTenantCounterContainer;

@Component
@Produces(MediaType.APPLICATION_JSON)
@Path("/api")
@Transactional(readOnly = true)
public class RestResource {

  @Autowired
  private NodeTenantCounterContainer container;

  @GET
  @Path("sample")
  public void getSample() throws InterruptedException {
    String tenant = "myTestTenant";
    String node = "myTestNode";
    container.getCounter(node, tenant).increment();
    container.getCounter(node, tenant).increment();
    container.getCounter(node, tenant).decrement();
  }

}
