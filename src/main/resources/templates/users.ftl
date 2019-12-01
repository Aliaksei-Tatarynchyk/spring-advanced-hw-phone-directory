<!DOCTYPE html>

<html lang="en" xmlns="http://www.w3.org/1999/html">
<head>
  <title>Users</title>
  <script src="/static/custom.js"></script>
</head>
<body data-autotests-id="users-page">

<h2>Users:</h2>
<ul>
  <#list users as user>
    <li>
      <b>${user.fullName}</b>:
      <#list user.phoneNumbers as phoneNumber>
          ${phoneNumber.value}<#if phoneNumber.phoneCompany??> (${phoneNumber.phoneCompany.name})</#if>;
      </#list>
    </li>
  </#list>
</ul>
<a href="/">Back to the import</a></br>
Download as PDF:
<ul style="display: inline-grid;">
  <li><a href="javascript:void(0);" onclick="downloadPDF('/users', 'users.pdf')" type="application/pdf">directly</a></li>
  <li><a href="javascript:void(0);" onclick="downloadPDF('/users/spring-pdf', 'users-spring-pdf.pdf')" type="application/pdf">via Spring PDF view</a></li>
</ul>

</body>
</html>