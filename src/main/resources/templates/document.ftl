<#assign title="SeusDocumentos">
<!DOCTYPE html>
<html lang="en">
<head>
    <#include "./partials/_meta.ftl">
</head>
<body>
<#include "./partials/_topbarMenu.ftl">
<div class="container" style="margin-top:80px">
    <div class="row justify-content-center">
        <h1>Seu documentos</h1>
    </div>
    <div class="row">
        <h2>Certidões de nascimento</h2>
        <h3>Sua Certidão</h3>
    </div>
    <div class="row justify-content-center">
        <#if (clientBc??)>
            <div class="card m-3">
                <div class="col ">
                    <div class="card m-3">
                        <ul class="list-group list-group-flush">
                            <li class="list-group-item">ID: ${clientBc.id}</li>
                            <li class="list-group-item">Nome: ${clientBc.name}</li>
                        </ul>
                    </div>
                    <div class="card-body">
                        <a href="/civil-registry/birth/${clientBc.id}" class="card-link">Ver Certidão</a>
                    </div>
                </div>
            </div>
        <#else >
            <p>Não há cadastro seu como filiado em nenhuma certidão de nascimento</p>
        </#if>
    <div class="row">
        <h3>Certidões que está registrado como afiliação/pai</h3>
    </div>
    <div class="row">
        <#if (affiliationsBcs?size = 0)>
            <p>Não há cadastro seu como filiado em nenhuma certidão de nascimento</p>
        </#if>
        <#list affiliationsBcs as bc>
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
        </#list>
    </div>
</div>

<script type="text/javascript" src="/static/main.js"></script>
</body>
</html>