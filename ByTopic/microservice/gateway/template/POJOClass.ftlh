package ${package};
public class ${class} {
  <#list fields as field>
  private ${field.type} ${field.name};
  </#list>

  <#list fields as field>
  public ${field.type} get${field.pascalName}() {
    return ${field.name};
  }

  public void set${field.pascalName}(${field.type} value) {
    ${field.name} = value;
  }
  </#list>
}
