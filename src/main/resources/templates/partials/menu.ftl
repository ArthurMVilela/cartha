<nav class="navbar fixed-top navbar-expand-lg navbar-dark bg-primary">
    <div class="container-fluid">
        <a class="navbar-brand" href="/">Cartha</a>
        <button class="navbar-toggler text-white" type="button" data-bs-toggle="collapse" data-bs-target="#navbar-links">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbar-links">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <#list menu?keys as i>
                    <li class="nav-item">
                        <a class="nav-link" href="${menu[i]}">${i}</a>
                    </li>
                </#list>
                <#if !userRole??>
                    <li>
                        <a class="nav-link" href="/login">Entrar</a>
                    </li>
                    <li>
                        <a class="nav-link" href="/create-account/client">Criar Conta</a>
                    </li>
                <#else>
                    <li>
                        <a class="nav-link" href="/logout">Sair</a>
                    </li>
                </#if>
            </ul>
        </div>
    </div>
</nav>