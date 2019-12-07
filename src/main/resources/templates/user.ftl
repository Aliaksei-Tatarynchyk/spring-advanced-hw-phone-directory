<!DOCTYPE html>

<html lang="en" xmlns="http://www.w3.org/1999/html">
<head>
  <title>User page</title>
  <script src="/static/custom.js"></script>
</head>
<body data-autotests-id="user-page">

  <h2>You:</h2>
  <ul>
    <#if user??>
      <#include "components/userInfo.ftl">
    </#if>
  </ul>

  <span>Back to the <a href="/">home</a> page</span></br>
</body>
</html>