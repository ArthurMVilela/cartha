<#assign title="Nó">
<!DOCTYPE html>
<html lang="en">
<head>
    <#include "./partials/_meta.ftl">
</head>
<body>
<#include "./partials/_topbarMenu.ftl">
<div class="container" style="margin-top:80px">
    <div class="row justify-content-center">
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
                <div class="card-body">
                    <a href="/blockchain/blocks/${node.nodeId}" class="card-link">Ver Blockchain</a>
                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript" src="/static/main.js"></script>
</body>
</html>