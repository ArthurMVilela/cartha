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
            <div class="input-group mb-3">
                <#if currentPage gt 1>
                    <a href="?page=1" class="btn btn-primary" type="button">Primeira</a>
                    <a href="?page=${currentPage - 1}" class="btn btn-primary" type="button">Anterior</a>
                <#else>
                    <a class="btn btn-secondary disabled" type="button">Primeira</a>
                    <a class="btn btn-secondary disabled" type="button">Anterior</a>
                </#if>

                <div class="input-group-text" id="btnGroupAddon">página: </div>
                <input type="text" class="form-control" value="${currentPage}" disabled>
                <div class="input-group-text" id="btnGroupAddon">de: </div>
                <input type="text" class="form-control" value="${numberOfPages}" disabled>

                <#if currentPage != numberOfPages>
                    <a href="?page=${currentPage + 1}" class="btn btn-primary" type="button">Próxima</a>
                    <a href="?page=${numberOfPages}" class="btn btn-primary" type="button">Última</a>
                <#else>
                    <a class="btn btn-secondary disabled" type="button">Próxima</a>
                    <a class="btn btn-secondary disabled" type="button">Última</a>
                </#if>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript" src="/static/main.js"></script>
</body>
</html>