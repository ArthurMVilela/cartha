<#assign title="Adicionar nó de blockchain">
<!DOCTYPE html>
<html lang="en">
<head>
    <#include "./partials/_meta.ftl">
</head>
<body>
<#include "./partials/_topbarMenu.ftl">
<div class="container" style="margin-top:80px">
    <div class="row justify-content-center">
        <form class="col" action="/blockchain/nodes/create/${notaryId}" method="post">
            <div class="m-3">
                <label for="address" class="form-label">Endereço: </label>
                <input id="address" type="text" class="form-control" name="address" required>
                <div class="invalid-feedback">
                    Você deve inserir endereço do nó
                </div>

                <div class="m-3">
                    <button type="submit" class="btn btn-primary">Criar Nó</button>
                </div>
            </div>
        </form>
    </div>
</div>

<script type="text/javascript" src="/static/main.js"></script>
</body>
</html>