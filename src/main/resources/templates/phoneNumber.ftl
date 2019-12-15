<!DOCTYPE html>
<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />

<html lang="en" xmlns="http://www.w3.org/1999/html">
<head>
  <title>Phone number details</title>
</head>
<body data-autotests-id="phone-number-page">

  <p>
    Phone number: <b>${phoneNumber.value}</b>
  </p>
  <ul>
    <li>Owner: ${phoneNumber.user.fullName}</li>
    <li>Account balance: ${phoneNumber.userAccount.remainingAccountBalance}</li>
  </ul>

  <p>
    <form name="mobileOperator" action="${springMacroRequestContext.requestUri}" method="POST">
      <@security.csrfInput/>

      <#if error??><span style="color: red; font-weight: bold">${error}</span><br></#if>

      <label for="newMobileOperator">Mobile operator:</label>
      <select name="newMobileOperator">
        <#list mobileOperators as mobileOperator>
          <option value="${mobileOperator.id}" <#if mobileOperator.id == phoneNumber.mobileOperator.id>selected</#if> >${mobileOperator.name}</option>
        </#list>
      </select> ⇨ <input type="submit" value="Change mobile operator" />
    </form>
  </p>

  <#include "components/backToHome.ftl">

</body>
</html>