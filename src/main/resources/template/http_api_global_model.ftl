package ${api.packageName};

<#list api.importList as import>
import ${import};
</#list>

<#list api.model?keys as class>
public class ${class} {
    <#list api.model[class]?keys as parameter>
    public ${api.model[class][parameter]} ${parameter};
    </#list>
}
</#list>

