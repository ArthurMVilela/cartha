<#assign title="Cartório">
<!DOCTYPE html>
<html lang="en">
<head>
    <#include "./partials/_meta.ftl">
</head>
<body>
<#include "./partials/_topbarMenu.ftl">
<div class="container" style="margin-top:80px">
    <div class="row justify-content-center">
        <h2 class="m-3">Cartório (ID: ${notary.id})</h2>
    </div>
    <div class="row justify-content-center">
        <div class="col">
            <div class="card m-3">
                <ul class="list-group list-group-flush">
                    <li class="list-group-item">ID: ${notary.id}</li>
                    <li class="list-group-item">Nome: ${notary.name}</li>
                    <li class="list-group-item">CNPJ: ${notary.cnpj}</li>
                    <li class="list-group-item">CNS: ${notary.cns}</li>
                </ul>
            </div>
        </div>
    </div>
    <div class="row justify-content-end">
        <div class="col">
            <a class="btn btn-primary" href="/notary/${notary.id}/add-manager" role="button">Adicionar Gerente</a>
            <a class="btn btn-primary" href="/notary/${notary.id}/add-official" role="button">Adicionar Funcionário</a>
        </div>
    </div>
    <div class="row justify-content-center">
        <h3 class="m-3">Funcionários e gerentes: </h3>
    </div>
    <div class="row justify-content-center">
        <table class="table col m-3">
            <thead>
                <tr>
                    <th scope="col">CPF</th>
                    <th scope="col">Nome</th>
                </tr>
            </thead>
            <tbody>
                <#list officials as official>
                    <tr>
                        <td>${official.cpf}</td>
                        <td>${official.name}</td>
                    </tr>
                </#list>
            </tbody>
        </table>
    </div>
</div>

<script type="text/javascript" src="/static/main.js"></script>
</body>
</html>