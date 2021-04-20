<div class="row">
    <div class="row">
        <div class="col">
            <label for="id" class="form-label">ID</label>
            <div class="input-group">
                <input type="text" class="form-control" id="id" aria-describedby="basic-addon3">
                <button class="btn btn-outline-secondary" id="find-physical-person">Buscar</button>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col">
            <label for="name" class="form-label">Nome</label>
            <input type="text" class="form-control" id="name" placeholder="Fulano da Silva Pereira">
        </div>
    </div>
    <div class="row">
        <div class="col">
            <label for="cpf" class="form-label">CPF</label>
            <input type="text" class="form-control mask-cpf" id="cpf" placeholder="999.999.999-99">
        </div>
    </div>
    <div class="row">
        <div class="col">
            <label for="birthday" class="form-label">Data de nascimento:</label>
            <div class="input-group" id="birthday">
                <input type="number" id="birthday_day" class="form-control" min="1" max="31" placeholder="1">
                <span class="input-group-text">de</span>
                <select class="form-select" id="birthday_month">
                    <#list month?keys as i>
                        <option value="${i}">${month[i]}</option>
                    </#list>
                </select>
                <span class="input-group-text">de</span>
                <input type="number" id="birthday_year" class="form-control input-year" min="1900" placeholder="2000">
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col">
            <label for="sex" class="form-label">Sexo:</label>
            <select class="form-select" id="sex">
                <#list sex?keys as i>
                    <option value="${i}">${sex[i]}</option>
                </#list>
            </select>
        </div>
        <div class="col">
            <label for="color" class="form-label">Cor/raça (Classificação do IBGE):</label>
            <select class="form-select" id="color">
                <#list color?keys as i>
                    <option value="${i}">${color[i]}</option>
                </#list>
            </select>
        </div>
    </div>
    <div class="row">
        <div class="col">
            <label for="civil-status" class="form-label">Estado Cívil:</label>
            <select class="form-select" id="civil-status">
                <#list civilStatus?keys as i>
                    <option value="${i}">${civilStatus[i]}</option>
                </#list>
            </select>
        </div>
    </div>
    <div class="row">
        <div class="col">
            <label for="nationality" class="form-label">Nacionalidade</label>
            <input type="text" class="form-control" id="nationality" placeholder="brasileiro">
        </div>
    </div>
</div>