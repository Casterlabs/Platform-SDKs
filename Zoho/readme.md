# ZohoApiJava

## Send Email
![Send Email Result](https://i.imgur.com/dKrbBXI.png)

```java
// Fill in these variables :^)
ZohoAuth auth = new ZohoAuth(refreshToken, clientId, clientSecret, redirectUri, scope);

ZohoUserAccount account = new ZohoMailGetUserAccountDetailsRequest(auth)
    .send()
    .get(0); // Get the first associated account, seems to work fine.

String accountId = account.getAccountId();
String fromAddress = account.getPrimaryEmailAddress();

new ZohoMailSendEmailRequest(auth)
    .setAccountId(accountId)
    .setContentsAsHtml("<!DOCTYPE html><html><span>Test message from <b>ZohoApiJava</b>!</span></html>")
    .setFromAddress(fromAddress)
    .setToAddress("example@email.com")
    .setSubject("Test!")
    .send();
```
