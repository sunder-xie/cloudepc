<#escape x as x?html>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en" xmlns="http://www.w3.org/1999/html">
<!--<![endif]-->

<!-- BEGIN HEAD -->
<head>
    <meta charset="utf-8"/>
    <title>汽配管家</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta content="width=device-width, initial-scale=1.0" name="viewport"/>
    <meta http-equiv="Content-type" content="text/html; charset=utf-8">

    <link rel="shortcut icon" href="${monkDomain}/img/logo/im.ico">
    <link rel="stylesheet" href="${monkDomain}/monk-release/resources/common/chat_init_new.css?v=${.now?string('yyyy-MM-dd')}">

</head>

<body class="center">
    <input type="hidden" id="monkDomain" value="${monkDomain}">
</body>
<!-- END BODY -->

<script src="/static/js/jquery.min.js" type="text/javascript"></script>
<script src="${monkDomain}/monk-release/resources/chat/chat_init_new.js?v=${.now?string('yyyy-MM-dd')}"></script>

<script>
    var monkDomain = '${monkDomain}';
    ChatUtil.init(monkDomain);
</script>
</html>

</#escape>
