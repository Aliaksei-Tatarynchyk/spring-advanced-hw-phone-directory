<!DOCTYPE html>

<html lang="en">
   <head>
       <title>Users</title>
   </head>
   <body>

      <h2>Users:</h2>
      <ul>
         <#list users as user>
            <li>${user.fullName}</li>
         </#list>
      </ul>

   </body>
</html>