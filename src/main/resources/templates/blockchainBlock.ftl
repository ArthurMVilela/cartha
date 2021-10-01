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
                <span class="input-group-text" id="basic-addon1">Cartório</span>
                <input type="text" class="form-control" disabled value="${nodeId}">
            </div>
        </div>
    </div>
    <div class="row justify-content-center">
        <div class="col ">
            <div class="card m-3">
                <div class="card-body">
                    <p class="card-text">ID: ${block.id}</p>
                    <p class="card-text">Timestamp: ${block.timestamp}</p>
                    <p class="card-text">Hash do bloco anterior: ${block.previousHash}</p>
                    <p class="card-text">Hash: ${block.hash}</p>
                    <p class="card-text">ID do nó criador: ${block.nodeId}</p>
                    <h5 class="card-title">Transações</h5>
                    <table class="table">
                        <thead>
                            <tr>
                                <th scope="col">Timestamp</th>
                                <th scope="col">Tipo</th>
                                <th scope="col">Hash</th>
                                <th scope="col">Hash do documento</th>
                            </tr>
                        </thead>
                        <tbody>
                        <#list block.transactions as t>
                            <tr>
                                <td>${t.timestamp}</td>
                                <td>${t.type}</td>
                                <td>${t.hash}</td>
                                <td>${t.documentHash}</td>
                            </tr>
                        </#list>

                        </tbody>
                    </table>
                </div>
                <ul class="list-group list-group-flush">

                </ul>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript" src="/static/main.js"></script>
</body>
</html>