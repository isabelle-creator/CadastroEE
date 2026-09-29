<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="cadastroee.model.Produto"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Dados do Produto</title>
    <!-- Link CDN do Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="container"> <!-- Alínea a: classe container no body[cite: 8] -->
    <h1 class="my-4">Dados do Produto</h1>

    <%
        Produto p = (Produto) request.getAttribute("produto");
        String acao = (p == null) ? "incluir" : "alterar";
        String nome = (p != null && p.getNome() != null) ? p.getNome() : "";
        String quantidade = (p != null) ? String.valueOf(p.getQuantidade()) : "";
        String precoVenda = (p != null) ? String.valueOf(p.getPrecoVenda()) : "";
        String idProduto = (p != null) ? String.valueOf(p.getIDProduto()) : "";
    %>

    <!-- Alínea c: classe form no formulário[cite: 8] -->
    <form action="ServletProdutoFC" method="post" class="form">
        <input type="hidden" name="acao" value="<%= acao %>">

        <% if (acao.equals("alterar")) { %>
            <input type="hidden" name="id" value="<%= idProduto %>">
        <% } %>

        <!-- Alínea b: encapsular par label/input em div com mb-3[cite: 8] -->
        <div class="mb-3">
            <!-- Alínea d: classe form-label na label[cite: 8] -->
            <label class="form-label">Nome:</label>
            <!-- Alínea e: classe form-control no input[cite: 8] -->
            <input type="text" name="nome" value="<%= nome %>" class="form-control">
        </div>
        
        <div class="mb-3">
            <label class="form-label">Quantidade:</label>
            <input type="text" name="quantidade" value="<%= quantidade %>" class="form-control">
        </div>
        
        <div class="mb-3">
            <label class="form-label">Preço de Venda:</label>
            <input type="text" name="precoVenda" value="<%= precoVenda %>" class="form-control">
        </div>
        
        <!-- Alínea f: classes btn e btn-primary no botão[cite: 8] -->
        <button type="submit" class="btn btn-primary">
            <%= acao.equals("incluir") ? "Adicionar Produto" : "Alterar Produto" %>
        </button>
    </form>

    <!-- Link CDN do Bootstrap JavaScript -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>