<#assign title="Cartórios">
<!DOCTYPE html>
<html lang="en">
<head>
    <#include "./partials/_meta.ftl">
</head>
<body>
<#include "./partials/_topbarMenu.ftl">
<div class="container" style="margin-top:80px">
    <div class="row justify-content-start">
        <div class="col">
            <a class="btn btn-primary" href="/notary/create" role="button">Adicionar Cartório</a>
        </div>
    </div>
    <#if notaries??>
        <#list notaries as notary>
            <div class="row justify-content-center">
                <div class="col ">
                    <div class="card m-3">
                        <div class="col ">
                            <div class="card m-3">
                                <ul class="list-group list-group-flush">
                                    <li class="list-group-item">ID: ${notary.id}</li>
                                    <li class="list-group-item">Nome: ${notary.name}</li>
                                    <li class="list-group-item">CNPJ: ${notary.cnpj}</li>
                                    <li class="list-group-item">CNS: ${notary.cns}</li>
                                </ul>
                            </div>
                            <div class="card-body">
                                <a href="/notary/${notary.id}" class="card-link">Ver Cartório</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </#list>
    </#if>

    <div class="row justify-content-center">
        <div class="col">
            <#include "./partials/_pagination.ftl">
        </div>
    </div>
</div>

<script type="text/javascript" src="/static/main.js"></script>
</body>
</html>