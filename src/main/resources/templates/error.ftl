<#assign title="Erro">
<!DOCTYPE html>
<html lang="en">
<head>
    <#include "./partials/_meta.ftl">
</head>
<body>
<#include "./partials/_topbarMenu.ftl">
<div class="container" style="margin-top:80px">
    <div class="row justify-content-center">
        <div class="col">
            <h1 class="display-1">${code} - ${errorTitle}</h1>
            <p>${errorDesc}</p>
            <p><a href="/">Retornar ao início</a></p>
        </div>
    </div>
</div>

<script type="text/javascript" src="/static/main.js"></script>
</body>
</html>