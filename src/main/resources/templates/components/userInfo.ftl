<li data-autotests-id="user-info">
  <b>${user.fullName}</b>:
  <#list user.phoneNumbers as phoneNumber>
    ${phoneNumber.value}<#if phoneNumber.phoneCompany??> (${phoneNumber.phoneCompany.name})</#if>;
  </#list>
</li>