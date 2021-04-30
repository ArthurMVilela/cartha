<#assign title="Blockchain">
<!DOCTYPE html>
<html lang="en">
<head>
    <#include "./partials/_meta.ftl">
</head>
<body>
    <#include "./partials/menu.ftl">
    <div class="container">
        <div class="row my-3">
            <select class="form-select" id="node-select">
                <#list nodes as node>
                    <#if selected == node.id>
                        <option value="${node.id}" selected>${node.id}</option>
                    <#else>
                        <option value="${node.id}">${node.id}</option>
                    </#if>
                </#list>
            </select>
        </div>
        <div class="row my-3">
            <p>Número de blocos: ${blockchain.blocks?size}</p>
        </div>

        <div class="row row-cols-1 row-cols-md-2 row-cols-lg-3  g-4">
            <#list blockchain.blocks?reverse as block>
                <div class="col">
                    <div class="card">
                        <div class="card-body">
                            <h5 class="card-title">Bloco</h5>
                        </div>
                        <ul class="list-group list-group-flush">
                            <li class="list-group-item">ID: ${block.id}</li>
                            <li class="list-group-item">Timestamp: ${block.timestamp}</li>
                            <li class="list-group-item">Hash das transações: ${block.transactionsHash}</li>
                            <li class="list-group-item">Hash do bloco: ${block.hash}</li>
                        </ul>
                        <div class="card-body">
                            <a href="/blocks/${block.id}" class="btn btn-primary">Ver detalhes do bloco</a>
                        </div>

                    </div>
                </div>
            </#list>
        </div>
    </div>

    <script type="text/javascript" src="/static/main.js"></script>
    <script type="text/javascript">
        $("#node-select").change(function () {
            console.log( $( "#node-select option:selected" ).text())
            $(location).attr('href', '/blockchain/' + $( "#node-select option:selected" ).text())
        })
    </script>
</body>
</html>