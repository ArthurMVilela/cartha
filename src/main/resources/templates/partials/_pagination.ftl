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