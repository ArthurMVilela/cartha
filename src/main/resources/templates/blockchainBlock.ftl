<#assign title="Bloco ${block.id}">
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
            <div class="input-group mb-3">
                <input type="text" class="form-control" value="${block.nodeId}">
                <button class="btn btn-outline-secondary" type="button" id="button-addon2">Buscar</button>
            </div>
        </div>
    </div>
    <div class="row justify-content-center">
        <div class="col ">
            <div class="card m-3">
                <ul class="list-group list-group-flush">
                    <li class="list-group-item">ID: ${block.id}</li>
                    <li class="list-group-item">Timestamp: ${block.timestamp}</li>
                    <li class="list-group-item">Hash: ${block.hash}</li>
                    <li class="list-group-item">ID do nó criador: ${block.nodeId}</li>
                </ul>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript" src="/static/main.js"></script>
</body>
</html>