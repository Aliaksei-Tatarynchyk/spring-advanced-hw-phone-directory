<!DOCTYPE html>

<html lang="en">
   <head>
       <title>Users</title>
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
      <a href="/">Back to the import</a>

   </body>
</html>