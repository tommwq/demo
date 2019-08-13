package com.tq.gateway.generator;

/**
 * Record type and name of field generated for pojo class.
 */
public class FieldInfo {
  private String type;
  private String name;
  private String pascalName;

  public String getType() {
    return type;
  }

  public String getName() {
    return name;
  }

  public String getPascalName() {
    return pascalName;
  }

  private void setType(String aType) {
    type = aType;
  }

  private void setName(String aName) {
    name = aName;
    if (!name.isEmpty()) {
      pascalName = name.substring(0, 1).toUpperCase() + name.substring(1);
    } else {
      pascalName = name;
    }
  }

  public FieldInfo(String aType, String aName) {
    setType(aType);
    setName(aName);
  }
}
