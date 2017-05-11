<!DOCTYPE html>
<html>
<head>
    <title>TODO supply a title</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>
<div>
    <#if payHtml == null>
        该订单已支付或者已完成
    <#else>
        ${payHtml}
    </#if>
</div>
</body>
</html>
