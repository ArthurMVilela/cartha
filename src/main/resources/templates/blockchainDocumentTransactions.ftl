<#assign title="Transações com documento: ${documentId}">
<!DOCTYPE html>
<html lang="en">
<head>
    <#include "./partials/_meta.ftl">
</head>
<body>
<#include "./partials/_topbarMenu.ftl">
<div class="container" style="margin-top:80px">
    <#if transactions??>
        <#list transactions as t>
            <div class="row justify-content-center">
                <div class="col ">
                    <div class="card m-3">
                        <ul class="list-group list-group-flush">
                            <li class="list-group-item">ID: ${t.id}</li>
                            <li class="list-group-item">Timestamp: ${t.timestamp}</li>
                            <li class="list-group-item">ID do documento: ${t.timestamp}</li>
                            <li class="list-group-item">Hash do documento: ${t.timestamp}</li>
                            <li class="list-group-item">Tipo de transação: ${t.timestamp}</li>
                            <li class="list-group-item">Hash: ${t.hash}</li>
                            <li class="list-group-item">Pendente: <#if t.pending>SIM<#else>NÃO</#if></li>
                            <li class="list-group-item">Bloco: <#if t.blockId??>${t.blockId}<#else>NA</#if></li>
                        </ul>
                    </div>
                </div>
            </div>
        </#list>
    <#else >
        <div class="row justify-content-center">
            <div class="col ">
                Não há transações registradas com este documento
            </div>
        </div>
    </#if>
</div>

<script type="text/javascript" src="/static/main.js"></script>
</body>
</html>