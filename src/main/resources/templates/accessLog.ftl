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
            <h2>Log de acesso: ${log.id}</h2>
            <ul class="list-group">
                <li class="list-group-item">ID do log: ${log.id}</li>
                <li class="list-group-item">ID da sessão de usuário: ${log.sessionId}</li>
                <li class="list-group-item">ID de usuário: ${log.userId}</li>
                <li class="list-group-item">Data e hora: ${log.timestamp}</li>
                <li class="list-group-item">
                    Ação: ${log.action.type.value} | ${log.action.subject.value} | <#if log.action.domainId??>${log.action.domainId}<#else>NA</#if>
                </li>
            </ul>
        </div>
    </div>
</div>

<script type="text/javascript" src="/static/main.js"></script>
</body>
</html>