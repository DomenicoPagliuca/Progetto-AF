package it.unisa.agency_formation.team.control;

import it.unisa.agency_formation.autenticazione.domain.Utente;
import it.unisa.agency_formation.team.domain.Team;
import it.unisa.agency_formation.team.manager.TeamManagerImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/AddTeamControl")
public class AddTeamControl extends HttpServlet {

    private TeamManagerImpl teamManager = new TeamManagerImpl();

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher;
        String action = req.getParameter("action");
        try {
            if (action == null) {
                    resp.getWriter().write("1");//action null
                    dispatcher = req.getServletContext().getRequestDispatcher("/static/CreateTeam.jsp");
                    dispatcher.forward(req, resp);
            } else if (action.equalsIgnoreCase("aggiungi")) {
                int idDip = Integer.parseInt(req.getParameter("id"));
                int idTeam = teamManager.viewLastIdTeam();
                teamManager.updateDipOnTeam(idDip, idTeam);
                resp.getWriter().write("2");//action null
                dispatcher = req.getServletContext().getRequestDispatcher("/static/CreateTeam.jsp");
                dispatcher.forward(req, resp);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doPost (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}