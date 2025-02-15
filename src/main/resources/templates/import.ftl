<!DOCTYPE html>
<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />

<html lang="en">
<head>
  <title>Import dictionary</title>
</head>
<body data-autotests-id="import-page">

  <p>
    Example of the file being imported: <a href="/static/phoneDirectory.json">phoneDirectory.json</a>
    <form name="phoneDirectory" action="/import" enctype="multipart/form-data" method="POST">
      <@security.csrfInput/> <#-- include <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" /> for CSRF protection -->
      <input type="file" name="file" /> ⇨ <input type="submit" value="Import phone directory" />
    </form>
  </p>

  <#include "components/backToHome.ftl">

</body>
</html>