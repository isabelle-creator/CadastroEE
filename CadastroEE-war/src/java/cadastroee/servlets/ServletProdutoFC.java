package cadastroee.servlets;

import cadastroee.controller.ProdutoFacadeLocal;
import cadastroee.model.Produto;
import java.io.IOException;
import java.util.List;
import jakarta.ejb.EJB;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.annotation.WebServlet;

@WebServlet(name = "ServletProdutoFC", urlPatterns = {"/ServletProdutoFC"})
public class ServletProdutoFC extends HttpServlet {

    @EJB
    ProdutoFacadeLocal facade;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String acao = request.getParameter("acao");
        String destino = "ProdutoLista.jsp";

        try {
            if (acao == null || acao.equals("listar")) {
                List<Produto> lista = facade.findAll();
                request.setAttribute("lista", lista);
                destino = "ProdutoLista.jsp";

            } else if (acao.equals("formIncluir")) {
                destino = "ProdutoDados.jsp";

            } else if (acao.equals("formAlterar")) {
                String idStr = request.getParameter("id");
                Integer id = Integer.valueOf(idStr);
                Produto produto = facade.find(id);
                request.setAttribute("produto", produto);
                destino = "ProdutoDados.jsp";

            } else if (acao.equals("excluir")) {
                String idStr = request.getParameter("id");
                Integer id = Integer.valueOf(idStr);
                Produto produto = facade.find(id);
                facade.remove(produto);
                
                List<Produto> lista = facade.findAll();
                request.setAttribute("lista", lista);
                destino = "ProdutoLista.jsp";

            } else if (acao.equals("alterar")) {
                String idStr = request.getParameter("id");
                Integer id = Integer.valueOf(idStr);
                Produto produto = facade.find(id);

                produto.setNome(request.getParameter("nome"));
                produto.setQuantidade(Integer.valueOf(request.getParameter("quantidade")));
                produto.setPrecoVenda(Float.valueOf(request.getParameter("precoVenda")));

                facade.edit(produto);

                List<Produto> lista = facade.findAll();
                request.setAttribute("lista", lista);
                destino = "ProdutoLista.jsp";

            } else if (acao.equals("incluir")) {
                Produto produto = new Produto();
                produto.setNome(request.getParameter("nome"));
                produto.setQuantidade(Integer.valueOf(request.getParameter("quantidade")));
                produto.setPrecoVenda(Float.valueOf(request.getParameter("precoVenda")));

                List<Produto> todos = facade.findAll();
                int novoId = todos.isEmpty() ? 1 : todos.get(todos.size() - 1).getIDProduto() + 1;
                produto.setIDProduto(novoId);

                facade.create(produto);

                List<Produto> lista = facade.findAll();
                request.setAttribute("lista", lista);
                destino = "ProdutoLista.jsp";
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("erro", e.getMessage());
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher(destino);
        dispatcher.forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}