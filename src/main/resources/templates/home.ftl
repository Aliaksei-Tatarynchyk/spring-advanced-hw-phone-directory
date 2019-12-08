<!DOCTYPE html>
<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />

<html lang="en">
<head>
  <title>Home page</title>
</head>
<body data-autotests-id="home-page">

  <p>
    Available actions:
  </p>
  <ul>
    <li>
      <a href="/login">Login</a>. There is a predefined "admin/admin" user to be able to make an initial import of users. <br>
      Default password for all regular users in <a href="/static/phoneDirectory.json">phoneDirectory.json</a> is "password", for manager is "admin".
    </li>
    <li><a href="/import">Import users from JSON file</a></li>
    <li><a href="/users">See all imported users</a></li>
    <li><a href="/users/current">See the current user</a></li>
    <li>
      <a href="javascript:void(0);" onclick="document.getElementById('logout-form').submit();">Logout</a>
      <form id="logout-form" action="/logout" method="post" style="visibility: hidden">
        <@security.csrfInput/>
      </form>
    </li>
  </ul>

</body>
</html>