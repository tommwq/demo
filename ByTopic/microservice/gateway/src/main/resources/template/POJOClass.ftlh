package ${package};
public class ${class} {
  <#list fields as field>
  private ${field.type?no_esc} ${field.name};
  </#list>

  <#list fields as field>
  public ${field.type?no_esc} get${field.pascalName}() {
    return ${field.name};
  }

  public void set${field.pascalName}(${field.type?no_esc} value) {
    ${field.name} = value;
  }
  </#list>
}
