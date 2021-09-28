<#assign title="Certidões de nascimento">
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
            <a class="btn btn-primary" href="/civil-registry/birth/create" role="button">Criar Certidão de Nascimento</a>
        </div>
    </div>
    <#if birthCertificates??>
        <#list birthCertificates as bc>
            <div class="row justify-content-center">
                <div class="col ">
                    <div class="card m-3">
                        <div class="col ">
                            <div class="card m-3">
                                <ul class="list-group list-group-flush">
                                    <li class="list-group-item">ID: ${bc.id}</li>
                                    <li class="list-group-item">Nome: ${bc.name}</li>
                                </ul>
                            </div>
                            <div class="card-body">
                                <a href="/civil-registry/birth/${bc.id}" class="card-link">Ver Certidão</a>
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