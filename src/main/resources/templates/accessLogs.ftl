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
        <form class="col mb-3" method="post" action="/logs">
            <div class="row mb-3">
                <div class="col">
                    <label for="user-id" class="form-label">ID de usuário</label>
                    <#if filter??>
                        <input type="text" class="form-control mask-uuid" id="user-id" name="user-id" <#if filter.userId??>value="${filter.userId}"</#if>>
                    <#else>
                        <input type="text" class="form-control mask-uuid" id="user-id" name="user-id">
                    </#if>
                </div>
            </div>
            <div class="row mb-3">
                <div class="col-6">
                    <label for="start" class="form-label">Desde:</label>
<#--                    <#if filter??>-->
<#--                        <input type="text" class="form-control mask-date-time" id="start" name="start" <#if filter.start??>value="${filter.start}"</#if>>-->
<#--                    <#else>-->
<#--                        <input type="text" class="form-control mask-date-time" id="start" name="start">-->
<#--                    </#if>-->
                    <input type="text" class="form-control mask-date-time" id="start" name="start" disabled>
                </div>
                <div class="col-6">
                    <label for="end" class="form-label">Até:</label>
<#--                    <#if filter??>-->
<#--                        <input type="text" class="form-control mask-date-time" id="end" name="end" <#if filter.end??>value="${filter.end}"</#if>>-->
<#--                    <#else>-->
<#--                        <input type="text" class="form-control mask-date-time" id="end" name="end">-->
<#--                    </#if>-->
                    <input type="text" class="form-control mask-date-time" id="end" name="end" disabled>
                </div>
            </div>
            <div class="row mb-3">
                <div class="col">
                    <label for="domain-id" class="form-label">ID de domínio:</label>
                    <#if filter??>
                        <input type="text" class="form-control mask-uuid" id="domain-id" name="domain-id" <#if filter.domainId??>value="${filter.domainId}"</#if>>
                    <#else>
                        <input type="text" class="form-control mask-uuid" id="domain-id" name="domain-id">
                    </#if>
                </div>
            </div>
            <div class="row mb-3">
                <div class="col-6">
                    <label for="action-type" class="form-label">Ação:</label>

                    <select class="form-select" aria-label="Default select example" id="action-type" name="action-type">
                        <option value=""></option>
                        <#list actionTypes as key, value>
                            <option value="${key}"
                            <#if filter??>
                                <#if filter.actionType?? && filter.actionType == key>selected</#if>
                            </#if>>${value}</option>
                        </#list>
                    </select>
                </div>
                <div class="col-6">
                    <label for="subject" class="form-label">Assunto:</label>
                    <select class="form-select" aria-label="Default select example" id="subject" name="subject">
                        <option value=""></option>
                        <#list subjects as key, value>

                            <option value="${key}"
                            <#if filter??>
                                <#if filter.subject?? && filter.subject == key>selected</#if>
                            </#if>>${value}</option>
                        </#list>
                    </select>
                </div>
            </div>
            <button type="submit" class="btn btn-primary">Filtrar</button>
        </form>
    </div>
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
                        <td><a href="/logs/${row.id}">Ver log</a></td>
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