<!DOCTYPE html>
<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />

<html lang="en">
<head>
  <title>Home page</title>
</head>
<body data-autotests-id="import-page">

  <p>
    Example of the file being imported: <a href="/static/phoneDirectory.json">phoneDirectory.json</a>
    <form name="phoneDirectory" action="/import" enctype="multipart/form-data" method="POST">
      <#-- include <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" /> for CSRF protection -->
      <@security.csrfInput/>

      <input type="file" name="file" /> ⇨
      <input type="submit" value="Import phone directory" />
    </form>
  </p>

  <span>Back to the <a href="/">home</a> page</span></br>

<#--  <#if importDone??>
    <p style="color: green">
      Import is finished successfully. <a href="/users">Click to see the imported users</a>.
    </p>
  </#if>-->

</body>
</html>