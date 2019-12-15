<!DOCTYPE html>

<html lang="en" xmlns="http://www.w3.org/1999/html">
<head>
  <title>All users</title>
  <script src="/static/custom.js"></script>
</head>
<body data-autotests-id="users-page">

  <h2>Users:</h2>
  <#if users?size == 0>
    <p style="color: #ff6107">
      There are no users currently in the application, but you can <a href="/import">import</a> them.
    </p>
  <#else>
    <ul>
      <#list users as user>
        <#include "components/userInfo.ftl">
      </#list>
    </ul>
  </#if>

  <#include "components/backToHome.ftl">

  <span>Download as PDF:</span>
  <ul style="display: inline-grid;">
    <li><a href="javascript:void(0);" onclick="downloadPDF('/users', 'users.pdf')" type="application/pdf">directly</a></li>
    <li><a href="javascript:void(0);" onclick="downloadPDF('/users/spring-pdf', 'users-spring-pdf.pdf')" type="application/pdf">via Spring PDF view</a></li>
  </ul>

</body>
</html>