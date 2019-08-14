<!DOCTYPE html>
<html>
  <body>
    在线设备数：${onlineDeviceSet?size} <hr/>
    <ol>
    <#list onlineDeviceSet as device>
      <li><a href="/log/${device}">${device}</a></li>
    </#list>
    </ol>
  </body>
</html>
