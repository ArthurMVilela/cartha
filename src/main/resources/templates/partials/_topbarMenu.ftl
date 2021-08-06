<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="container-fluid">
        <a class="navbar-brand" href="/">CARTHA</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <#list menu as k,v>
                    <li class="nav-item">
                        <a class="nav-link" href="${v}">${k}</a>
                    </li>
                </#list>
            </ul>
            <ul class=" navbar-nav ml-auto mb-2 mb-lg-0">
                <#if role??>
                    <li class="nav-item">
                        <a class="nav-link" href="/logout">Logout</a>
                    </li>
                <#else>
                    <li class="nav-item">
                        <a class="nav-link" href="/login">Login</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/create-account">Criar Conta</a>
                    </li>
                </#if>
            </ul>
        </div>
    </div>
</nav>