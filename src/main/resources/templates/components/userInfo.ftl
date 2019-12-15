<li data-autotests-id="user-info">
  <b>${user.fullName}</b>:
  <ul>
    <#list user.phoneNumbers as phoneNumber>
      <li>
        <a href="/phoneNumber/${phoneNumber.id}">${phoneNumber.value}</a><#if phoneNumber.mobileOperator??> (${phoneNumber.mobileOperator.name})</#if>
      </li>
    </#list>
  </ul>
</li>