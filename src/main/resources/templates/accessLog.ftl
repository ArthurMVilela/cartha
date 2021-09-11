<#assign title="Logs de acesso">
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
            <h2>Log de acesso: ${accessLog.id}</h2>
            <ul class="list-group">
                <li class="list-group-item">ID do log: ${accessLog.id}</li>
                <li class="list-group-item">ID da sessão de usuário: ${accessLog.sessionId}</li>
                <li class="list-group-item">ID de usuário: ${accessLog.userId}</li>
                <li class="list-group-item">Data e hora: ${accessLog.timestamp}</li>
                <li class="list-group-item">
                    Ação: ${accessLog.action.type.value} | ${accessLog.action.subject.value} | <#if accessLog.action.domainId??>${accessLog.action.domainId}<#else>NA</#if>
                </li>
            </ul>
        </div>
    </div>
</div>

<script type="text/javascript" src="/static/main.js"></script>
</body>
</html>