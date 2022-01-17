package it.unisa.agency_formation.reclutamento.control;

import it.unisa.agency_formation.autenticazione.domain.RuoliUtenti;
import it.unisa.agency_formation.autenticazione.domain.Utente;
import it.unisa.agency_formation.autenticazione.manager.AutenticazioneManager;
import it.unisa.agency_formation.autenticazione.manager.AutenticazioneManagerImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet("/ListaColloqui")
public class ListaColloqui  extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Utente user = (Utente) request.getSession().getAttribute("user");
        if (user != null && user.getRole() == RuoliUtenti.HR) {
            try {
                ArrayList<Utente> candidati = getCandidatiForColloquioFromManager();
                request.setAttribute("candidati", candidati);
                response.getWriter().write("1"); //ci sono i candidati
                RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/ListaColloqui.jsp");
                dispatcher.forward(request, response);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            response.getWriter().write("2"); //non è un HR
            response.sendRedirect("./static/Login.html");
        }
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    public static ArrayList<Utente> getCandidatiForColloquioFromManager() throws SQLException {
        AutenticazioneManager autenticazioneManager = new AutenticazioneManagerImpl();
        return autenticazioneManager.getCandidatiColloquio();
    }

}
