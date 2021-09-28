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
                    <input type="text" class="form-control mask-date-time" id="start" name="start">
                </div>
                <div class="col-6">
                    <label for="end" class="form-label">Até:</label>
<#--                    <#if filter??>-->
<#--                        <input type="text" class="form-control mask-date-time" id="end" name="end" <#if filter.end??>value="${filter.end}"</#if>>-->
<#--                    <#else>-->
<#--                        <input type="text" class="form-control mask-date-time" id="end" name="end">-->
<#--                    </#if>-->
                    <input type="text" class="form-control mask-date-time" id="end" name="end">
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
                        <td>${row.action.type.value}</td>
                        <td><a href="/logs/${row.id}">Ver log</a></td>
                    </tr>
                </#list>
            </tbody>
        </table>
    </div>

    <div class="row justify-content-center">
        <div class="col">
            <#include "./partials/_pagination.ftl">
        </div>

    </div>
</div>

<script type="text/javascript" src="/static/main.js"></script>
</body>
</html>