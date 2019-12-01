<!DOCTYPE html>

<html lang="en">
<head>
  <title>Home page</title>
</head>
<body data-autotests-id="home-page">

  <p>
    Example of the file being imported: <a href="/static/phoneDirectory.json">phoneDirectory.json</a>
    <form name="phoneDirectory" action="/import" enctype="multipart/form-data" method="POST">
      <input type="file" name="file" /> ⇨
      <input type="submit" value="Import phone directory" />
    </form>

  </p>

</body>
</html>