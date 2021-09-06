<#assign title="Nós">
<!DOCTYPE html>
<html lang="en">
<head>
    <#include "./partials/_meta.ftl">
</head>
<body>
<#include "./partials/_topbarMenu.ftl">
<div class="container" style="margin-top:80px">

    <div class="row justify-content-center">
        <#list nodes as node>
            <div class="col ">
                <div class="card m-3">
                    <div class="card-body">
                        <ul class="list-group list-group-flush">
                            <li class="list-group-item">ID: ${node.nodeId}</li>
                            <li class="list-group-item">ID do cartório: ${node.notaryId}</li>
                            <li class="list-group-item">URL: ${node.address}</li>
                            <li class="list-group-item">Status: ${node.status}</li>
                            <li class="list-group-item">
                                <#if node.lastHealthCheck??>
                                    Último check: ${node.lastHealthCheck}
                                <#else>
                                    Último check: NA
                                </#if>
                            </li>
                        </ul>
                    </div>
                </div>

            </div>
        </#list>

    </div>
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