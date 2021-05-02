<#assign title="Bloco">
<!DOCTYPE html>
<html lang="en">
<head>
    <#include "./partials/_meta.ftl">
</head>
<body>
    <#include "./partials/menu.ftl">
    <div class="container">
        <div class="row justify-content-center my-3">
            <div class="col-8">
                <div class="card">
                    <div class="card-body">
                        <h4 class="card-title">Bloco</h4>
                        <p class="card-text">ID: ${block.id}</p>
                        <p class="card-text">Timestamp: ${block.timestamp}</p>
                        <p class="card-text">Hash das transações: ${block.transactionsHash}</p>
                        <p class="card-text">Hash anterior: ${block.previousHash}</p>
                        <p class="card-text">Hash: ${block.hash}</p>
                        <h6 class="card-title">Transações</h6>
                        <div class="table-responsive-md">
                            <table class="table">
                                <thead>
                                <tr>
                                    <th scope="col">ID</th>
                                    <th scope="col">Timestamp</th>
                                    <th scope="col">ID do documento</th>
                                    <th scope="col">Tipo</th>
                                </tr>
                                </thead>
                                <tbody>
                                <#list block.transactions as i>
                                    <tr>
                                        <th scope="row">${i.id}</th>
                                        <td>${i.timestamp}</td>
                                        <td>${i.documentId}</td>
                                        <td>${i.type.value}</td>
                                    </tr>
                                </#list>
                                </tbody>
                            </table>
                        </div>

                    </div>

                </div>
            </div>


        </div>

    </div>

    <script type="text/javascript" src="/static/main.js"></script>
</body>
</html>