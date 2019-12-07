<!DOCTYPE html>

<html lang="en" xmlns="http://www.w3.org/1999/html">
<head>
  <title>Users page</title>
  <script src="/static/custom.js"></script>
</head>
<body data-autotests-id="users-page">

  <h2>Users:</h2>
  <ul>
    <#list users as user>
      <#include "components/userInfo.ftl">
    </#list>
  </ul>

  <span>Back to the <a href="/import">import</a> / <a href="/">home</a> page</span></br>

  <span>Download as PDF:</span>
  <ul style="display: inline-grid;">
    <li><a href="javascript:void(0);" onclick="downloadPDF('/users', 'users.pdf')" type="application/pdf">directly</a></li>
    <li><a href="javascript:void(0);" onclick="downloadPDF('/users/spring-pdf', 'users-spring-pdf.pdf')" type="application/pdf">via Spring PDF view</a></li>
  </ul>

</body>
</html>