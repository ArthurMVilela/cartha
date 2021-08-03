<#assign title="Logs de acesso">
<!DOCTYPE html>
<html lang="en">
<head>
    <#include "./partials/_meta.ftl">
</head>
<body>
<#include "./partials/_topbarMenu.ftl">
<div class="container" style="margin-top:80px">
    <div class="row justify-content-center">
        <table class="table">
            <thead>
                <tr>
                    <th scope="col">ID Usuário</th>
                    <th scope="col">Hora</th>
                    <th scope="col">Ação</th>
                </tr>
            </thead>
            <tbody>
                <#list searchResult.rows as row>
                    <tr>
                        <td>${row.userId}</td>
                        <td>${row.timestamp}</td>
                        <td>${row.action.type}</td>
                    </tr>
                </#list>
            </tbody>
        </table>
    </div>
    <div class="row justify-content-center">
        <div class="col">
            <div class="input-group mb-3">

                <#if searchResult.currentPage gt 1>
                    <a href="?page=1" class="btn btn-primary" type="button">Primeira</a>
                    <a href="?page=${searchResult.currentPage - 1}" class="btn btn-primary" type="button">Anterior</a>
                <#else>
                    <a class="btn btn-secondary disabled" type="button">Primeira</a>
                    <a class="btn btn-secondary disabled" type="button">Anterior</a>
                </#if>

                <div class="input-group-text" id="btnGroupAddon">página: </div>
                <input type="text" class="form-control" value="${searchResult.currentPage}" disabled>
                <div class="input-group-text" id="btnGroupAddon">de: </div>
                <input type="text" class="form-control" value="${searchResult.numberOfPages}" disabled>

                <#if searchResult.currentPage != searchResult.numberOfPages>
                    <a href="?page=${searchResult.currentPage + 1}" class="btn btn-primary" type="button">Próxima</a>
                    <a href="?page=${searchResult.numberOfPages}" class="btn btn-primary" type="button">Ultima</a>
                <#else>
                    <a class="btn btn-secondary disabled" type="button">Próxima</a>
                    <a class="btn btn-secondary disabled" type="button">Ultima</a>
                </#if>

            </div>
        </div>

    </div>
</div>

<script type="text/javascript" src="/static/main.js"></script>
</body>
</html>