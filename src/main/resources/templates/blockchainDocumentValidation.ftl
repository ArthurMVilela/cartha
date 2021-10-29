<#assign title="Validação de documento">
<!DOCTYPE html>
<html lang="en">
<head>
    <#include "./partials/_meta.ftl">
</head>
<body>
<#include "./partials/_topbarMenu.ftl">
<div class="container" style="margin-top:80px">
    <div class="row justify-content-center">
        <h2>Última transação com este documento:</h2>
    </div>
    <div class="row justify-content-center">
        <div class="col ">
            <div class="card m-3">
                <ul class="list-group list-group-flush">
                    <li class="list-group-item">ID: ${transaction.id}</li>
                    <li class="list-group-item">Timestamp: ${transaction.timestamp}</li>
                    <li class="list-group-item">ID do documento: ${transaction.documentId}</li>
                    <li class="list-group-item">Hash do documento: ${transaction.documentHash}</li>
                    <li class="list-group-item">Tipo de transação: ${transaction.type}</li>
                    <li class="list-group-item">Hash: ${transaction.hash}</li>
                    <li class="list-group-item">Pendente: <#if transaction.pending>SIM<#else>NÃO</#if></li>
                    <li class="list-group-item">Bloco: <#if transaction.blockId??>${transaction.blockId}<#else>NA</#if></li>
                </ul>
            </div>
        </div>
    </div>
    <div class="row justify-content-center">
        <h2>Status do documento:</h2>
    </div>
    <div class="row justify-content-center">
        <div class="col ">
            <div class="card m-3">
                <ul class="list-group list-group-flush">
                    <li class="list-group-item">Validação de transação pendente: <#if transaction.pending>SIM<#else>NÃO</#if></li>
                    <li class="list-group-item">Hash válida: <#if valid>SIM<#else>NÃO</#if></li>
                </ul>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript" src="/static/main.js"></script>
</body>
</html>