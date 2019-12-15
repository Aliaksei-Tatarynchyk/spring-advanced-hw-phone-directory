<!DOCTYPE html>

<html lang="en" xmlns="http://www.w3.org/1999/html">
<head>
  <title>User details</title>
</head>
<body data-autotests-id="user-page">

  <h2>You:</h2>
  <ul>
    <#if user??>
      <#include "components/userInfo.ftl">
    </#if>
  </ul>

  <#include "components/backToHome.ftl">

</body>
</html>