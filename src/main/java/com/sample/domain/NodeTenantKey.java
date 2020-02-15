package com.sample.domain;

public class NodeTenantKey {
  
  private String node;
  private String tenant;
  
  public NodeTenantKey(String node, String tenant) {
    this.node = node;
    this.tenant = tenant;
  }

  public String getNode() {
    return node;
  }

  public void setNode(String node) {
    this.node = node;
  }

  public String getTenant() {
    return tenant;
  }

  public void setTenant(String tenant) {
    this.tenant = tenant;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((node == null) ? 0 : node.hashCode());
    result = prime * result + ((tenant == null) ? 0 : tenant.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    NodeTenantKey other = (NodeTenantKey) obj;
    if (node == null) {
      if (other.node != null)
        return false;
    } else if (!node.equals(other.node))
      return false;
    if (tenant == null) {
      if (other.tenant != null)
        return false;
    } else if (!tenant.equals(other.tenant))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "NodeTenantKey [node=" + node + ", tenant=" + tenant + "]";
  }  

}
