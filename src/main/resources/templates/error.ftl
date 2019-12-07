<!DOCTYPE html>

<html lang="en" xmlns="http://www.w3.org/1999/html">
<head>
  <title>Error page</title>
</head>
<body data-autotests-id="error-page">

  <h2>Error:</h2>
  <p>
    <#if errorMessage??>${errorMessage}</#if>
  </p>

  <span>Back to the <a href="/">home</a> page</span></br>

</body>
</html>