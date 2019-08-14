<!DOCTYPE html>
<html>
  <body>
    Hello! <br />
    ${onlineDeviceSet?size}
    <br />
    <ol>
    <#list onlineDeviceSet as device>
      <li>${device}</li>
    </#list>
    </ol>
  </body>
</html>
