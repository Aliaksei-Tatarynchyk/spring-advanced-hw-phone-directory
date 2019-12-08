<!DOCTYPE html>
<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />

<html lang="en">
<head>
  <title>Login page</title>
</head>
<body data-autotests-id="login-page">

  <p class="alert">
    <#if RequestParameters.error??>
      <span class="error" style="color: red">Invalid username or password</span>
    </#if>
    <#if RequestParameters.logout??>
      <span class="success" style="color: green">You have been logged out</span>
    </#if>
  </p>

  <form name="login" action="/login" method="POST">
    <@security.csrfInput/>
    <fieldset>
      <legend>Please Login</legend>

      <label for="username">Username</label><input type="text" name="username" />
      <label for="password">Password</label><input type="password" name="password" />

      <input type="submit" value="Log in" />
    </fieldset>
  </form>

  <#include "components/backToHome.ftl">

</body>
</html>