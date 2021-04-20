<div class="row">
    <div class="row">
        <div class="col">
            <label for="id" class="form-label">ID</label>
            <div class="input-group">
                <input type="text" class="form-control" id="id">
                <button type="button" class="btn btn-outline-secondary" id="find-physical-person">Buscar</button>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col">
            <label for="name" class="form-label">Nome</label>
            <input type="text" class="form-control" id="name" placeholder="nome...">
        </div>
    </div>
    <div class="row">
        <div class="col-6">
            <label for="cpf" class="form-label">CPF</label>
            <input type="text" class="form-control mask-cpf" id="cpf" placeholder="999.999.999-99">
        </div>
        <div class="col-6">
            <label for="sex" class="form-label">Sexo:</label>
            <select class="form-select" id="sex">
                <#list sex?keys as i>
                    <option value="${i}">${sex[i]}</option>
                </#list>
            </select>
        </div>
    </div>
</div>